# 剑指offer面试题27--对称的二叉树

> ```
> 请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
> ```

对称的二叉树，结点数必然是奇数，特别地定义空树也是对称的。当对称树的结点值不是完全相同时比较好处理，但是当结点值全部一样时候可能会有些麻烦。我们要实现一个通用的算法，使得对于这种特殊情况也能正确处理。

要保证树是对称的，左子树最左边的结点要和右子树最右边的结点值相同...左子树的右子结点要和右子树的左子结点值相同，即

```java
root1.left == root2.right;
root1.right == root2.left;
```

这是关键，理解了上面说的，可以很轻松地写出下面的代码。

## 递归版本

先来看递归版本，递归的实现一般比较简单、清晰。

```java
package Chap4;

import java.util.LinkedList;
import java.util.Queue;

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

```

如果遇到两棵子树都为空流返回true，这句代码隐含了**“空树也是对称的”**这样的信息。否则，只有一子树为空另一不为空，显然不是对称的；如果两个子树都不为空，比较它俩根结点的值，不相同肯定不是对称的。之后递归地对树的子树进行上述判断，直到检查到叶子结点，如果都满足就返回true。

## 非递归版本

思路和上面一样，非递归实现需要用到两个队列。**队列A专门存入左子树，队列B专门存入右子树。**

入列时，**将左子树的左子结点和右子树的右子结点分别存入队列A和队列B，紧接着还要将左子树的右子结点和右子树的左子结点分别存入队列A和队列B。**

出列时，两个队列同时出列一个元素，根据存入的顺序，这两个出列元素就是左子树的左子结点和右子树的右子结点，或者左子树的右子结点和右子树的左子结点。之后对这两个元素进行比较，比较操作和上面递归版本的一样。

```java
package Chap4;

import java.util.LinkedList;
import java.util.Queue;

public class SymmetricalTree {
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
}

```

---

by @sunhaiyu

2018.1.4