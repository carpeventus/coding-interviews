# 剑指offer面试题42--连续子数组的最大和

>   ```
>    输入一个整型数组，数组里正负数都可能有，数组中的一个或者连续的多个整数组成一个子数组。求所有子数组的和的最大值，要求时间复杂度为O(n)
>   ```

枚举所有的子数组，从中选择最大和可能是很容易想到的办法了，但是总共有$n(n+1) /2$个子数组，最快也需要$O(n^2)$时间复杂度，Pass。

我们可以举例分析数组的特点比如{1, -2, 3, 10, -4, 7, 2, -5}。首先记录下第一个元素，先假设它为最大和。当1加上-2时变成了-1，再加上3等于2，3前面加了一堆还不如不加，所以应该直接从3开始加，即**如果当前累加和是负数，那么它加上当前元素将使得新的累加和比当前元素还要小，此时应该将之前的累加和丢弃，从当前元素开始累加。**

按照上述方法，新的累加和为3大于当前最大和，因此最大和更新为3；加10，当前最大和变成13，继续-4，当前累加和为9，并不大于当前最大和，因此最大和不更新；加7、加2，当前和为18，大于最大和，当前最大和更新为18；最后-5，当前累加和为13，不大于最大和18。遍历完毕，返回最大和18.如果搞清楚了上面的思路，可以很容易写出如下代码:

```java
package Chap5;

public class FindGreatestSumOfSubArray {
    public int findGreatestSumOfSubArray(int[] array) {
        if (array == null || array.length == 0) return 0;

        int maxSum = array[0];
        int curSum = array[0];
        for (int i = 1; i < array.length; i++) {
            // 丢弃累结合，从当前元素开始累加
            if (curSum < 0) curSum = array[i];
          	// 累加
            else curSum += array[i];
          	// 和当前最大和比较，若比它大就更新
            if (curSum > maxSum) maxSum = curSum;
        }
        return maxSum;
    }
}

```

没几行，只需遍历一次，时间复杂度为O(n).

## 动态规划

还可以用动态规划的思想，用$f(i)$表示以第i个数字结尾的子数组，其中$0 \le i < n $，那么我们要求的就是$max[f(i)]$。

$$f(i) = array[i], i=0 或者f(i-1) < 0$$

$$f(i) = f(i -1) + array[i], i \neq 0 且f(i -1) \ge 0$$

**动态规划最重要的就是要保存中间计算结果**，这里$f(i)$其实就是上面的curSum，$max[f(i)]$的计算变成了两两比较，其实就是上面的maxSum。可以看到和上面的方法是异曲同工的。这里保存的中间计算结果就是curSum和maxSum，当$f(i-1) < 0$时对应着上面curSum < 0. 否则就累加，然后求$max[f(i)]$可以通过两两比较得到最终的最大和，也就是$f(i)$和当前的max比较选择较大的那个。

```java
package Chap5;

public class FindGreatestSumOfSubArray {
    /**
     * 动态规划，其实和上面是一样的代码...
     */
    public int FindGreatestSumOfSubArray2(int[] array) {
        if (array == null || array.length == 0) return 0;

        int maxSum = array[0];
        int curSum = array[0];
        for (int i = 1; i < array.length; i++) {
            // if (curSum + array[i] < array[i]),也就是if (curSum < 0) 则curSum的结果是array[i]
            // 否则curSum的值是curSum + array[i]
            curSum = Math.max(curSum + array[i], array[i]);
            // 如果curSum > maxSum,则maxSum取curSum，否则maxSum = maxSum
            maxSum = Math.max(curSum, maxSum);
        }
        return maxSum;
    }
}

```

---

by @sunhaiyu

2017.1.18
