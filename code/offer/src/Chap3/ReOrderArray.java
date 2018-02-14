package Chap3;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，
 * 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 */
public class ReOrderArray {
    public void reOrderArray(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        int len = array.length;
        // 左往右扫描的指针
        int p = 0;
        // 辅助数组
        int[] temp = new int[len];
        // 左往右扫描，只存入奇数
        for (int i = 0; i < len; i++) {
            if (isOdd(array[i]))
                temp[p++] = array[i];
        }
        // 再次扫描，只存入偶数
        for (int i = 0; i < len; i++) {
            if (!isOdd(array[i]))
                temp[p++] = array[i];
        }
        // 覆盖原数组
        for (int i = 0; i < len; i++) {
            array[i] = temp[i];
        }
    }

    private boolean isOdd(int val) {
        return (val & 1) == 1;
    }

    public void reOrderArray(int[] array, Predicate<Integer> p) {
        if (array == null || array.length == 0) {
            return;
        }
        int pBegin = 0;
        int pEnd = array.length - 1;
        while (pBegin < pEnd) {
            // 左到右找出第一个偶数
            while (pBegin < pEnd && p.test(array[pBegin]))
                pBegin++;
            // 右到左找出第一个奇数
            while (pBegin < pEnd && !p.test(array[pEnd]))
                pEnd--;

            if (pBegin < pEnd) {
                int temp = array[pBegin];
                array[pBegin] = array[pEnd];
                array[pEnd] = temp;
            }
        }
    }

    public static void main(String[] args) {
        ReOrderArray reOrder = new ReOrderArray();
        int[] a = {3, 2, 1, 9, 8, 7, 4, 5, 6};
        reOrder.reOrderArray(a, p -> (p & 1) == 1);
        System.out.println(Arrays.toString(a));
    }
}
