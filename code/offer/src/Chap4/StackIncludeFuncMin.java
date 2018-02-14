package Chap4;

import java.util.LinkedList;

/**
 * 实现栈的数据结构，包含min方法可以以O(1)的时间复杂度获得栈中的最小值
 */
public class StackIncludeFuncMin {
    private LinkedList<Integer> stack = new LinkedList<>();
    // 辅助栈，用于存储当前最小值
    private LinkedList<Integer> stackMin = new LinkedList<>();

    public void push(int node) {
        stack.push(node);
        if (stackMin.isEmpty() || node < stackMin.peek()) {
            stackMin.push(node);
        } else {
            stackMin.push(stackMin.peek());
        }
    }

    public void pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("stack is empty!");
        }
        stack.pop();
        stackMin.pop();
    }

    public int top() {
        if (stack.isEmpty()) {
            throw new RuntimeException("stack is empty!");
        }
        return stack.peek();
    }

    public int min() {
        if (stackMin.isEmpty()) {
            throw new RuntimeException("stack is empty!");
        }
        return stackMin.peek();
    }
}
