package Chap3;

/**
 * 输入一个链表，输出该链表中倒数第k个结点。
 */
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
