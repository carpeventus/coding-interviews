package Chap6;

/**
 * 统计一个数字在排序数组中出现的次数。
 */
public class NumOfK {
    /**
     * 方法一：遍历，O(n)复杂度，不推荐
     */
    public int getNumberOfK(int[] array, int k) {
        if (array == null) return 0;

        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == k) {
                count++;
            }
        }
        return count;
    }

    /**
     * 方法二：二分法找到第一个k和最后一个k，时间复杂度O(nlgn)
     */
    public int numberOfK(int[] array, int k) {
        if (array == null) return 0;
        int from = getFirstOfK(array, k, 0, array.length - 1);
        int to = getLastOfK(array, k, 0, array.length - 1);
        if (from == -1 && to == -1) return 0;
        else return to - from + 1;
    }

    private int getFirstOfK(int[] array, int k, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (k < array[mid]) high = mid - 1;
            else if (k > array[mid]) low = mid + 1;
            else {
                if (mid > 0 && array[mid - 1] == k) high = mid - 1;
                else return mid;
            }
        }
        return -1;
    }

    private int getLastOfK(int[] array, int k, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (k < array[mid]) high = mid - 1;
            else if (k > array[mid]) low = mid + 1;
            else {
                if (mid < array.length - 1 && array[mid + 1] == k) low = mid + 1;
                else return mid;
            }
        }
        return -1;
    }

    /**
     * 巧妙的方法3：得到与k相邻的两个浮点数排名
     * 因为数组中元素时int型的,改为查找浮点型的值，比如找3的次数，就找2.5和3.5之间的元素个数，
     * 稍微改变二分查找的返回值就能得到数组中排名为k的方法
     */
    public int numOfK(int[] array, int k) {
        if (array == null) return 0;
        return rank(array, k + 0.5) - rank(array, k - 0.5);
    }

    // 因为数组中元素都是int型的,改为查找浮点型的值，比如要获得3的出现次数，就找2.5和3.5之间的元素个数，
    // 稍微改变二分查找的返回值就能得到数组中排名为k的方法
    private int rank(int[] array, double k) {
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (k < array[mid]) high = mid - 1;
            else if (k > array[mid]) low = mid + 1;
        }
        return low;
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 3, 3, 3, 4, 5};
        NumOfK k = new NumOfK();
        System.out.println(k.numOfK(a, 3));
    }
}