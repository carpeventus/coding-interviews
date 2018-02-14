# 剑指offer面试题30--包含min方法的栈

> ```
> 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的min函数。要求push、pop、min方法的时间复杂度都为O(1)
> ```

有些很容易想到的方法：比如每次想获得栈中的最小元素，将栈中所有元素复制到另一个数据结构中（比如List），然后对这个列表排序可以很简单地得到最小值。但时间复杂度肯定就不是O(1)了。

或者设置一个全局变量min，每次push都和当前最小值比较，如果更小就更新min，否则min不变。但是这种方法有个问题：要是pop出栈的元素正好就是这个min呢，那新的min是多少？我们很难得知，所以另辟蹊径。考虑到要求我们用O(1)的时间复杂度。可以**考虑用空间换时间**，试试使用辅助空间。

**定义一个栈stackMin，专门用于存放当前最小值。**

- 存放数据的stack存入当前元素，如果即将要存入的元素比当前最小元素还小，stackMin存入这个新的最小元素；否则，stackMin将当前最小元素再次存入。
- stack出栈时，stackMin也出栈。

反正就是入栈时，两个栈都有元素入栈；出栈时，两个栈都弹出一个元素。这两个栈总是同步进出栈的。

```java
package Chap4;

import java.util.LinkedList;

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

```

我们来模拟一下：

| 出入栈  | stack   | stackMin   | min  |
| ---- | ------- | ---------- | ---- |
| 压入5  | 5       | 5          | 5    |
| 压入4  | 5, 4    | 5, 4       | 4    |
| 压入6  | 5, 4, 6 | 5, 4, 4    | 4    |
| 压入3  | 5,4,6,3 | 5, 4, 4, 3 | 3    |
| 弹出   | 5,4,6   | 5, 4, 4    | 4    |
| 弹出   | 5, 4    | 5, 4       | 4    |
| 弹出   | 5       | 5          | 5    |

举点例子模拟能很好地帮助我们理解。

---

by @sunhaiyu

2018.1.5
