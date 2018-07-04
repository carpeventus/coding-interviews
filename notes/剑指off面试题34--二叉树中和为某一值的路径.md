# 剑指off面试题34--二叉树中和为某一值的路径

> ```
> 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
> ```

很自然的想法是每条路径都和目标值比较，如果相同就将这条路径存入一个集合中，本质上是个**深度优先搜索**。

路径可以用一个列表表示，因为满足要求的路径首先要是从根结点到叶子结点的一个结点序列，而只有前序遍历能先存入根结点。使用前序遍历，每访问一个结点随即就存入到当前路径中，直到访问并存入叶子结点后，将当前路径中结点的值么目标值进行比较，如果相同就存入一个集合中，当左叶子结点那条路径比较后，就切换到右叶子结点（叶子结点）那条路径，此时需要从列表中移除刚存入的左叶子结点，而且当左右叶子结点的路径都比较完后，需要返回上层结点，得继续从列表的末尾移除结点。也就是当前路径始终存放的是**从根结点到当前结点**的所有结点。

表示路径的列表可以用栈，但是在打印路径时，由于要先打印出根结点，而栈中根结点在栈底，不好获取到。因此采用ArrayList就好了，可以像栈一样删除末尾元素，也可以很方便地访问到先存入的根结点。

```java
package Chap4;

import java.util.ArrayList;

public class FindPathInBST {
    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    /**
     * @param root 二叉树根结点
     * @param target 目标值
     * @return 所有和目标值相同的路径上结点的集合
     */
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(root == null) return res;
        ArrayList<Integer> path = new ArrayList<>();
        preOrder(root, target, path, res);
        return res;
    }

    private void preOrder(TreeNode root, int curVal,ArrayList<Integer> path, ArrayList<ArrayList<Integer>> res) {
        if (root == null) return;
        // 模拟结点进栈
        path.add(root.val);
        curVal -= root.val;
        // 只有在叶子结点处才判断是否和目标值相同，若相同加入列表中
        if (root.left == null && root.right == null) {
            if (curVal == 0) res.add(new ArrayList<>(path));
        }
        preOrder(root.left, curVal, path, res);
        preOrder(root.right, curVal, path, res);
        // 模拟结点出栈
        path.remove(path.size() - 1);
        curVal += root.val;
    }
}
```

最为关键的就是这句`path.remove(path.size() - 1);`它**模拟了递归调用方法结束，当前访问结点的出栈。**

---

by @sunhaiyu

2018.1.11