package Chap4;

/**
 * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。
 * 要求不能创建任何新的结点，只能调整树中结点指针的指向。
 */
public class BSTTransToDLinkedList {
    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    // 当前结点的前驱
    private TreeNode preNode;

    public TreeNode Convert(TreeNode pRootOfTree) {
        if (pRootOfTree == null) {
            return null;
        }

        TreeNode root = pRootOfTree;
        // 得到双向链表
        inOrder(root);
        // 向左找到双向链表的头结点
        while (root.left != null) {
            root = root.left;
        }
        return root;

    }

    // 中序遍历并改变指针
    private void inOrder(TreeNode node) {
        if (node == null) return;

        inOrder(node.left);

        node.left = preNode;
        if (preNode != null) {
            preNode.right = node;
        }
        preNode = node;

        inOrder(node.right);
    }
}
