package Chap7;

/**
 * 输入一棵二叉查找树的两个结点，返回它们的最低公共祖先。
 */
public class LastSameInBST {
    private class Node {
        private Node left, right;
        private int val;

        public Node(int val) {
            this.val = val;
        }
    }

    public Node findLastSame(Node root, Node a, Node b) {
        Node cur = root;
        while (cur != null) {
            if (cur.val < a.val && cur.val < b.val) {
                cur = cur.right;
            } else if (cur.val > a.val && cur.val > b.val) {
                cur = cur.left;
            } else {
                return cur;
            }
        }
        return null;
    }
}
