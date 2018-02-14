# 剑指offer面试题26--树的子结构

> ```
> 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
> ```

二叉树这种递归的数据结构。想到用递归的方法解决是很自然的。

首先我们要在二树A中找到和树B根结点值一样的结点R，结点R可能有多个，因为树A中的结点值可能不止一个与树B的根结点值相同。对于每个结点R，其子树都有可能是树A的子结构，因此**只要还未在树A中找到一个子结构，就要继续遍历树A判断其他R结点。**

对于某一个R结点，我们要求根结点R的左子结点、右子结点和树B根结点的左子结点、右子结点分别相等（值相等），而且R的子结点的左右子结点也要和B的子结点值相等......直到和树B的叶子结点也相等才说树A包含树B。这是个递归的过程。

对于这两个步骤，可写出如下

```java
package Chap3;

public class SubTree {

    private class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;

        }

    }

    public boolean hasSubTree(TreeNode root1,TreeNode root2) {
        boolean result = false;
        if (root1 != null && root2 != null) {
            // 根结点相同，紧接着判断子树结构一样不
            if (root1.val == root2.val) {
                // 如果子树结构一样，返回true，也不用再对树A遍历了
                result = doesTree1HaveTree2(root1, root2);
            }
            // 当前根结点不同，或者即使根结点相同子树结构不同，还需继续遍历，递归判断左右子树
            if (!result) {
                result = hasSubTree(root1.left, root2);
            }

            if (!result) {
                result = hasSubTree(root1.right, root2);
            }
        }

        return result;
    }

    private boolean doesTree1HaveTree2(TreeNode node1,TreeNode node2) {
        // node2到达叶子结点的左右子结点了都还相等，说明是树1的子结构
        if (node2 == null) {
            return true;
        }
        // 如果node2没有到叶子结点的左右子结点，而node1先到了说明树2比树1还大，返回false
        if (node1 == null) {
            return false;
        }

        if (node1.val != node2.val) {
            return false;
        }
	   // 递归比较其左右子结点，左右子结点必须都相等所以用的是&&
        return doesTree1HaveTree2(node1.left, node2.left) && doesTree1HaveTree2(node1.right, node2.right);
    }
}

```

有一点要注意，下面**两个if的判断顺序不可颠倒**。如果node2没有左右子结点了，说明叶子结点也和树B比较过了且相等，说明树A包含树B，因此返回true。如果第一个if没通过，到第二个if，说明node2还没到叶子结点，但现在node1却先于树B到达了叶子结点，说明树A该处的子树不包含树B，需要继续遍历树A。

```java
// node2到达叶子结点的左右子结点了都还相等，说明是树1的子结构
if (node2 == null) {
  	return true;
}
// 如果node2没有到叶子结点的左右子结点，而node1先到了说明树2比树1还大，返回false
if (node1 == null) {
  	return false;
}
```

---

by @sunhaiyu

2017.12.27
