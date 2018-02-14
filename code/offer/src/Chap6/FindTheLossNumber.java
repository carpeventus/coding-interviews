package Chap6;

/**
 * 0~n-1中缺失的数
 * 一个长度为n -1的递增排序数组中的所有数字都是唯一的，并且每个数字的都在范围0~n-1之内。
 * 在范围内0~n-1内的n个数字中有且只有一个数字不在该数组中，找出这个数字
 */
public class FindTheLossNumber {
    public int findLoss(int[] array) {
        if (array == null) return -1;
        int low = 0;
        int len = array.length;
        int high = len - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid != array[mid]) {
                if (mid == 0 || mid -1 == array[mid -1]) return mid;
                else high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if (low == len) return len;
        //  无效的输入数组，如不是递增排序，或者有的数字超出了0~n-1的范围
        return -1;
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        FindTheLossNumber findTheLossNumber = new FindTheLossNumber();
        System.out.println(findTheLossNumber.findLoss(a));
    }
}
