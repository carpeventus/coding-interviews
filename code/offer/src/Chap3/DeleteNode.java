package Chap3;

/**
 * 给定单向链表的头指针和一个结点指针，定义一个函数在O(1)时间内删除该结点。假设要删除的结点确实在链表中
 */
public class DeleteNode {

    private class Node {
        int val;
        Node next;
    }

    /**
     * 常规方法，从first开始找到要删除结点的前一个结点，时间复杂度为O(n)
     */
    public void deleteNode_2(Node first, Node toBeDel) {
        if (first == null || toBeDel == null) {
            return;
        }
        // 要删除的就是链表头，它没有前一个结点
        if (first == toBeDel) {
            first = first.next;
        } else {
            Node cur = first;
            while (cur.next != toBeDel) {
                cur = cur.next;
            }
            // cur为toBeDel的前一个结点
            cur.next = cur.next.next;
        }
    }

    /**
     * 将toBeDel的下一个结点j的值复制给toBeDel。然后将toBeDel指向j的下一个结点
     */
    public void deleteNode(Node first, Node toBeDel) {
        if (first == null || toBeDel == null) {
            return;
        }
        // 要删除的不是最后一个结点
        if (toBeDel.next != null) {
            Node p = toBeDel.next;
            toBeDel.val = p.val;
            toBeDel.next = p.next;
            // 是尾结点也是头结点
        } else if (first == toBeDel) {
            first = first.next;
            // 仅仅是尾结点，即在含有多个结点的链表中删除尾结点
        } else {
            Node cur = first;
            while (cur.next != toBeDel) {
                cur = cur.next;
            }
            cur.next = null;
        }

    }
}
