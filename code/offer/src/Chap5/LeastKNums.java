package Chap5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 输入n个整数，找出其中最小的K个数。
 * 例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
 */
public class LeastKNums {
    /**
     * 方法1：基于快排的切分
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k > input.length || k <= 0) return list;
        select(input, k - 1);
        for (int i = 0; i < k; i++) {
            list.add(input[i]);
        }
        return list;
    }

    /**
     * 选择排名为k的元素,只是部分排序，时间复杂度为O(N)
     */
    private int select(int[] array, int k) {
        int low = 0;
        int high = array.length - 1;
        // high==low时只有一个元素，不切分
        while (high > low) {
            int j = partition(array, low, high);
            if (j == k) return array[k];
            else if (j > k) high = j - 1;
            else if (j < k) low = j + 1;
        }

        return array[k];
    }

    /**
     * 快速排序的切分方法
     */
    private int partition(int[] array, int low, int high) {
        int i = low;
        int j = high + 1;
        int v = array[low];
        while (true) {
            while (array[++i] < v) if (i == high) break;
            while (array[--j] > v) if (j == low) break;
            if (i >= j) break;
            swap(array, i, j);
        }
        swap(array, low, j);
        return j;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * 方法2：基于最小堆
     */
    public ArrayList<Integer> leastK(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k > input.length || k == 0) return list;

        int N = input.length;
        // 堆的构造
        for (int i = N / 2; i >= 1; i--) {
            sink(input, i, N);
        }

        // 和堆顶交换k次
        for (int i = 0; i < k; i++) {
            // 存入堆顶的最小元素
            list.add(input[0]);
            swapForHeap(input, 1, N--);
            sink(input, 1, N);
        }
        return list;
    }

    private void sink(int[] arr, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            // 左右子结点中选择小的
            if (j < N && greater(arr, j, j + 1)) j++;
            if (!greater(arr, k, j)) break;
            swapForHeap(arr, k, j);
            k = j;
        }
    }

    private void swapForHeap(int[] array, int i, int j) {
        int temp = array[i - 1];
        array[i - 1] = array[j - 1];
        array[j - 1] = temp;
    }

    private boolean greater(int[] arr, int i, int j) {
        return arr[i - 1] > arr[j - 1];
    }

    /**
     * 上面两个方法都改变了输入数组
     * 直接使用Java内置的优先队列
     */
    public ArrayList<Integer> getLeastK(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k > input.length || k == 0) return list;

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int a : input) {
            maxHeap.offer(a);
            // 只要size大于k，不断剔除最大值，最后优先队列里只剩最小的k个
            if (maxHeap.size() > k) maxHeap.poll();
        }
        list.addAll(maxHeap);

        return list;
    }

}
