package Chap5;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 输入两个链表，找出它们的第一个公共结点。由于是单链表，第一个公共结点及其后的结点都相同
 */
public class FirstPublicNode {

    private class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 方法1：两个辅助栈，从尾到头，找到最后一个相同的结点
     */
    public ListNode findFirstCommonNodeStack(ListNode pHead1, ListNode pHead2) {
        ListNode cur1 = pHead1;
        ListNode cur2 = pHead2;
        LinkedList<ListNode> stack1 = new LinkedList<>();
        LinkedList<ListNode> stack2 = new LinkedList<>();
        // 分别存入两个栈中
        while (cur1 != null) {
            stack1.push(cur1);
            cur1 = cur1.next;
        }
        while (cur2 != null) {
            stack2.push(cur2);
            cur2 = cur2.next;
        }
        // 用于记录逆序的上一个公共结点
        ListNode publicNode = null;
        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            if (stack1.peek() == stack2.pop()) publicNode = stack1.pop();
                // 当前比较的不相同时，返回逆序的最后一个公共结点（也就是正序的第一个公共结点）
            else return publicNode;
        }
        return publicNode;
    }

    /**
     * 还可以用Set，先存入第一个链表的所有结点，然后存入第二个链表的结点，当第一次添加失败的时候说明发现了第一个重复结点
     */
    public ListNode findFirstCommonNodeSet(ListNode pHead1, ListNode pHead2) {
        Set<ListNode> set = new HashSet<>();
        ListNode cur1 = pHead1;
        ListNode cur2 = pHead2;
        while (cur1 != null) {
            set.add(cur1);
            cur1 = cur1.next;
        }

        while (cur2 != null) {
            if (!set.add(cur2)) return cur2;
            cur2 = cur2.next;
        }
        return null;
    }

    /**
     * 方法2：先得到两个链表的长度；
     * 让两个链表的尾部对齐，即先让长链表走若干部，然后两个链表同时走，保证它俩同时到链表末尾
     */
    public ListNode firstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode cur1 = pHead1;
        ListNode cur2 = pHead2;
        int len1 = 0;
        int len2 = 0;
        // 计算链表1的长度
        while (cur1 != null) {
            len1++;
            cur1 = cur1.next;
        }
        // 计算链表2的长度
        while (cur2 != null) {
            len2++;
            cur2 = cur2.next;
        }
        // 长链表先走若干步，和短链表的尾部对齐
        if (len2 > len1) {
            for (int i = 0; i < len2 - len1; i++) pHead2 = pHead2.next;
        }

        if (len1 > len2) {
            for (int i = 0; i < len1 - len2; i++) pHead1 = pHead1.next;
        }
        // 同时前进，第一个相等的结点即是
        while (pHead1 != null && pHead2 != null) {
            if (pHead1 == pHead2) return pHead1;
            pHead1 = pHead1.next;
            pHead2 = pHead2.next;
        }
        return null;
    }

    /**
     * 很短的代码
     */
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        while (p1 != p2) {
            p1 = (p1 == null ? pHead2 : p1.next);
            p2 = (p2 == null ? pHead1 : p2.next);
        }
        return p1;
    }
}
