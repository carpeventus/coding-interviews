package Chap6;

import java.util.LinkedList;
import java.util.List;

/**
 * 0, 1, 2...,n - 1这n个数字排成一个圆圈，一开始从数字0开始，从这个圆圈里删除第m个数字；然后从被删除的数字后一位开始计数，继续删除第m个数字...
 * 重复这个过程，直到最后只剩一个数字为止。求出这个圆圈里剩下的最后一个数字。
 */
public class LastInCircle {
    public int lastRemaining(int n, int m) {
        if (n <= 0 || m <= 0) return -1;
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) list.add(i);
        int p = 0;
        while (list.size() > 1) {
            for (int k = 0; k < m - 1; k++) {
                p++;
                // 走到链表尾部时
                if (p == list.size()) p = 0;
            }
            list.remove(p);
            // 删除的正好是链表的最后一个元素
            if (p == list.size()) p = 0;
        }

        return list.get(0);
    }

    public int LastRemaining_Solution(int n, int m) {
        if (n <= 0 || m <= 0) return -1;
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) list.add(i);
        int removeIndex = 0;
        while (list.size() > 1) {
            removeIndex = (removeIndex + m - 1) % list.size();
            list.remove(removeIndex);
        }
        return list.get(0);
    }

    /**
     * 数学规律：约瑟夫环问题
     */
    public int lastNumInCycle(int n, int m) {
        if (n <= 0 || m <= 0) return -1;
        int f = 0;
        for (int i = 2; i <= n; i++) {
            f = (f + m) % i;
        }
        return f;
    }


    public static void main(String[] args) {
        LastInCircle lastInCircle = new LastInCircle();
        System.out.println(lastInCircle.lastRemaining(5, 3));
    }
}
