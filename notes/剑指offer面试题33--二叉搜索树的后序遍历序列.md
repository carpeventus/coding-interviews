# 剑指offer面试题33--二叉搜索树的后序遍历序列

> ```
> 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
> ```

注意是二叉搜索（查找）树，特点是父结点的左子树都比父结点小，父结点的右子树都比父结点大。因此其后序遍历的序列有一定的规律：

- 序列最后一位必然是树的根结点；
- 序列前部分是根结点的左子树，后部分是根结点的右子树；具体来说：将序列各个元素和和序列最后一位的根结点比较，序列前部分都小于根结点的值，这部分子序列是左子树；序列后部分的值都大于根结点，这部分子序列是右子树；

**根据二叉树的递归结构，可以将树分成左子树、根结点、右子树，对于每棵子树可以继续按照上面的方式分下去。于是原序列可被划分为若干个子序列，每个子序列表示一棵子树。**

比如对于一个序列{5, 7, 6, 9, 11, 10, 8}，根结点为8，{5, 7, 6}因为都小于8所以是8的左子树，而{9, 11, 10}都大于8所以是8的右子树，而对于子树{5,                                                                             7, 6}，最后一个元素6是根结点.....以此类推。

因此解决本题：首先要从序列中获得根结点，然后找到序列中左右子树的分界线，由此将序列分成三部分：左子树、根结点、右子树。再对左右子树进行递归操作。递归终止的条件是：上层结点只有左子树或只有右子树，或者当前结点是叶子结点，即没有子结点。这两种情况都应该返回true（可以画图举几个例子模拟下，理解为什么）

思路清晰后，程序就好写了，如下：

```java
package Chap4;

public class VeritySeqOfSearchBST {
    public boolean verifySquenceOfBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) return false;
        return isSearchBST(sequence, 0, sequence.length - 1);
    }

    private boolean isSearchBST(int[] seq, int begin, int end) {
        // begin比end大说明上层结点没有左子树或者右子树，begin == end说明该本层结点没有子树，无需比较了
        // 这两种情况都应该返回true
        if (begin >= end) return true;

        int rootVal = seq[end];
        int i = begin;
        // 左子树都比root小
        while (i < end && seq[i] < rootVal) {
            i++;
        }
        // 找到了左右子树的分界，[begin, boundary-1]为左子树，[boundary, end -1]是右子树
        int boundary = i;
        while (i < end) {
            // 右子树中还存在比root小的说明不是二叉搜索树
            if (seq[i] < rootVal) return false;
            i++;
        }
        // 左右子树必须同时都是二叉搜索树
        return isSearchBST(seq, begin, boundary - 1) && isSearchBST(seq, boundary, end - 1);
    }
}

```

递归中主要判断某个序列或者子序列构成的树是不是二叉搜索树，在定下来左右子树的分界线后，如果右子树中还存在比root小的说明该子树不是二叉搜索树，直接返回false；注意必须满足**左右子树都是二叉搜索树**。

---

by @sunhaiyu

2018.1.11