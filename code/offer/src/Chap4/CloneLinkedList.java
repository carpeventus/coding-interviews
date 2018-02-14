package Chap4;

/**
 * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的head
 * （注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
 */
public class CloneLinkedList {
    private class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }


    /**
     * 1、为每个结点的next插入一个和该结点的值一样的结点
     * 2、设置每个复制结点的random结点
     * 3、将链表拆分，返回复制链表的头结点
     *
     * @param pHead 原链表头结点
     * @return 复制链表的头结点，不可直接返回原链接结点的引用，必须使用new出来的RandomListNode
     */
    public RandomListNode Clone(RandomListNode pHead) {
        if (pHead == null) return null;

        copyNode(pHead);
        setCloneRandomNode(pHead);
        return splitList(pHead);
    }

    // 1. 为每个结点的next插入一个和该结点的值一样的结点
    private void copyNode(RandomListNode head) {
        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode cloneNode = new RandomListNode(cur.label);
            cloneNode.next = cur.next;
            cur.next = cloneNode;

            cur = cloneNode.next;
        }
    }

    // 2. 设置每个复制结点的random
    private void setCloneRandomNode(RandomListNode head) {
        RandomListNode cur = head;
        while (cur != null) {
            RandomListNode cloneNode = cur.next;
            if (cur.random != null) {
                cloneNode.random = cur.random.next;
            }
            cur = cloneNode.next;
        }
    }

    // 3. 拆分链表
    private RandomListNode splitList(RandomListNode head) {
        RandomListNode cur = head;
        RandomListNode cloneHead = cur.next;

        while (cur != null) {
            RandomListNode cloneNode = cur.next;
            cur.next = cur.next.next;

            if (cloneNode.next != null) {
                cloneNode.next = cloneNode.next.next;
            }

            cur = cur.next;
        }
        return cloneHead;
    }

}
