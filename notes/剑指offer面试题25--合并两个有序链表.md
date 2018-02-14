# 剑指offer面试题25--合并两个有序链表

> ```
> 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
> ```

第一想到的就是归并排序了。

## 非递归版本

设置两个指针，p1指向链表1的头结点，p2指向链表2的头结点。先选出归并后链表的头结点：比较两个有序链表的头结点，哪个小即将其作为归并后链表的头结点并将相应的指针右移一步。接下来不断从两个链表中取较小值的结点插入到归并链表的末尾，分四种情况：

- 链表1被取完了，直接将整条链表2插入到归并链表的末尾，归并结束；
- 链表2被取完了，直接将整条链表1插入到归并链表的末尾，归并结束；
- 如果不是以上两种情况，说明两个链表都还有元素，因此可以调用p1.val与p2.val比较（**因此上面两条要先判断，否则可能会引发空指针异常**），p1.val < p2.val时候，取链表1的结点，并将p1指针右移一步，归并链表的指针也要右移一步。
- p1.val >= p2.val时，取链表2的结点，并将p2指针右移一步，归并链表的指针也要右移一步。

针对特殊输入：比如输入的其中一个链表为空，直接返回另外一个链表即可；如果输入的两个链表都为空，在前一种情况中已经包含了。

以上描述直接写成代码，如下：

```java
package Chap3;

public class MergeTwoOrderedList {
    private class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 非递归实现
     * @param list1 有序链表1
     * @param list2 有序链表2
     * @return 合并后的有序链表
     */
    public ListNode merge(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        ListNode p1 = list1;
        ListNode p2 = list2;
        ListNode head = null;
        // 两个链表中哪个头结点的值小，就以此作为新链表的头结点
        if (p1.val <= p2.val) {
            head = p1;
            p1 = p1.next;
        } else {
            head = p2;
            p2 = p2.next;
        }

        ListNode cur = head;

        while (p1 != null || p2 != null) {
            // list1取完了，直接将p2全部插入到新链表后面，p2 =null表示添加完毕后立即终止
            if (p1 == null) {
                cur.next = p2;
                break;
                // list2取完了，直接将p1全部插入到新链表后面
            } else if (p2 == null) {
                cur.next = p1;
                break;
                // 否则只能一个个比较，将较小的插入到后面，同时指针移动
            } else if (p1.val < p2.val) {
                cur.next = p1;
                cur = cur.next;
                p1 = p1.next;
            } else {
                cur.next = p2;
                cur = cur.next;
                p2 = p2.next;
            }
        }
        return head;
    }
}

```

## 递归版本

从两个链表的头结点中选出值小的那个作为归并链表的头结点，此时该结点已经从链表中取出，头结点已经更新为原头结点的next，另一个链表的头结点保持不变。剩下的结点依然是有序的。然后继续归并两个链表中剩下的结点，继续比较两个链表头结点的值...这过程和上面一样了——典型的递归问题。

注意边界条件：当链表1头结点为空，此时就直接返回链表2的头结点，相当于直接和链表2相连了；同理当链表2头结点为空，此时就直接返回链表1的头结点，相当于直接和链表1相连了。这一点上和非递归版本是一样的。

```java
package Chap3;

public class MergeTwoOrderedList {
    public ListNode mergeRecur(ListNode list1,ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        ListNode mergedHead = null;
        if (list1.val < list2.val) {
            mergedHead = list1;
            mergedHead.next = mergeRecur(list1.next, list2);
        } else {
            mergedHead = list2;
            mergedHead.next = mergeRecur(list1, list2.next);
        }
        return mergedHead;
    }
}

```

---

by @sunhaiyu

2017.12.27
