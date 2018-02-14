package Chap4;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 按照Z字形打印二叉树，即先打印根结点，然后从右往左打印第二层，从左往右打印第三层...以此类推
 */
public class PrintTreeZ {
    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    public void printTreeZ(TreeNode root) {
        if (root == null) return;
        LinkedList<TreeNode> stackOdd = new LinkedList<>();
        LinkedList<TreeNode> stackEven = new LinkedList<>();
        stackOdd.push(root);
        // 只要还有一个栈不为空就继续
        while (!stackOdd.isEmpty() || !stackEven.isEmpty()) {
            if (!stackOdd.isEmpty()) {
                while (!stackOdd.isEmpty()) {
                    TreeNode node = stackOdd.pop();
                    System.out.println((node.val + " "));
                    if (node.left != null) stackEven.push(node.left);
                    if (node.right != null) stackEven.push(node.right);
                }
            } else {
                while (!stackEven.isEmpty()) {
                    TreeNode node = stackEven.pop();
                    System.out.println((node.val + " "));
                    if (node.right != null) stackOdd.push(node.right);
                    if (node.left != null) stackOdd.push(node.left);
                }
            }
            System.out.println();
        }
    }

    public ArrayList<ArrayList<Integer>> print(TreeNode root) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        LinkedList<TreeNode> stackOdd = new LinkedList<>();
        LinkedList<TreeNode> stackEven = new LinkedList<>();
        stackOdd.push(root);
        // 只要还有一个栈不为空就继续
        while (!stackOdd.isEmpty() || !stackEven.isEmpty()) {
            ArrayList<Integer> layerList = new ArrayList<>();
            if (!stackOdd.isEmpty()) {
                while (!stackOdd.isEmpty()) {
                    TreeNode node = stackOdd.pop();
                    layerList.add(node.val);
                    if (node.left != null) stackEven.push(node.left);
                    if (node.right != null) stackEven.push(node.right);
                }
            } else {
                while (!stackEven.isEmpty()) {
                    TreeNode node = stackEven.pop();
                    layerList.add(node.val);
                    if (node.right != null) stackOdd.push(node.right);
                    if (node.left != null) stackOdd.push(node.left);
                }
            }
            list.add(layerList);
        }
        return list;
    }

}
