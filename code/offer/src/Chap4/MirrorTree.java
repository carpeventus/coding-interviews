package Chap4;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 操作给定的二叉树，将其变换为源二叉树的镜像。
 */
public class MirrorTree {
    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    /**
     * 递归版本
     */
    public void mirrorRecur(TreeNode root) {
        exchangeChildren(root);
    }

    private void exchangeChildren(TreeNode node) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            return;
        }
        // 交换两个子结点
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;

        if (node.left != null) exchangeChildren(node.left);
        if (node.right != null) exchangeChildren(node.right);
    }

    /**
     * 非递归版本,层序遍历
     */
    public void mirror(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            // 交换两个子结点
            if (node.left != null || node.right != null) {
                TreeNode temp = node.left;
                node.left = node.right;
                node.right = temp;
            }
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
    }

    /**
     * 非递归，前序遍历
     */
    public void mirror_preOrder(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        // 当前结点不为空，或者为空但有可以返回的父结点（可以进行pop操作）都可以进入循环
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                // 交换两个子结点
                if (root.left != null || root.right != null) {
                    TreeNode temp = root.left;
                    root.left = root.right;
                    root.right = temp;
                }
                root = root.left;
            }

            if (!stack.isEmpty()) {
                root = stack.pop();
                root = root.right;
            }
        }
    }

}
