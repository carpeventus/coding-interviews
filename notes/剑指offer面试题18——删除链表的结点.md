# 剑指offer面试题18——删除链表的结点

## 题目一——O(1)删除链表结点

> ```
> 给定单向链表的头指针和一个结点指针，定义一个函数在O(1)时间内删除该结点。假设要删除的结点确实在链表中。
> ```

常规思路：删除某个结点需要找到该结点的前一个结点，由于单向链表没有指向前一个结点的指针，所以不得不从头指针开始遍历链表。显然时间复杂度为O(n)。实现如下：

```java
package Chap3;

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
          	// 找到被删除结点的前一个结点
            while (cur.next != toBeDel) {
                cur = cur.next;
            }
            // cur为toBeDel的前一个结点
            cur.next = cur.next.next;
        }
    }
}

```

试想一个简单例子，下面是一个链表，假设要删除的结点是C。按照上面的思路是从A开始遍历，找到D的前一个结点B后，然后令B.next = D。

```
A -> B -> C -> D
```

现在要实现O(1)的复杂度，肯定不能从头结点开始，试试直接从要删除的那个结点入手，因此A、B应该都不会被访问到。如果将D结点中的值(value)覆盖C中的值，就变成了下面这样

```
A -> B -> D(new) -> D(original)
```

此时再讲原来的D删除掉，就变成了下面这样，D(new)其实就是原来的C结点，只是值被替换了而已。这样我们**只需用到被删除结点及其下一个结点就能实现O(1)时间删除**。

```
A -> B -> D(new)
```

有一种特殊情况是：**如果被删除结点是链表的最后一个结点**，比如此时要删除D，就不能按照上面的方法来了，因为D的后面没有结点，其值不能被覆盖；此时还得从头结点开始找到D的前一个结点。

更特殊的情况：如果删除的结点既是最后一个结点又是头结点（只有一个结点的链表），那么直接将头结点置空即可。

```java
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
```

## 题目二——删除链表中的重复结点

> ```
> 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
> ```

注意重复的结点不保留：并不是将重复结点删除到只剩一个，而是重复结点的全部会被删除。所以链表1->2->3->3->4->4->5 处理后不是1->2->3->4->5。

思路：从头结点开始遍历链表，因为是删除结点，所以需要知道被删除结点的前一个结点，设为pre；只要当前结点和下一结点不为空，就比较它俩，如果相同，删除当前结点及其后所有值和它相同的结点（由于链表有序，所以值相同的结点必然连续）——只需将pre和第一个不和当前结点值相同的结点相连；**特殊情况是头结点就是重复结点，此时头结点也会被删除，因此需要重新定义头结点。**如果当前结点和下一个结点值不同，就更新当前结点和前一个结点pre，继续上述比较....

```java
package Chap3;

public class DeleteDuplicationNode {

    private class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode deleteDuplication_2(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }
        // 当前结点的前一个结点
        ListNode pre = null;
        // 当前结点
        ListNode cur = pHead;
        while (cur != null && cur.next != null) {
            // 如果当前结点和下一个结点值相同
            if (cur.val == cur.next.val) {
                int val = cur.val;
                // 跳过所有和cur相同的值
                while (cur != null && (cur.val == val)) {
                    cur = cur.next;
                }
                // 跳出循环得到的是第一个和cur.val不同的结点
                // pre为空说明头结点就是重复结点，因此需要重新设置头结点
                if (pre == null) pHead = cur;
                    // 否则cur之前的pre的下一个结点何cur连接
                else pre.next = cur;
                // 不相等就像前推进，更新cur和pre
            } else {
                pre = cur;
                cur = cur.next;
            }
        }
        return pHead;
    }
}

```

还有种方法更为巧妙，**一开始我们就为链表设置一个新的头结点**，因为链表有序，所以将其值设置为原头结点的`val -1`，这样保证了新的头结点的值与之后的所有结点都不会相同。因为是从原头结点开始遍历的，所以pre一开始理所应该地是新设置的头结点，如下：

```java
// 建立一个头结点代替原来的pHead
ListNode first = new ListNode(pHead.val - 1);
first.next = pHead;
// 当前结点的前一个结点
ListNode pre = first;
// 当前结点
ListNode cur = pHead;
```

然后代码的主体和上面基本一样，只不过在**头结点就是重复结点这种情况下**，`pre.next = cur`仍然是正确的，因为此时`pre == first`，即`first.next = cur`。效果等同于重新设置了原链表的头结点（新链表的first.next就是原链表的头结点）。最后记得返回的是`first.next`，不能返回pHead，因为pHead有可能已经被删除了。

```java
public ListNode deleteDuplication(ListNode pHead) {
  	if (pHead == null || pHead.next == null) {
    	return pHead;
  	}
  	// 建立一个头结点代替原来的pHead
  	ListNode first = new ListNode(pHead.val - 1);
  	first.next = pHead;
  	// 当前结点的前一个结点
  	ListNode pre = first;
  	// 当前结点
  	ListNode cur = pHead;
  	while (cur != null && cur.next != null) {
    	if (cur.val == cur.next.val) {
      	int val = cur.val;
      	while (cur != null && (cur.val == val)) {
        	cur = cur.next;
      	}
      	pre.next = cur;
    	} else {
      	pre = cur;
      	cur = cur.next;
    	}
  	}
  	// 这里不能返回pHead，因为pHead也可能被删除了
  	return first.next;
}
```

---

by @sunhaiyu

2017.12.22
