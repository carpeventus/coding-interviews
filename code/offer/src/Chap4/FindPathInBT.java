package Chap4;

import java.util.ArrayList;

/**
 * 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
 * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
 */
public class FindPathInBT {
    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    /**
     *
     * @param root 二叉树根结点
     * @param target 目标值
     * @return 所有和目标值相同的路径上结点的集合
     */
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> pathList = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();

        preOrder(root, pathList, path, target);
        return pathList;
    }
    // 要记录从根结点到叶子结点的路径，需要深度优先DFS，只有前序遍历满足先存入根结点
    private void preOrder(TreeNode node, ArrayList<ArrayList<Integer>> pathList, ArrayList<Integer> path, int target) {
        if (node == null) return;
        // 模拟结点进栈
        path.add(node.val);
        // 只有在叶子结点处才判断是否和目标值相同，若相同加入列表中
        if (node.left == null && node.right == null) {
            if (judgeEqual(path, target)) {
                pathList.add(new ArrayList<>(path));
            }
        }

        preOrder(node.left, pathList, path, target);
        preOrder(node.right, pathList, path, target);
        // 模拟结点出栈
        path.remove(path.size() - 1);

    }

    /**
     * @param path 某条根结点到叶子结点的路径上的所有结点
     * @param target 目标值
     * @return 路径上结点值的和与目标值是否相同
     */
    private boolean judgeEqual(ArrayList<Integer> path, int target) {
        int sum = 0;
        for (int v : path) {
            sum += v;
        }
        return sum == target;
    }


}
