package Chap7;

/**
 * 输入一棵普通树（拥有指向父结点的指针）的两个结点，返回它们的最低公共祖先。
 */
public class LastSameInTreeParent {
    private class Node {
        Node parent;
        int val;

        public Node() {
            this.val = val;
        }
    }

    /**
     * 变成了两条链表的第一个公共结点问题
     */
    public Node findLastSame(Node node1, Node node2) {
        Node cur1 = node1;
        Node cur2 = node2;
        int len1 = 0;
        int len2 = 0;
        // 计算链表1的长度
        while (cur1 != null) {
            len1++;
            cur1 = cur1.parent;
        }
        // 计算链表2的长度
        while (cur2 != null) {
            len2++;
            cur2 = cur2.parent;
        }
        // 长链表先走若干步，和短链表的尾部对齐
        if (len2 > len1) {
            for (int i = 0; i < len2 - len1; i++) node2 = node2.parent;
        }

        if (len1 > len2) {
            for (int i = 0; i < len1 - len2; i++) node1 = node1.parent;
        }
        // 同时前进，第一个相等的结点即是
        while (node1 != null && node2 != null) {
            if (node1 == node2) return node1;
            node1 = node1.parent;
            node2 = node2.parent;
        }
        return null;
    }
}
