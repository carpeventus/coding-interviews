package Chap6;

import java.util.*;

/**
 * 题目1：滑动窗口的最大值。
 * 给定一个数组和滑动窗口的大小，请找出所有滑动窗口里的最大值。
 * 例如，如果输入数组{2, 3, 4, 2, 6, 2, 5}以及滑动窗口的大小3，那么一共存在6个滑动窗口
 * 他们的最大值分别为{4,4,6,6,6,5}
 */
public class MaxInWindow {
    /**
     * 方法1：使用优先队列
     */
    public ArrayList<Integer> maxInWindows(int[] num, int size) {
        ArrayList<Integer> list = new ArrayList<>();
        if (num == null || num.length < size || size <= 0) return list;
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        int j = 0;
        for (int i = 0; i < num.length; i++) {
            maxHeap.offer(num[i]);
            if (maxHeap.size() >= size) {
                list.add(maxHeap.peek());
                maxHeap.remove(num[j++]);
            }
        }
        return list;
    }
    /**
     * 方法2: 使用双端队列，存放下标
     */
    public ArrayList<Integer> maxInWindow2(int[] num, int size) {
        ArrayList<Integer> list = new ArrayList<>();
        if (num == null || num.length < size || size <= 0) return list;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < num.length; i++) {
            while (!deque.isEmpty() && num[i] >= num[deque.peekLast()]) deque.pollLast();
            if (!deque.isEmpty() && i - deque.peekFirst() >= size) deque.pollFirst();
            deque.offerLast(i);
            if (i +1 >= size) list.add(num[deque.peekFirst()]);
        }
        return list;
    }
}