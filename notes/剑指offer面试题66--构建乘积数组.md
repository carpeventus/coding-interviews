# 剑指offer面试题66--构建乘积数组

```text
给定一个数组A[0, 1,...n - 1],请构建一个数组A[0, 1,...n - 1]，其中B中的元素B[i] = A[0]*A[1]*...*A[i - 1]*A[i + 1]*...*A[n - 1]

不能使用除法。
```

如果可以使用除法，直接除以A[i]就可以得到B[i],但是现在要求了不能使用除法，只好另辟蹊径了。

一种方法是剔除A[i]进行连乘计算B[i],计算一次的时间是 $O(n)$，需要计算n次所以总的时间复杂度为$O(n^2)$

有没有$O(n)$的算法呢。

注意到B[i]是 **除了A[i]** 之外A[0]到A[n - 1]的连乘。那么可以考虑从A[i]处将乘积分成两部分。`C[i] = A[0]*A[1]*...*A[i - 1]，D[i] = A[i + 1]*...A[n - 2]*A[n -1]`;

而`C[i] = C[i -1]*A[i -1],D[i] = D[i + 1]*A[i + 1]`

根据这些关系计算出C[i]和D[i]，最后求得`B[i] = C[i] * D[i]`即可。

![](http://picmeup.oss-cn-hangzhou.aliyuncs.com/coding/Snipaste_2018-10-20_21-55-32.png)

如上图，表中每一行的第一个数就是其后每个数字连乘，没有被乘上的数字A[i]就用1表示了，反正乘积不变。显然C[0] = 1,我们可以用`C[i] = C[i -1]*A[i -1]`计算出对角线左边的三角中各个A[i]的值；显然D[n -1] = 1,再`D[i] = D[i + 1]*A[i + 1]`计算对角线右边的三角中各个A[i]的值，然后将两者相乘就能得到B[i]。

```java
package Chap6;

public class Multiply {
    public int[] multiply(int[] A) {
        if (A == null || A.length < 2) return null;
        int len = A.length;
        int[] B = new int[len];
        B[0] = 1;
        for (int i = 1; i < len; i++) {
            // 计算C[i]
            B[i] = B[i - 1] * A[i - 1];
        }
        int d = 1;
        for (int j = len - 2; j >= 0; j--) {
            // 计算D[i]
            d *= A[j + 1];
            // B[i] = C[i] * D[i]
            B[j] *= d;
        }
        return B;
    }
}

```

计算C[i]是一个for循环，花了$O(n)$的时间，计算D[i]并顺便计算B[i]也是一个for循环，花了$O(n)$的时间，总的时间复杂度为$O(n)$

---

by @sunhaiyu

2108.2.13
