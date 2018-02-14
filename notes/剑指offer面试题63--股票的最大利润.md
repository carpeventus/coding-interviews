# 剑指offer面试题63--股票的最大利润

```text
假设某股票的价格按照时间先后顺序存储在数组中，问买卖该股票一次可能获得的最大利润是多少？
如一支股票在某段时间内的价格为{9, 11, 8, 5, 7, 12, 16, 14}那么能在价格为5的时候购入并在价格为16时卖出，能获得最大利润11
```

咋一看好像只要求出数组中的最大/最小值就完事了，但是数组中的值是按照时间顺序排列的，这就是说假如数组中最小值在最大值之后，它们的差值不会是股票的最大利润。因此我们要求的是数组中**排在后面的数（售出价格）与排在前面的数（购入价格）的最大差值**。

暴力法是$O(n^2)$的复杂度，就不考虑了。我们力求在一次循环中就求出最大差值。假设当前访问的数组下标是i，**只需记住当前下标之前所有元素中的最小值即可**，用一个int型的变量min维护这个最小值，每次用当前数字和min作差，差值用maxDiff保存，遍历一遍数组即可得出最大的差值。

```java
package Chap6;

public class MaxDiff {
    public int getMaxDiff(int[] prices) {
        if (prices == null || prices.length < 2) return 0;
        int min = prices[0];
        int maxDiff = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < min) min = prices[i];
            int curDiff = prices[i] - min;
            if (curDiff > maxDiff) maxDiff = curDiff;
        }
        return maxDiff;
    }
}

```

一次遍历就搞定，时间复杂度是$O(n)$

---

by @sunhaiyu

2018.2.13