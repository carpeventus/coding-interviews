package Chap6;

import java.util.ArrayList;

/**
 * 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S；
 * 如果有多对数字的和等于S，输出两个数的乘积最小的。
 */
public class TwoSum {
    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> list = new ArrayList<>();
        if (array == null || array.length <= 1) return list;

        int low = 0;
        int high = array.length - 1;

        while (low < high) {
            if (array[low] + array[high] == sum) {
                list.add(array[low]);
                list.add(array[high]);
                break;
            } else if (array[low] + array[high] > sum)
                high--;
            else low++;
        }
        return list;
    }
}
