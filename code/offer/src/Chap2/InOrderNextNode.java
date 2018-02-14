package Chap2;

/**
 * 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。
 * 注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
 */
public class InOrderNextNode {

    private class TreeLinkNode {
        int val;
        TreeLinkNode left = null;
        TreeLinkNode right = null;
        // next指向父结点
        TreeLinkNode next = null;

        TreeLinkNode(int val) {
            this.val = val;
        }
    }

    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        // 如果当前结点右子树不空，那么中序下一个结点是右子树的最左子结点（如果有的话）；如果右子树没有左子结点就返回右子树根结点
        if (pNode.right != null) {
            pNode = pNode.right;
            while (pNode.left != null) {
                pNode = pNode.left;
            }
            return pNode;
        }
        // 如果当前子结点pNode右子树为空
        // 返回上层的父结点，如果父结点的右子结点就是当前结点，继续返回到上层的父结点...直到父结点的左子结点等于当前结点
        while (pNode.next != null && pNode.next.right == pNode) {
            pNode =  pNode.next;
        }
        // 如果父结点的左子结点等于当前结点，说明下一个要遍历的结点就是父结点了；或者父结点为空（说明当前结点是root），还是返回父结点（null）
        // pNode.next == null 或者 pNode.next.left == pNode
        return pNode.next;
    }
}
