package Chap6;

import java.util.LinkedList;

/**
 * 给定一颗二叉搜索树，请找出排名第k的结点。
 */
public class KthNode {
    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }
    }

    public TreeNode findKthNode(TreeNode pRoot, int k) {
        if (pRoot == null || k <= 0) return null;
        LinkedList<TreeNode> stack = new LinkedList<>();
        int count = 0;
        while (pRoot != null || !stack.isEmpty()) {
            while (pRoot != null) {
                stack.push(pRoot);
                pRoot = pRoot.left;
            }
            if (!stack.isEmpty()) {
                pRoot = stack.pop();
                if (++count == k) return pRoot;
                pRoot = pRoot.right;
            }
        }
        return null;
    }
}
