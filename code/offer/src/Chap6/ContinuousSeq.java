package Chap6;

import java.util.ArrayList;

/**
 * 和为s的连续正数序列
 * 输入一个正数s，打印出所有何为s的连续正数序列（至少含有两个数）。
 * 例如输入15，由于1+2+3+4+5 = 4+5+6 = 7+8,所有打印出三个连续的序列1~5,4~6,7~8
 */
public class ContinuousSeq {
    public ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        if (sum <= 2) return list;
        int small = 1;
        int big = 2;
        int curSum = small + big;
        int mid = (sum + 1) / 2;
        while (small < mid) {
            while (curSum > sum && small < mid) {
                curSum -= small;
                small++;
            }
            if (curSum == sum) list.add(addFromSmallToBig(small, big));
            big++;
            curSum += big;
        }
        return list;
    }

    /**
     * 对一个连续区间内的数字求和
     */
    private ArrayList<Integer> addFromSmallToBig(int small, int big) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = small; i<= big; i++) {
            list.add(i);
        }
        return list;
    }

    public static void main(String[] args) {
        ContinuousSeq continuousSeq = new ContinuousSeq();
        System.out.println(continuousSeq.FindContinuousSequence(9));
    }
}
