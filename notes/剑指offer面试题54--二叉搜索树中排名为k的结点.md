# 剑指offer面试题54--二叉搜索树中排名为k的结点

>   ```
>   给定一颗二叉搜索树，请找出排名第k的结点。
>   ```

注意是二叉搜索树，这说明对于任何结点，有父结点大于其左子结点且小于右子结点。**如果中序遍历这棵树，就能得到递增排序的序列。**

接下来就很简单了，只需中序遍历到第k个结点，然后立即返回就行了。感觉对于这道题，非递归的中序遍历更好写一点。

```java
package Chap6;

import java.util.LinkedList;

public class KthNode {
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
              	// 一个计数器，遍历到第k个就立即返回
                if (++count == k) return pRoot;
                pRoot = pRoot.right;
            }
        }
        return null;
    }
}

```

---

by @sunhaiyu

2018.1.29
