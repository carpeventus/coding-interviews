package Chap4;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 请实现一个函数，用来判断一颗二叉树是不是对称的。
 * 注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
 */
public class SymmetricalTree {
    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    /**
     * 非递归，队列实现(栈也可以实现)
     */
    public boolean isSymmetrical(TreeNode pRoot) {
        if (pRoot == null) return true;

        Queue<TreeNode> queueA = new LinkedList<>();
        Queue<TreeNode> queueB = new LinkedList<>();
        queueA.offer(pRoot.left);
        queueB.offer(pRoot.right);
        TreeNode left = null;
        TreeNode right = null;

        while (!queueA.isEmpty() && !queueB.isEmpty()) {
            left = queueA.poll();
            right = queueB.poll();
            // 两个都空跳过
            if (left == null && right == null) continue;
            // 只有一个空，不对称
            if (left == null || right == null) return false;
            // 两个都不空，比较值
            if (left.val != right.val) return false;
            // 两两对称的加入
            // 左孩子的左孩子，右孩子的右孩子
            queueA.offer(left.left);
            queueB.offer(right.right);
            // 左孩子的右孩子，右孩子的左孩子
            queueA.offer(left.right);
            queueB.offer(right.left);
        }
        return true;
    }

    /**
     * 递归实现
     */
    public boolean isSymmetricalRecur(TreeNode pRoot) {
        if (pRoot == null) return true;
        return isSymmetrical(pRoot.left, pRoot.right);
    }

    private boolean isSymmetrical(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) return true;
        if (root1 == null || root2 == null) return false;
        if (root1.val != root2.val) return false;

        return isSymmetrical(root1.left, root2.right)
                && isSymmetrical(root1.right, root2.left);
    }
}
