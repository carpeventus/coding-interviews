# 剑指offer面试题6--从尾到头打印链表

> ```
> 输入一个链表的头节点，从尾到头打印链表每个节点的值。
> ```

## 使用栈

我的做法：典型的后进先出，使用栈，顺序遍历一遍链表，依次将每个值入栈。得到的就是尾节点在前，头节点在后的列表。

```java
package Chap2;

import java.util.LinkedList;
import java.util.ArrayList;

public class FromTail2Head {
    // 节点类的定义
    private class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 更推荐使用栈，正序压入，尾节点就在最前面了
     *
     * @param listNode 链表的头结点
     * @return 从尾到头排列的结点
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        LinkedList<Integer> stack = new LinkedList<>();
        for (ListNode node = listNode; node != null; node = node.next) {
            stack.push(node.val);
        }
        return new ArrayList<>(stack);
    }
}
```

使用了“万能的”LinkedList，将其当栈使用。

## 使用递归

递归的本质也是栈（系统创建的），下面采用递归的解法。

```java
/**
 * 利用递归，先递归到最后一个结点后开始依次返回。链表如果很长不适合用递归，递归深度将很大
 */

// 这是一个类成员变量
private ArrayList<Integer> a = new ArrayList<>();

public ArrayList<Integer> printListFromTailToHead2(ListNode listNode) {
  if (listNode != null) {
    printListFromTailToHead(listNode.next);
    a.add(listNode.val);
  }
  return a;
}
```

可以看到如果没到链表尾部，就不断递归，直到最后一个节点，之后开始返回，执行下一句add方法，此时第一个被添加的是尾节点，最后一步递归调用的返回（即第一次递归调用）将添加最开始的头节点。因此得到的也是从尾到头的列表。

递归通常代码更简洁，但是如果**链表很长时，递归调用的层级十分深，速度将大大降低。**所以更推荐用第一种——栈的方法。

---

by @sunhaiyu

2017.12.14
