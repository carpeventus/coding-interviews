# 剑指offer面试题57--和为s的数字

# 题目1

>   ```
>   输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S；如果有多对数字的和等于S，输出两个数的乘积最小的。
>   ```

先看这个条件：和一样，要求乘积最小。由于数组递增排序，那么这两个数与其他和为s的两个数比，相隔的距离更远。这里就不证明了，试想周长一样的两个图形，又扁又长的矩形和正方形比哪个面积大吧....

要从数组中找两个数相加，如果和刚好为s，这样就找到了一组候选值；如果和小于s呢，可以把两个数中较小者丢弃，换一个比它稍大的继续求和与s比较;如果和大于s就丢弃较大值换一个比该值稍小的，继续求和与s比较。由于数组已经排序，要换比较小值稍大的，或者换比较大值稍小的都不是难事。

**设置两个指针，一个指向数组的第一个元素，另一个指向数组的最后一个元素。即一开始将较小值设为数组的最小值，较大值设置为数组的最大值。**

接下来求按照上面的方法不断与s比较，找到第一组和为s的两个数就是乘积最小的。

举个例子{1, 2, 4, 7, 11, 15}和数字15，刚开始1+15大于15，所以丢弃15；将1和11求和，小于15，所以丢弃1；将2和11求和，小于15，丢弃2；将4和11求和，刚好等于15，找到第一组和为15的两个数是4和11，它们乘积就是最小的。

```java
package Chap6;

import java.util.ArrayList;

public class TwoSum {
    public ArrayList<Integer> FindNumbersWithSum(int[] array, int sum) {
        ArrayList<Integer> list = new ArrayList<>();
        if (array == null || array.length <= 1) return list;

        int low = 0;
        int high = array.length - 1;

        while (low < high) {
            if (array[low] + array[high] == sum) {
                list.add(array[low]);
                list.add(array[high]);
                break;
            } else if (array[low] + array[high] > sum)
                high--;
            else low++;
        }
        return list;
    }
}

```

代码中只有一个循环，从两头向中间扫描数组，因此时间复杂为O(n)

## 题目2

>   ```
>   和为s的连续正数序列。
>   输入一个正数s，打印出所有何为s的连续正数序列（至少含有两个数）。
>   例如输入15，由于1+2+3+4+5 = 4+5+6 = 7+8,所有打印出三个连续的序列1~5,4~6,7~8
>   ```

注意因为是连续的正数序列，所以序列至少是从1开始的。题目要求至少含有两个数，不妨假设一开始序列中是两个最小的值，即1和2。

举个例子假设s等于9。对初始序列求和得到3，如果小于9，说明需要扩大序列使它包含更多数字，那就增加一个数字，现在序列变成{1,2, 3}，对序列求和继续和9比较，还小了，那就再增加一个数字序列变成{1, 2, 3 ,4}，这次序列和比9大了，丢弃序列中的最小值，现在序列变成{2, 3, 4}，序列求和刚好等于9！因此{2, 3, 4}是一个满足条件的序列，继续找。满足条件的其他序列肯定在后面，所以此时需要扩大序列，因此增加一个数字序列变成{2,3,4,5}, 序列和比9大，**不断丢弃最小值直到序列和小于等于9**，因此丢弃2、丢弃3，现在剩下{4, 5}，序列和和9相等，又找到一个！后面还有没有满足条件的序列呢？**注意到序列是递增的，而且满足条件的序列至少有两个数。**那么满足条件的序列中最大的数不会⌈s / 2⌉了，因为5之后是6，加起来超过9了，所以从5开始其后的序列都不可能满足条件。

```java
package Chap6;

import java.util.ArrayList;

public class ContinuousSeq {
    public ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        if (sum <= 2) return list;
        int small = 1;
        int big = 2;
        int curSum = small + big;
        int mid = (sum + 1) / 2;
        while (small < mid) {
            while (curSum > sum && small < mid) {
                curSum -= small;
                small++;
            }
            if (curSum == sum) list.add(addFromSmallToBig(small, big));
            big++;
            curSum += big;
        }
        return list;
    }

    /**
     * 对一个连续区间内的数字求和
     */
    private ArrayList<Integer> addFromSmallToBig(int small, int big) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = small; i<= big; i++) {
            list.add(i);
        }
        return list;
    }

    public static void main(String[] args) {
        ContinuousSeq continuousSeq = new ContinuousSeq();
        System.out.println(continuousSeq.FindContinuousSequence(9));
    }
}

```

因为每次只是在序列尾部增加一个数字或者在序列头部丢弃一个数字，所以使用了一个curSum可以很方便地保存当前序列的和。

当序列和小于s，或者序列和等于s两种情况下都需要扩大序列使其包含更多的数。所以先用while循环判断序列和大于s的情况（此时small在自增所以还要保证small要小于mid），直到序列和小于或者等于s才退出循环；接在再判断序列和是否等于s，不管等不等于s，扩大序列的代码都会得到执行，这就很好地综合了两种情况，节省了代码量。

---

by @suhaiyu

2018.1.31
