# 剑指offer面试题 52--两个链表的第一个公共结点

>   ```
>   输入两个单链表，找出它们的第一个公共结点。
>   ```

这道题有一个隐含条件：单链表只有一个next指针，如果两个链表有公共结点，那么从第一个公共结点前的一个结点开始，两个链表的next都指向同一个结点了。通俗点说就是两条路汇聚成了一条。

## 两个链表，逆序比较

比如一条链表{1, 5, 6, 7, 8}另外一条{2, 3, 4, 5, 6, 7, 8}从结点5开始后面的结点都完全一样了。既然后面的结点完全一样，**我们可以从后往前比较两个链表当遇到某两个结点不同时，上次比较的结点就是逆序的最后一个公共结点了，即正序的第一个公共结点。**

为了可以从后往前比较两个结点，栈可以实现我们的想法。

```java
package Chap5;

import java.util.LinkedList;
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
}

```

假设链表1的长度为m，链表2的长度为n，那么这种方法的时间复杂度为O(m+n)，但相应地需要额外空间。

## 利用集合不能添加重复元素的特性

说到使用额外空间，使用Set可以轻松找出第一个公共结点。所谓公共结点其实就是完全一样的元素，**而Set不能存入相同元素，当第一次添加元素失败时，该结点就是第一个公共结点了。**

```java
package Chap5;

import java.util.HashSet;
import java.util.Set;

public class FirstPublicNode {
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
}

```

## 不使用额外空间的方法

因为第一个公共结点及其之后的结点都相同，所以我们可以将两条链表的**尾部对齐**

，但是两条链表的长短可能不一样。这就需要长链表先走若干步，然后两条链表一起走，知道遇到一个相同结点，该结点就是第一个公共结点。具体长链表要先多少步，当然是长短链表长度之差。所有需先遍历一遍链表得到两个链表的长度。

```java
package Chap5;

public class FirstPublicNode {
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
}

```

上面几种方法的时间复杂度都是O(m+n)，但是这种方法不需要额外空间，更好。

## 很短的代码

下面看别人写的代码，总之就是两个指针p1和p2在两个链表之交替着走：p1如果没走到末尾就前进一步，走到末尾就转向链表2，从头结点开始接着走；对于p2也是如此。

```java
public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
  	ListNode p1 = pHead1;
  	ListNode p2 = pHead2;
  	while (p1 != p2) {
    	p1 = (p1 == null ? pHead2 : p1.next);
    	p2 = (p2 == null ? pHead1 : p2.next);
  	}
  	return p1;
}
```

具体来说，如果确实有公共结点，设链表1是由a + n两部分组成，a是非公共结点部分，n是公共结点部分；相应的链表2由b+ n组成。如果两个链表长度一样，p1和p2同时走，不到一次遍历就可找出公共结点。

如果长度不一样，假设a +n < b+ n。此时p1先到末尾，p2还有b - a到末尾。之后p1转到链表2的头结点，p2走b-a也到达末尾转入链表1的头结点，p1因为也走了b-a步，所以当p2走到链表1的头结点时，此时p1和p2两个指针对齐了，这样同时走，就能在第一个公共结点处相遇；

![](http://obvjfxxhr.bkt.clouddn.com/offer_IMG_20180127_113309.jpg)

如果没有公共结点，那么最后两个指针都是null也会退出循环并返回null。

---

by @sunhaiyu

2018.1.27
