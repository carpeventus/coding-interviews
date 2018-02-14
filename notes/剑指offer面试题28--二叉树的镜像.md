# 剑指offer面试题28--二叉树的镜像

> ```
> 操作给定的二叉树，将其变换为原二叉树的镜像。
> ```

思路很简单：从根结点开始，先交换根结点的左右子结点（子树），之后再对子结点进行同样的操作，一直往下直到树的叶子结点。叶子结点没有左右子结点，所以到这里停止交换。

## 前序遍历的递归版本

二叉树的前序前序遍历可以实现上述思路。

```java
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
}
```

第一个if如果根结点为空，就直接返回；第二个if是说如果遇到了叶子结点，无需操作直接返回。

## 非递归版本

我们发现要保证以**从上到下**的顺序交换左右子树，即先交换树的父结点，再交换父结点的两个子结点。**除了前序遍历，层序遍历也可以实现。**下面来实现两种遍历的非递归版本。

```java
package Chap4;

import java.util.LinkedList;
import java.util.Queue;

public class MirrorTree {
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

```

**前序遍历是深度优先搜索，所以用到了栈；而层序遍历是广度优先搜索，用到队列。**

前序遍历是只要某结点还有左子结点，就不断压入栈中，直到没有左子结点时弹栈，接着将根结点指向右子树重复上述过程。

层序遍历很简单，先将根结点入列，然后弹出根结点将根结点子结点入列，不断重复上述过程直到队列为空。

---

by @sunhaiyu

2018.1.4
