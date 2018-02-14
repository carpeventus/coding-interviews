# 剑指offer面试题62--圆圈中最后剩下的数字

> ```
> 0, 1, 2...,n - 1这n个数字排成一个圆圈，一开始从数字0开始，从这个圆圈里删除第m个数字；然后从被删除的数字后一位开始计数，继续删除第m个数字...重复这个过程，直到最后只剩一个数字为止。求出这个圆圈里剩下的最后一个数字。
> ```

## 用环形链表模拟圆圈

比较直观的思路就是模拟游戏的过程，题目中说到圆圈而且要经常删除元素，因此容易想到用环形链表，但是Java内置的数据结构中没有环形链表，那么只好当遍历到链表末尾时转到链表头部。

举个简单的例子{0, 1, 2, 3, 4}和m = 3。从0开始第一个被删除的将是2，然后圆圈里剩下{0,1, 3, 4}，接下来从3开始计数，那么下一个被删除的将是0（注意这里到链表末尾所以转到了链表头），然后圆圈里剩下{1, 3, 4}从1开始计数下一个被删除的是4，现在圆圈里剩下{1, 3}，从下一个元素1开始计数，将被删除的元素是1，最后圆圈只剩下3了。

遍历到链表尾部需要转到链表头部，对于这个操作，可以设置一个整数p，表示链表中某元素的下标，每走一步p自增，走到链表尾部时（即p和链表长度相等），将该指针值置为0表示回到链表头部；同样如果删除的正好是链表的最后一个元素，下一个开始计数的应该是链表头部，所以这种情况下也要将p置为0。

```java
package Chap6;

import java.util.LinkedList;
import java.util.List;

public class LastInCircle {
    public int lastRemaining(int n, int m) {
        if (n <= 0 || m <= 0) return -1;
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) list.add(i);
        int p = 0;
        while (list.size() > 1) {
            for (int k = 0; k < m - 1; k++) {
                p++;
              	// 走到链表尾部时
                if (p == list.size()) p = 0;
            }
            list.remove(p);
            // 删除的正好是链表的最后一个元素
            if (p == list.size()) p = 0;
        }

        return list.get(0);
    }
}

```

其实**取余操作可以模拟很多环形问题**，因为n % n == 0，所以取余操作自动解决了到链表尾部时需要转到下标0的问题。所以本题只需用`removeIndex = （removeIndex + (m -1)） % list.size`就能得到本次要删除的元素的下标。

比如一开始链表的大小为5，运用上面的式子，要删除的元素下标是2 % 5 = 2；删除后链表大小变成4，下一个要删除的元素下标是(2 + 2) % 4 = 0;删除后链表大小变成3，下一个要删除的元素下标是(0 + 2) % 3 = 2.....跟着走一遍流程，没问题！

```java
package Chap6;

import java.util.LinkedList;
import java.util.List;

public class LastInCircle {
    public int LastRemaining_Solution(int n, int m) {
        if (n <= 0 || m <= 0) return -1;
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) list.add(i);
        int removeIndex = 0;
        while (list.size() > 1) {
            // 关键是这句
            removeIndex = (removeIndex + m - 1) % list.size();
            list.remove(removeIndex);
        }
        return list.get(0);
    }
}

```

## 发现数学规律--约瑟夫环问题

令$f(n, m)$为n个数字`0, 1, ...n -1`中删除第m个数字后，最后剩下的那个数字。

要删除第m个数字，因为从0开始计数，所以计数到m-1，在这n个数中，第一个被删除的是(m -1) % n，不妨设k =  (m -1) % n。删除k之后，剩下`0,1,...k-1,k+1,...n -1`下一个要从k+1开始计数。

既然从`k+1`开始计数，相当于把k+1放在最前面，`k+1,...n-1,0,1,k-1`由于此时打乱了数字排列的规律，该函数已不再是$f(n-1, m)$了，不妨表示为$f'(n -1, m)$

现在把k + 1认为是0，k+2认为是1,以此类推，因为刚删除了一个元素，所以只有n-1个元素了，映射表如下

```
0     ->   k+1
1     ->   k+2
...
n-k-2 ->   n-1
n-k-1 ->   0
n-k   ->   1
n-2   ->   k-1

```

如果用x’表示重排后新序列中的元素（上表中箭头右边的数），x表示x'被当作的数字，那么有`x' = (x + k + 1) % n` ，所以下式也成立

`f'(n -1, m) = [f(n-1, m) + k + 1] % n`

还有，**最初序列最后剩下的数字$f(n,m)$和删除一个数字后的序列最后剩下的数字时同一个，因此有$f(n,m)=f'(n-1, m)$**

综合以上各式，再代入k = (m-1) % n

`f(n, m) = [f(n-1, m) + m] % n ,n > 1`

且恒有`f(1, m) = 0, n = 1`，因为如果只有一个数（这个数是0），那么无需删除，最后一个剩下的数就是它。既然知道了$f(1, m)$根据上式就能求出$f(2, m)$，以此类推，只需一个循环就能求出$f(n, m)$

```java
package Chap6;

public class LastInCircle {
    /**
     * 数学规律：约瑟夫环问题
     */
    public int lastNumInCycle(int n, int m) {
        if (n <= 0 || m <= 0) return -1;
        int f = 0;
        for (int i = 2; i <= n; i++) {
            f = (f + m) % i;
        }
        return f;
    }
}

```

---

by @sunhaiyu

2018.2.7
