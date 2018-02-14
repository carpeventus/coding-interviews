# 剑指offer面试题24--反转链表

> ```
> 输入一个链表的头结点，反转链表后，并返回反转链表的头结点。
> ```

## 用栈实现，不推荐

本题容易想到用栈，但是空间复杂度为O(N)不推荐。

```java
package Chap3;

import java.util.LinkedList;

/**
 * 输入一个链表的头结点，反转链表后，并返回反转链表的头结点。
 */
public class ReverseLinkedList {
    private class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 用栈，空间复杂度为O(N)不推荐
     *
     * @param head 头结点
     * @return 反转之后的链表头结点
     */
    public ListNode reverseList_2(ListNode head) {
        if (head == null) {
            return null;
        }
        LinkedList<ListNode> stack = new LinkedList<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        head = stack.pop();
        cur = head;
        while (!stack.isEmpty()) {
            cur.next = stack.pop();
            cur = cur.next;
        }
        cur.next = null;

        return head;
    }
}

```

思路就是链表的所有结点存入栈中，弹出的第一个结点作为头结点。然后不断往后添加结点，需要注意的是添加最后一个元素的时候，next要指向空（因为栈中最后一个元素实际上是原链表的头结点，该结点的next不为null）。

## 三个指针，推荐

可以设置三个指针，分别指向前一结点、当前结点、后一结点。当前结点的next本来是指向它的后一结点的，现在让其指向它的前一个结点，就实现了链表的反转。**但是当前结点与它后一个结点链接断开了**，因此在反转链表之前需要保存当前结点的下一个结点的指针。以便链表反转的过程向前推进（当前指针和前一指针前移）。

```java
public ListNode reverseList(ListNode head) {
  	ListNode pre = null;
  	ListNode next = null;
  	ListNode cur = head;
  	while (cur != null) {
      	// 当前结点的后一结点需要保存下来
    	next = cur.next;
      	// 反转
    	cur.next = pre;
      	// 指针前移
    	pre = cur;
    	cur = next;
  	}
  	// 最后pre到达的位置刚好是最末尾那个结点，即反转后的头结点
  	return pre;
}
```

## 递归解法

递归解法还是有必要学习下的。如果链表为空，就返回null；如果链表只有一个结点，就返回该结点。链表长度大于等于2时思路时，**先递归到倒数第一个结点处**。即下面代码中的revHead，然后返回它，递归调用的返回中逐层返回的都是这个revHead，这保证了最后返回的是反转后的头结点。接着递归调用返回到上一层。nextNode就是最后一个结点，而head是倒数第二个结点，让nextNode.next 指向head就实现了链表的反转，**之后倒数第二个结点的next要指向null，因为它原先是指向nextNode的，同时也保证了最后一个结点的next也指向null的正确性。**

```java
public ListNode reverseListRecur(ListNode head) {
  	if (head == null || head.next == null) return head;

  	ListNode revHead = reverseListRecur(head.next);
  	ListNode nextNode = head.next;
  	nextNode.next = head;
  	head.next = null;
  	return revHead;
}
```

---

by @sunhaiyu

2017.12.27
