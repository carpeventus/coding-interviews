package Chap6;

public class BalancedTree {
    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    /**
     * 方法1：递归地求每个结点的左右子树深度差，有重复计算
     */
    public boolean isBalancedTree(TreeNode root) {
        if (root == null) return true;
        int left = depth(root.left);
        int right = depth(root.right);
        if (Math.abs(left - right) > 1) return false;
        return isBalancedTree(root.left) && isBalancedTree(root.right);
    }

    private int depth(TreeNode root) {
        if (root == null) return 0;
        int left = depth(root.left);
        int right = depth(root.right);
        return Math.max(left, right) + 1;
    }

    /**
     * 方法2：修改求二叉树深度的方法：
     * 只要有某个结点不平衡，将一直返回-1直到root；否则就正常返回树的深度
     */
    public boolean isBalanced(TreeNode root) {
        return depth2(root) >= 0;
    }

    private int depth2(TreeNode root) {
        if (root == null) return 0;
        int left = depth2(root.left);
        int right = depth2(root.right);
        return left >= 0 && right >= 0 && Math.abs(left - right) <= 1 ? Math.max(left, right) + 1 : -1;
    }

    /**
     * 方法3：后序遍历，为了传引用使用了对象数组
     */
    public boolean IsBalanced_Solution(TreeNode root) {
        return isBalance(root, new int[1]);
    }

    public boolean isBalance(TreeNode root, int[] depth) {
        if (root == null) {
            depth[0] = 0;
            return true;
        }
        boolean left = isBalance(root.left, depth);
        // 左子树的深度
        int leftDepth = depth[0];
        // 右子树的深度
        boolean right = isBalance(root.right, depth);
        int rightDepth = depth[0];
        // 当前结点的深度
        depth[0] = Math.max(leftDepth + 1, rightDepth + 1);
        if (left && right && Math.abs(leftDepth - rightDepth) <= 1) return true;
        return false;
    }
}
