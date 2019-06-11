# 剑指offer面试题7--重建二叉树

> ```
> 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。
> ```

由中序遍历序列和前序或后序任一序列就能确定唯一的一棵二叉树，中序遍历序列是必须要的。

二叉树本来就是一种递归的结构，某个父结点，你可以说它拥有一个左子结点和一个右子结点，也可以说它拥有一棵以左子结点为根的左子树和一颗右子结点为根的右子树。因此二叉树的构建采用递归的方法是一种很自然的想法。

一棵树从上到下可以分解成了左子树、根结点、右子树，对于每一棵子树，又可以继续分解下去...一直到树底的叶子结点。相反，树的构建是从下到上的顺序，叶子结点可以看做最小的子树，这些子树的根结点成为其他结点的左右子结点，于是产生了更大的子树，这些子树继续成为上层结点的左右子结点....一直到根结点的做右子结点也确定下来。

关键是要找到每棵树的根结点，**前序遍历的第一个结点就是树的根结点；在中序遍历里找到这个结点，其左边的结点都是根结点的左子树，其右边的结点都是根结点的右子树。假如根结点左边有M个结点，那么在前序序列中，根结点后的M个结点也是属于根结点的左子树的。前序序列中余下的后面的结点自然属于根结点的右子树**。这样就可以把中序遍历的数组从根结点处分解成左右子树（对应的有两个子数组），然后递归地对这两个子数组执行同样的操作。现在重点是要在子数组中找到根结点——它依然是数组的第一个元素！

比如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}。前序序列可知根结点是1，于是从中序序列可知，{4, 7, 2}属于根结点1的左子树，有3个结点；{5, 3 ,8, 6}属于根结点1的右子树。回到前序序列，除开第一个根结点，其后3个结点{2, 4, 7}就是左子树，余下的{3, 5, 6 ,8}是右子树。按照这些关系可以精确地将数组分解成两个子数组，递归地对两个数组进行同样的操作即可。

```java
package Chap2;


public class ReConstructTree {

    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode reConstructBinaryTree(int[] pre, int[] in) {
        TreeNode root = reConstructBinaryTree(pre, 0, pre.length - 1, in, 0, in.length - 1);
        return root;
    }

    /**
     * 递归！
     * [preStart + 1, preStart + i - inStart]是前序序列中左子树封闭区间
     * [preStart + i - inStart + 1, preEnd]是前序序列中右子树封闭区间
     *
     * [inStart, i - 1]是中序序列中左子树封闭区间
     * [i + 1, inEnd]是中序序列中右子树封闭区间
     *
     * @param pre 前序序列
     * @param preStart 前序序列封闭区间的左指针
     * @param preEnd   前序序列封闭区间的右指针
     * @param in  中序序列
     * @param inStart 中序序列封闭区间的左指针
     * @param inEnd   中序序列封闭区间的右指针
     * @return  树的根结点
     */
    private TreeNode reConstructBinaryTree(int[] pre, int preStart, int preEnd, int[] in, int inStart, int inEnd) {
        // 还能分解成子数组就继续递归，不能分解（表现为end > start）,就返回空子树给父结点
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        int rootVal = pre[preStart];
        TreeNode root = new TreeNode(rootVal);
        for (int i = inStart; i <= inEnd; i++) {
            if (in[i] == rootVal) {
                root.left = reConstructBinaryTree(pre, preStart + 1, preStart + i - inStart, in, inStart, i - 1);
                root.right = reConstructBinaryTree(pre, preStart + i - inStart + 1, preEnd, in, i + 1, inEnd);
            }
        }
        return root;
    }
}

```

---

by @sunhaiyu

2017.12.16