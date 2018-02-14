# 剑指offer面试题68--树中两个结点的最低公共祖先

```text
输入一棵树的两个结点，返回它们的最低公共祖先。
```

这道题说得很含糊，仅仅告诉了*一棵树，*那这棵树是二叉树吗？再具体点，它是二叉查找树吗？我们来一一讨论这这几种情况。

## 如果这颗树是二叉查找树

二叉查找树的特点是：**任意父节点都大于其左子树的所有结点值，且都小于其右子树的所有结点值**。两个结点的公共祖先一定是大于其中较小的那个且小于其中较大的那个，而**从根结点开始从上到下遇到的第一个位于两个输入结点值之间的结点就是最低的公共祖先。**

于是我们可以这么做：从根结点开始，和两个输入结点的值比较，如果当前结点比两个输入都小，那么最低公共祖先一定在当前结点的右子树中，所以下步遍历右子树；如果当前结点比两个输入都大，那么最低公共祖先一定在当前结点的左子树中，所以遍历左子树......直到找到第一个位于两个输入结点值之间的结点就是最低的公共祖先。

```java
package Chap7;

public class LastSameInBST {
    private class Node {
        private Node left, right;
        private int val;

        public Node(int val) {
            this.val = val;
        }
    }

    public Node findLastSame(Node root, Node a, Node b) {
        Node cur = root;
        while (cur != null) {
            if (cur.val < a.val && cur.val < b.val) {
                cur = cur.left;
            } else if (cur.val > a.val && cur.val > b.val) {
                cur = cur.right;
            } else {
                return cur;
            }
        }
        return null;
    }
}

```

非递归的版本如上，代码比较好理解。

## 拥有指向父结点的指针

如果这棵树只是一颗普通的树，但是它拥有指向父结点的指针，该如何求最低公共祖先呢。

这个问题就更简单了，拥有指向父结点的话，**这棵树从下往上看，就是若干条链表汇集在根结点处。我们要找的就是这两个结点的第一个公共结点。**

之前刚好做过一道题*面试题52*就是求两个链表的第一个公共结点，直接把代码拿过来就行。

```java
package Chap7;

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

```

## 普通树，没有指向父结点的指针

这道题再加大难度，如果没有指向父结点的指针呢？是否还能转换成两个链表的第一个公共结点来解决？

想办法创造链表。两个输入结点如果在树中存在，那么**从根结点开始向下的某条路径中必然包含这个结点**，使用两个链表分别保存包含这两个结点的路径。这样就可以把问题转换成求两个链表的第一个公共结点了。

```java
package Chap7;

import java.util.LinkedList;
import java.util.List;

/**
 * 输入一棵普通树的两个结点，返回它们的最低公共祖先。
 */
public class LastSameInTree {

    private class Node {
        List<Node> children;
        int val;
    }

    public Node findLastSame(Node root, Node a, Node b) {
        if (root == null || a == null || b == null) return null;

        LinkedList<Node> path1 = new LinkedList<>();
        LinkedList<Node> path2 = new LinkedList<>();
        collectNode(root, a, path1);
        collectNode(root, b, path2);
        return getLastSameNode(path1, path2);
    }

    /**
     * 收集含有结点node的路径上的所有结点，形成一条链表
     */
    private boolean collectNode(Node root, Node node, LinkedList<Node> path) {
        if (root == node) return true;
        path.add(root);
        for (Node child : root.children) {
            if (collectNode(child, node, path)) return true;
        }
        // 该条路径上没找到结点node就要从路径中移除
        path.removeLast();
        return false;
    }

    /**
     * 两个链表前面的结点都是相同的，找到最后一个相同的结点就是最低公共祖先
     */
    private Node getLastSameNode(LinkedList<Node> path1, LinkedList<Node> path2) {
        Node lastSameNode = null;
        while (!path1.isEmpty() && !path2.isEmpty()) {
            if (path1.peekFirst() == path2.removeFirst()) {
                lastSameNode = path1.removeFirst();
            } else {
                return lastSameNode;
            }
        }
        return lastSameNode;
    }
}
```

上面代码中，路径是从根结点向下的，所以两个链表前面的结点都是相同的，这样就把**两个链表的第一个公共结点问题转换成了两个链表的最后一个相等的结点**，这两个命题是等价的。

得到两条路径需要遍历树两次，每次都是$O(n)$的时间，而每条路径需要的空间在平均情况下是$O(\lg n)$,最差情况下(树退化成链表)是$O(n)$

---

by @sunhaiyu

2018.2.14
