# 剑指offer面试题5--替换空格

> ```
> 请实现一个函数，将一个字符串中的空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
> ```

## 使用Java内置方法

我的做法，由于使用Java语言，很容易想到使用库函数...遍历每个字符，如果遇到空格，就直接使用replace方法替换。

```java
/**
 * 遍历每一个字符，如果是空格就使用Java库函数replace替换
 *
 * @param str 原字符串
 * @return 替换空格后的字符串
 */
public String replaceSpace2(StringBuffer str) {
  	if (str == null) {
    	return null;
  	}

  	for (int i = 0; i < str.length(); i++) {
    	if (str.charAt(i) == ' ') {
      	str.replace(i, i + 1, "%20");
    	}
  }
  return str.toString();
}
```

当然如果你还知道String类有个方法叫`replaceAll`，那实现就更简洁了。

```java
/**
 * 更加简洁的库函数方法
 */
public String replaceSpace3(StringBuffer str) {
  	return str.toString().replaceAll("\\s", "%20");
}
```

一行就完事儿，`\\s`是正则表达式，表示一个空格。

## 从后往前扫描

本着学习的态度（而不是完成任务），上面的方法虽然简洁，但封装过好的方法掩藏了算法的实现细节。现在我们自己来实现一种比较底层的方法。

按照一般思维，总习惯从左往右处理字符串。当遇见第一个空格时，其后的所有字符都需要右移两个位置；当遇见第二个空格时，其后的字符又要右移...因此很多字符被移动了不止一次。能不能减少移动的次数呢？**从前往后扫描要移动那么多次，不妨反过来从后往前扫描试试。**

- 先遍历一遍原字符串，统计空格字符的个数。
- 由于要将空格（一个字符）变成`%20`（三个字符），所以需要将原字符串增长`2 * 空格数`
- 设置两个指针，一个指针oldP指向原字符串的末尾；另一个指针newP指向增长后的新字符串末尾。不断将oldP处的字符移动到newP处，然后两个指针都要左移；如果oldP处字符是空格，就在newP处设置三个字符：按顺序分别是`0、2、%`，同样的两个指针相应的左移。

按照上面的算法流程，写出如下代码。

```java
package Chap2;

public class ReplaceSpace {
    /**
     * 库函数很方便，但是也要理解原理，所以掌握下面的方法是很有必要的。
     * 1、统计空格数目
     * 2、增长原字符串的长度
     * 3、两个指针，一个指向原来字符串末尾，一个指向新字符串末尾。从后往前扫描字符串，并左移指针
     */
    public String replaceSpace(StringBuffer str) {
        if (str == null) {
            return null;
        }
        // 统计空格个数
        int spaceNum = 0;
        for (int i = 0;i < str.length();i++) {
            if (str.charAt(i) == ' ') {
                spaceNum++;
            }
        }
        // 原来字符串的末尾指针
        int oldP = str.length() - 1;
        // 设置新长度
        str.setLength(str.length() + 2*spaceNum);
        // 新的字符串的末尾指针
        int newP = str.length() - 1;
        while (oldP >=0 && newP > oldP) {
            if (str.charAt(oldP) == ' ') {
                str.setCharAt(newP--, '0');
                str.setCharAt(newP--, '2');
                str.setCharAt(newP--, '%');
            } else {
                str.setCharAt(newP--, str.charAt(oldP));
            }
            oldP--;
        }
        return str.toString();
    }

}

```

每个字符最多只会被移动一次，所以时间复杂度为O(n)。

## 相关题目

有两个有序的数组A1和A2，A1末尾有足够空间容纳A2。实现一个函数将A2的所有数字插入到A1中，并且所有数字是有序的。

**因为空闲的空间在A1的末尾，所以从后往前比较两个A1和A2的数字，将更大的那个移动到A1的末尾，然后左移指针，继续比较两个数组中的数。当某个数组中的元素被取完了，就直接从另外一个数组取。**

比如下面的例子

```
A1 = [1, 2 ,4 ,7, 9, , , ...]
A2 = [3, 5, 8, 10, 12]
```

假设A1的长度为10，现暂时只有5个元素，这个长度刚好能装下A2。从后往前比较A1和A2：12比9大，将12移动到A1[9]中，然后9和10继续比较，10移动到A1[8]中，9和8比较9移动到A1[7]中，如此这般直到扫描完两个数组，所有数字也都有序了。

```java
 package Chap2;

import java.util.Arrays;

public class MergeTwoSortedArray {
    public static void merge(Integer[] a, Integer[] b) {
        // 统计a数组中有值元素的个数
        int len = 0;
        for (int i = 0;i < a.length;i++) {
            if (a[i] != null) {
                len++;
            }
        }
        int pA = len-1; // 指向a数组不为空的最后一个元素末尾
        int pB = b.length-1; // 指向b数组不为空的最后一个元素末尾
        int k = len + b.length -1; // 指向归并后数组不为空的最后一个元素末尾

        while (k >= 0) {
            // a数组取完了，取b数组中的
            if (pA < 0) {
                a[k--] = b[pB--];
                // b数组取完了，取a数组中的
            } else if (pB < 0) {
                a[k--] = a[pA--];
                // 否则谁的大取谁的值
            } else if (a[pA] > b[pB]) {
                a[k--]= a[pA--];
            } else {
                a[k--] = b[pB--];
            }
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[10];
        Integer[] b= {1, 3, 5, 7, 9};
        // {2, 4, 6, 8, 10}
        for (int i = 0; i < 5; i++) {
            a[i] = 2 * i + 2;
        }

        merge(a, b);
        System.out.println(Arrays.toString(a));
    }
}

```

---

by @sunhaiyu

2017.12.14
