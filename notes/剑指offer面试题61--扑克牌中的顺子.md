# 剑指offer面试题61--扑克牌中的顺子

> ```
> 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这五张牌是不是连续的。2~10是数字本身，A为1，J为11，Q为12，K为13，而大小王可以看成任意数字。
> ```

这道题有两种思路。

## 方法1：用大小王填补间隔

正常的顺子比如23456，假如含有大小王呢？03467也能构成顺子，虽然4和6中间有一个间隔，但是因为存在一张大小王，刚好可以让它成为5而填补上4和6之间的间隔。再假设是00367也能构成顺子，3和6之间有两个间隔但是刚好有两个王可以填补。更特殊的02345, 00234, 这种没有本身间隔的牌组合，因为大小王可以作任何数字，显然也构成顺子。

接下来看不能构成顺子的例子，34578，以及03678或者00378，可以发现**当王的个数比间隔数少的话就不能构成顺子，反之如果王的个数大于等于间隔数，就能构成顺子。**

要计算相邻两张牌的间隔，需要牌组合已经有序，因此第一步就要对五张牌排序。因此这种思路需要O(nlgn)的时间。

基于上述思路写出如下代码：

```java
package Chap6;

import java.util.Arrays;

public class PlayCard {
    /**
     * 方法1：排序，计算大小王的个数和总间隔
     */
    public boolean isContinuous(int[] numbers) {
        if (numbers == null || numbers.length != 5) return false;
        Arrays.sort(numbers);
        int joker = 0;
        // 统计大小王的个数，题目说了最多四个
        for (int i = 0; i < 4; i++) {
            if (numbers[i] == 0) joker++;
        }
        // 是对子直接返回false
        // 相邻数字gap为0，4~6之间间隔1，所以有减1的操作
        int totalGap = 0;
        for (int i = numbers.length - 1; i > joker; i--) {
            if (numbers[i] == numbers[i - 1]) return false;
            // 统计总间隔
            totalGap += numbers[i] - numbers[i - 1] - 1;
        }
        // 总的间隔要小于joker的数量才是顺子
        return totalGap <= joker;
    }
}

```

## 方法2：顺子本身的规律

五张牌组合成的顺子，都有哪些共性呢？如果牌中没有大小王，那么23456, 56789等这样的组合才是顺子；即五张牌中最大值和最小值的差始终是4，且任意两张牌不重复；现在假设有大小王（用0表示），02345、03467、00367都是顺子，但03678、00378不是顺子。发现大小王可多次出现，因此五张牌中0可以重复出现。除开0之外，其他牌不能重复出现且最大值与最小值的差小于5。综合以上两种情况，要想构成顺子，需要满足以下条件：

- 除开0之外，其他任意牌不得重复出现
- 除开0之外的其他牌，最大值和最小值的差要小于5

基于以上两条规则，可写出如下代码：

```java
package Chap6;

public class PlayCard {
    /**
     * 方法2：除了0之外，其他数字不可重复出现；最大最小值差不得超过5
     */
    public boolean isContinuous2(int [] numbers) {
        if (numbers == null || numbers.length != 5) return false;
        int[] count = new int[14];
        int max = -1;
        int min = 14;
        for (int number : numbers) {
            count[number]++;
            // 对除了0之外的其他数计算最大最小值
            if (number != 0) {
                if (count[number] > 1) return false;
                if (number > max) max = number;
                if (number < min) min = number;
            }
        }
        // 如果没有0，最大最小值之差为4，有0还能凑成顺子的，差值小于4；大于4肯定不能凑成顺子
        return max - min < 5;
    }
}

```

这种思路无需排序，时间复杂度为是O(n)，但使用了额外空间，由于额外空间的大小固定为14，可以认为空间仍是常数级别的。

---

by @sunhaiyu

2018.2.6
