package Chap3;

/**
 * 输入两个单调递增的链表，输出两个链表合成后的链表.
 * 当然我们需要合成后的链表满足单调不减规则。
 */
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
