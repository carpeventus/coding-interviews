# 剑指offer面试题22--链表中倒数第k个结点

> ```
> 输入一个链表，输出该链表中倒数第k个结点。
> ```

设置两个指针a、b，若当a指针到链表末尾时，b指针正好指向倒数第k个结点，此时返回指针b处的结点即可，这时a指针的位置减去b指针的位置等于k-1，如果将两个指针同步地倒退到链表头结点处，b指针会位于位置0处的头结点，而a指针位于位置k-1处的结点。所以如果我们反过来思考：一开始先让a指针走k-1步，b不动，之后两个指针同时移动，直到a到了链表末尾，此时返回b指针处的结点就是倒数第k个结点。**这种方法只需遍历一次链表。**时间复杂度为O(n)。

以上说的是普通情况，如果k比链表长度还要大怎么办？比如链表长度为5，偏要求倒数第6个结点，当然没有——这时需要返回空。考虑到这种情况，可以在指针a先走k-1步的过程中，加入判断：**因为指针a最多走到链表的尾结点处**，所以如果在循环内出现了`a.next == null`（紧接着`a = a.next`，指针a将走到尾结点之后，这之后才跳出循环），就说明k比链表长度还大，这种情况直接返回null即可。当然如果k <= 0也是没有意义的。**参数k是传入的，一定要讨论它的各种取值。**

```java
package Chap3;

public class FindKthToTailInLinkedList {
    private class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode FindKthToTail(ListNode head, int k) {
        if (k <= 0 || head == null) {
            return null;
        }

        ListNode a = head;
        ListNode b = head;
        // 第一个指针先移动k -1步
        for (int i = 0; i < k - 1; i++) {
            // 如果k的值比链表结点数还大，就会出现这种情况
            if (a.next == null) {
                return null;
            }
            a = a.next;
        }
        // 然后两个指针同时移动到末尾
        while (a.next != null) {
            a = a.next;
            b = b.next;
        }
        return b;
    }
}

```

---

by @sunhaiyu

2017.12.25
