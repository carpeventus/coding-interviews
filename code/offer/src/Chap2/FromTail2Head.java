package Chap2;


import java.util.LinkedList;
import java.util.ArrayList;

/**
 * 输入一个链表的头节点，从尾到头打印链表每个节点的值。
 */

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
     * 更推荐使用栈，正序压入，逆序弹出
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

    /**
     * 利用递归，先递归到最后一个结点后开始依次返回。链表如果很长不适合用递归，递归深度将很大
     */
    private ArrayList<Integer> a = new ArrayList<>();

    public ArrayList<Integer> printListFromTailToHead2(ListNode listNode) {
        if (listNode != null) {
            printListFromTailToHead(listNode.next);
            a.add(listNode.val);
        }
        return a;
    }
}