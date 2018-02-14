package Chap4;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 逐层打印二叉树，即打印完一层后要有换行符
 */
public class PrintTreeEveryLayer {
    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    public void printEveryLayer2(TreeNode root) {
        if (root == null) {
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        // 下一层的结点数
        int nextLevel = 0;
        // 本层还有多少结点未打印
        int toBePrinted = 1;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.val + " ");
            // 每打印一个，减1
            toBePrinted--;
            // 每一个元素加入队列，加1
            if (node.left != null) {
                queue.offer(node.left);
                nextLevel++;
            }

            if (node.right != null) {
                queue.offer(node.right);
                nextLevel++;
            }
            // 本层打印完毕
            if (toBePrinted == 0) {
                System.out.println();
                toBePrinted = nextLevel;
                nextLevel = 0;
            }
        }
    }
    // 也是分行打印，比上面简洁
    public void printEveryLayer(TreeNode root) {
        if (root == null) return;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int layerSize = queue.size();
            for (int i = 0; i < layerSize; i++) {
                TreeNode node = queue.poll();
                System.out.println(node.val+" ");
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            System.out.println();
        }
    }

}
