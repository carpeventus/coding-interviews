package Chap2;

import java.util.Arrays;

/**
 * 将有序数组B归并到有序数组A中（A能容纳下B），归并后的A数组也是有序的
 */
public class MergeTwoSortedArray {
    public static void merge(Integer[] a, Integer[] b) {
        // 统计a数组中有值元素的个数
        int len = 0;
        for (int i = 0;i < a.length;i++) {
            if (a[i] != null) {
                len++;
            }
        }
        int pA = len-1; // 指向a数组不为空的最后一个元素末尾
        int pB = b.length-1; // 指向b数组不为空的最后一个元素末尾
        int k = len + b.length -1; // 指向归并后数组不为空的最后一个元素末尾

        while (k >= 0) {
            // a数组取完了，取b数组中的
            if (pA < 0) {
                a[k--] = b[pB--];
                // b数组取完了，取a数组中的
            } else if (pB < 0) {
                a[k--] = a[pA--];
                // 否则谁的大取谁的值
            } else if (a[pA] > b[pB]) {
                a[k--]= a[pA--];
            } else {
                a[k--] = b[pB--];
            }
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[10];
        Integer[] b= {1, 3, 5, 7, 9};
        // {2, 4, 6, 8, 10}
        for (int i = 0; i < 5; i++) {
            a[i] = 2 * i + 2;
        }

        merge(a, b);
        System.out.println(Arrays.toString(a));
    }
}
