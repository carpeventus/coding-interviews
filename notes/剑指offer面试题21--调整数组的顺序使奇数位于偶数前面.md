# 剑指offer面试题21--调整数组的顺序使奇数位于偶数前面

> ```
> 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
> ```

如果可以使用额外的空间，这道题不难。O(n)的空间和O(n)的时间复杂度。思路很简单：

- 第一次遍历，将奇数存入辅助数组中；
- 第二次遍历，将偶数存入数组中；
- 辅助数组的元素覆盖原数组。

```java
package Chap3;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，
 * 并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 */
public class ReOrderArray {
    public void reOrderArray(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        int len = array.length;
        // 左往右扫描的指针
        int p = 0;
        // 辅助数组
        int[] temp = new int[len];
        // 左往右扫描，只存入奇数
        for (int i = 0; i < len; i++) {
            if (isOdd(array[i]))
                temp[p++] = array[i];
        }
        // 再次扫描，只存入偶数
        for (int i = 0; i < len; i++) {
            if (!isOdd(array[i]))
                temp[p++] = array[i];
        }
        // 覆盖原数组
        for (int i = 0; i < len; i++) {
            array[i] = temp[i];
        }
    }

    private boolean isOdd(int val) {
        return (val & 1) == 1;
    }
}

```

`isOdd`方法判断一个数是不是奇数，用了“与”运算，**任何奇数的二进制表示的最低位必然是1**。通常“与”运算的效率比`val % 2`要高。

## 相对位置可以改变的情况

如果将题目条件放宽，所有奇数在偶数之前就可以了，不要求保持原来的相对位置。则本题可以用一次遍历完成。思路如下：

- 设置两个指针，一个指针pBegin指向数组第一个元素，一个指针pEnd指向数组的最后一个元素；
- 当pBegin < pEnd时：pBegin指针不断右移，直到遇到某个偶数为止；同样的，pEnd不断左移，直到遇到某个奇数为止。
- 若此时pBegin < pEnd：交换两个元素，因此排在前面的奇数被交换到了后面。

```java
package Chap3;

import java.util.Arrays;
import java.util.function.Predicate;

public class ReOrderArray {
    public void reOrderArray(int[] array, Predicate<Integer> p) {
        if (array == null || array.length == 0) {
            return;
        }
        int pBegin = 0;
        int pEnd = array.length - 1;
        while (pBegin < pEnd) {
            // 左到右找出第一个偶数
            while (pBegin < pEnd && p.test(array[pBegin]))
                pBegin++;
            // 右到左找出第一个奇数
            while (pBegin < pEnd && !p.test(array[pEnd]))
                pEnd--;
		   // 交换两个奇数和偶数，奇数被换到了前面，偶数换到了后面
            if (pBegin < pEnd) {
                int temp = array[pBegin];
                array[pBegin] = array[pEnd];
                array[pEnd] = temp;
            }
        }
    }

    public static void main(String[] args) {
        ReOrderArray reOrder = new ReOrderArray();
        int[] a = {3, 2, 1, 9, 8, 7, 4, 5, 6};
        reOrder.reOrderArray(a, p -> (p & 1) == 1);
        System.out.println(Arrays.toString(a));
    }
}

```

**为了增强程序的可拓展性**，`reOrderArray`可以传入一个方法，这里使用`Predicate`类，它有一个`test`方法，用于测试传入参数是否符合给定的条件。在调用`reOrderArray`时候，不必传入Predicate实例，直接使用Java 8的Lambda表达式即可，这其实和`isOdd`方法功能一样。

---

by @sunhaiyu

2017.12.25
