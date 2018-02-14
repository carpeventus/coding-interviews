# 剑指offer面试题9--两个栈实现队列

> ```
> 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
> ```

刚开始两个栈都是空的，要进队列，可以将元素压入任何一个栈，不妨就压入stack1中，我们知道元素在栈中是后进先出的，因此要出列需要删除stack1最底部的元素，此时stack2派上用场了，将stack1的元素再压入stack2中，现在元素的排列变成了原来插入的顺序，要出列的元素到了栈顶，要完成出列操作就很方便了。若要继续出列，只要stack2不为空，只需继续从stack2中出栈即可，**因为stack2中的栈顶元素就是最先入列的元素。若stack2为空，就将stack1中的元素压入到stack2后，再弹出栈顶元素。**如果要入列，就直接压入stack1中。

总结一下就是：

- stack1专门用于进入队列
- stack2专门用于出队列
- 出列操作。stack2不为空就直接出列；为空就将stack1中所有元素压入stack2中，再弹出栈顶元素。


```java
package Chap2;

import java.util.LinkedList;

/**
 * 两个栈实现一个队列
 */
public class TwoStackImpQueue {

    private LinkedList<Integer> stack1 = new LinkedList<>();
    private LinkedList<Integer> stack2 = new LinkedList<>();

    public void enqueue(int node) {
      // stack1专门用于进入队列
        stack1.push(node);
    }
	// 出列操作。stack2不为空就直接出列；为空就将stack1中所有元素压入stack2中，再弹出栈顶元素。
    public int dequeue() {
        if (stack1.isEmpty() && stack2.isEmpty()) {
            throw new RuntimeException("队列为空");
        }

        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
}

```

## 相关题目--两个队列实现栈

刚开始两个队列都为空，进栈时，可以进入任意一个队列。不妨默认进入`Queue a`。后续进栈时操作，哪个队列不为空（将看到，另外一个队列肯定为空）就进入该队列中。出栈操作，**最后进入队列的要先出栈**，而此时要出栈的元素在队列最后，但是队列只支持在队列头删除，因此将除了最后一个元素之外的所有元素都删除并复制一份到另一个队列`Queue another`，然后出列最后一个元素即可。此时`Queue a`成了空队列。之后每次出列操作都像上述以样：将不为空的队列中除最后一个元素的其余元素删除并复制到另一个空队列中，再删除原队列中唯一一个元素并弹出。**每次出栈操作后，总有一个队列是空的。又因为进栈时也是进入不为空的那个队列，因此进出栈操作时总有一个队列是空的。**

这两个队列不像上面的例子中有明确的分工，在两个队列实现栈的例子中，它们交替实现进栈或出栈的功能。

总结一下：

- 进栈，进入不为空的那个队列中
- 出栈，将不为空队列中除倒数最后一个元素外的其余元素移动到另一个空队列中，紧接着弹出原队列的最后一个元素。

```java
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

```

---

by @sunhaiyu

2017.12.16
