## 剑指offer面试题59--队列的最大值

## 滑动窗口的最大值

> ```
> 题目1：滑动窗口的最大值。
> 给定一个数组和滑动窗口的大小，请找出所有滑动窗口里的最大值。例如，如果输入数组{2, 3, 4, 2, 6, 2, 5}以及滑动窗口的大小3，那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}
> ```

### 方法1：基于最大堆的优先队列

就以题目中的例子来模拟找出窗口中的最大值的过程。先存入3个元素，于是优先队列中有{2, 3, 4}，使用peek方法可以以O(1)的时间得到最大值，之后删除队列头的元素2，同时入列下一个元素，此时队列中有{3, 4, 2}，再调用peek方法得到最大值，然后删除队列头的3，下一个元素入列......不断重复进行此操作，直到最后队列中只有两个元素为止。

```java
package Chap6;

import java.util.*;

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
}
```

### 方法2：使用双端队列

这种思路**只将有可能成为滑动窗口中的最大值的元素入列**。

使用双端队列，**队列头永远存放当前滑动窗口的最大值，而队列尾存放候选最大值，即当队列头的最大值弹出后成为新的最大值的那些元素**。以题目中的例子模拟下该思路。

一开始2进入队列，然后3进入队列，因为3比2大，所以2不可能是滑动窗口的最大值。因此先将2从队列中弹出，然后3入列；接下来4入列也类似：将3弹出，4入列，目前队列中只有一个元素，且滑动窗口中已经有三个数字了，所以此时窗口中的最大值是位于队列头的4。

接下来2入列，虽然比4小，但是不知道什么时候4将会位于滑动窗口之外，所以这个2还是有一定可能成为窗口中的最大值的，应该将其入列，注意应该排在队列的尾部，因为队列头始终是当前窗口的最大值。由于队列头还是4，所以此时窗口中的最大值还是4。

然后6入列，队列中的4和2都不可能成为窗口中的最大值了，因此应该先从队列中弹出4和2然后再将6入列....后面的分析也大同小异。

 ......

再看最后一个元素1，它入列前，队列中有6和5，且6位于队列头是窗口中的最大值，按照之前的做法，应该将1入列，但是窗口的大小为3，此时队列头的6已经在窗口之外了，所以要讲6从队列中弹出，那么此时队列中还剩下5和1且5位于队列头，所以最后一个窗口中的最大值是5。**那么如何判断某个元素还在不在窗口内呢？我们应该在往队列中存元素的下标而不是元素本身。**若当前正访问的元素的下标与窗口最大值的下标（即队列头元素的下标）超过了窗口的宽度，就应该从队列头删除这个在滑动窗口之外的最大值。

总之就是：

- 即将要入列的的元素比队列中哪些元素大或者相等，就将那些元素先从队列中删除，然后再入列新元素；
- 队列头的最大值如果位于滑动窗口之外，则需要将队列头的最大值从队列中删除；
- 当前下标加上1（因为下标从0开始计）等于窗口宽度的时候就可以开始统计滑动窗口的最大值了

基于以上三点可写出下面的代码

```java
package Chap6;

import java.util.*;

public class MaxInWindow {
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
```

## 队列的最大值

> ```
> 定义一个队列，实现max方法得到队列中的最大值。要求入列、出列以及邱最大值的方法时间复杂度都是O(1)
> ```

此题和面试题30“包含min的栈”同一个思路。

一个dataQueue正常入列、出列元素，为了以O(1)的时间获取当前队列的最大值，需要使用一个maxQueue存放当前队列中最大值。具体来说就是，**如果即将要存入的元素比当前最大值还大，那么存入这个元素；否则再次存入当前最大值。**

```java
package Chap6;

import java.util.Deque;
import java.util.LinkedList;

public class MaxQueue {
    private Deque<Integer> maxDeque = new LinkedList<>();
    private Deque<Integer> dataDeque = new LinkedList<>();

    public void offer(int number) {
        dataDeque.offerLast(number);
      	// 即将要存入的元素比当前队列最大值还大，存入该元素
        if (maxDeque.isEmpty() || number > maxDeque.peekFirst()) maxDeque.offerFirst(number);
      	// 即将要存入的元素不超过当前队列最大值，再将最大值存入一次
        else maxDeque.offerFirst(maxDeque.peekFirst());
    }

    public void poll() {
        if (dataDeque.isEmpty()) throw new RuntimeException("队列已空");
        dataDeque.pollFirst();
        maxDeque.pollFirst();
    }

    public int max() {
        if (maxDeque.isEmpty()) throw new RuntimeException("队列已空");
        return maxDeque.peekFirst();
    }
}

```

还是上面的例子{2, 3, 4, 2, 6, 2, 5}，分析随着各个元素入列dataQueue和maxQueue的情况。

| 操作   | dataQueue           | maxQueue            | max  |
| ---- | ------------------- | ------------------- | ---- |
| 2入列  | 2                   | 2                   | 2    |
| 3入列  | 2, 3                | 3, 2                | 3    |
| 4入列  | 2, 3, 4             | 4, 3, 2             | 4    |
| 2入列  | 2, 3, 4, 2          | 4, 4, 3, 2          | 4    |
| 6入列  | 2, 3, 4, 2, 6       | 6, 4, 4, 3, 2       | 6    |
| 2入列  | 2, 3, 4, 2, 6, 2    | 6, 6, 4, 4, 3, 2    | 6    |
| 5入列  | 2, 3, 4, 2, 6, 2, 5 | 6, 6, 6, 4, 4, 3, 2 | 6    |

出列的话两个队列同时出列一个元素，保证了maxQueue的队列头元素始终是dataQueue的当前最大值。

---

by @sunhaiyu

2018.2.6

