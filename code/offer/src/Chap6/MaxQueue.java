package Chap6;

import java.util.Deque;
import java.util.Queue;
import java.util.LinkedList;

/**
 * 定义一个队列，实现max方法得到队列中的最大值。
 * 要求入列、出列以及邱最大值的方法时间复杂度都是O(1)
 */
public class MaxQueue {

    private Queue<Integer> queue;
    private Deque<Integer> maxQueue;


    public MaxQueue() {
        queue = new LinkedList<>();
        maxQueue = new LinkedList<>();
    }

    public int max_value() {
        if (maxQueue.isEmpty()) {
            return -1;
        }
        return maxQueue.peekFirst();
    }

    public void push_back(int value) {
        queue.offer(value);
        while (!maxQueue.isEmpty() && value > maxQueue.peekLast()) {
            maxQueue.pollLast();
        }
        maxQueue.offerLast(value);

    }

    public int pop_front() {
        if (queue.isEmpty()) {
            return -1;
        }
        int tmp =  queue.poll();
        if (tmp == maxQueue.peekFirst()) {
            maxQueue.pollFirst();
        }
        return tmp;
    }

}

