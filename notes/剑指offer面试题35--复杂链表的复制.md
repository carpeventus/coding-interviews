# 剑指offer面试题35--复杂链表的复制

> ```
> 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
> ```

复杂链表的定义如下

```java
private class RandomListNode {
   int label;
   RandomListNode next = null;
   RandomListNode random = null;

   RandomListNode(int label) {
     this.label = label;
   }
}
```

**注意不可将链表头结点引用返回，所以需要自己new一个结点出来。**要完成复杂链表的复制，第一步要完成普通链表的复制，普通链表的复制可以用一个栈复制每一个结点，之后逐个弹出并连接起来。对于本题有两种策略：

第一种是，在复制普通链表时，将原链表结点和复制链表的结点成对存入`HashMap<N, N'>`中，建立一对一的映射。当进行随机结点复制时，遍历原链表，如果某结点的随机结点不为空，那么根据HashMap能以O(1)的时间找到对应的复制链表结点中，若原始链表的结点M指向随机结点S，那么复制链表的M'也要指向结点S'，这种方法时间复杂度为O(N)，但空间复杂度为O(N)。

更好的方法是，插入、连接、拆分三步法。

- 插入：在原始链表的每个结点后插入一个值和它一样的新结点；则有`oriNode.next == cloneNode`这样的关系；
- 连接随机结点：遍历插入新结点后的链表，在访问原始链表中的那些结点时，判断其是否有随机结点，有的话`cloneNode.random = oriNode.random.next`这里`oriNode.random.next`表示原始链表随机结点的下一个结点，其实就是复制链表的随机结点。
- 拆分原始链表和复制链表：将奇数结点相连就是原始链表，将偶数结点相连就是我们想要的复制链表。返回复制链表的头结点即可。

```java
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

```

该方法没有用到额外的空间，仅仅是对每个结点进行插入、删除操作等。时间复杂度为O(N).

---

by @sunhaiyu

2018.1.11
