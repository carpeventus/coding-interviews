package Chap4;

/**
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。假设输入的数组的任意两个数字都互不相同。
 */
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
