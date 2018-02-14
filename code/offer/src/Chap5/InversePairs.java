package Chap5;

/**
 * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。
 * 输入一个数组,求出这个数组中的逆序对的总数
 */
public class InversePairs {
    public int inversePairs(int[] array) {
        if (array == null) return 0;
        int[] aux = new int[array.length];
        return sort(array, aux, 0, array.length - 1);
    }

    private int sort(int[] array, int[] aux, int low, int high) {
        if (high <= low) return 0;
        int mid = low + (high - low) / 2;
        int left = sort(array, aux, low, mid);
        int right = sort(array, aux, mid + 1, high);
        int merged = merge(array, aux, low, mid, high);
        return left + right + merged;
    }

    private int merge(int[] array, int[] aux, int low, int mid, int high) {
        int count = 0;
        int len = (high - low) / 2;
        int i = mid;
        int j = high;

        for (int k = low; k <= high; k++) {
            aux[k] = array[k];
        }

        for (int k = high; k >= low; k--) {
            if (i < low) array[k] = aux[j--];
            else if (j < mid + 1) array[k] = aux[i--];
            else if (aux[i] > aux[j]) {
                // 在归并排序的基础上，在这里求count
                count += j - low - len;
                array[k] = aux[i--];
            } else array[k] = aux[j--];
        }
        return count;
    }
}
