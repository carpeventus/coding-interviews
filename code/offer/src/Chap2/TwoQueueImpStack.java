package Chap2;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 两个队列实现一个栈
 */
public class TwoQueueImpStack {
    private Queue<Integer> a;
    private Queue<Integer> another;

    public TwoQueueImpStack() {
        this.a = new LinkedList<>();
        this.another = new LinkedList<>();
    }

    public void push(int node) {
        if (a.isEmpty() && another.isEmpty()) {
            a.offer(node);
        } else if (!a.isEmpty()){
            a.offer(node);
        } else {
            another.offer(node);
        }
    }

    public int pop() {
        if (a.isEmpty() && another.isEmpty()) {
            throw new RuntimeException("栈已空");
        }

        if (!another.isEmpty()) {
            int size = another.size();
            // 除了最后一个元素，其他都删除并复制到另外一个队列中
            for (int i = 0; i < size - 1; i++) {
                a.offer(another.poll());
            }
            return another.poll();
        } else {
            int size = a.size();
            for (int i = 0; i < size -1; i++) {
                another.offer(a.poll());
            }
            return a.poll();
        }
    }


    public static void main(String[] args) {
        TwoQueueImpStack a = new TwoQueueImpStack();
        a.push(54);
        a.push(55);
        a.push(56);
        System.out.println(a.pop());
        System.out.println(a.pop());
        a.push(53);
        System.out.println(a.pop());
        a.push(52);
        System.out.println(a.pop());
        System.out.println(a.pop());
    }
}
