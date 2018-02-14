# 剑指offer面试题45--把数组排成最小的数

>   ```
>   输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。
>   ```

这道题可以对数组排序，比如对于普通的数组{23, 12, 32}，可能会想到按照自然序排序后，得到{12, 23, 32}然后直接拼接起来就得到了最小的数字122332，但是像题中的例子按照自然序排好后就是{3, 32, 321}，如果直接拼接得到332321，是不是最小呢？可以发现排好后是{321, 32, 3}，才能得到最小数321323。**可见这已经不是自然序了，所以想到需要自定义一种比较方法，得到一种新的排序方式。**

既然是要拼接数组中所有的数，保证拼接后的数最小，我们从最小的问题出发，当数组中只有两个数时，情况很简单，比如{3, 32}，有用两种方式拼接他们`"3"+"32"`和`"32"+"3"`，分别为332和323，因为323 < 332，所以将32排在3的前面，也就是对于数组中的任意两个数m和n，如何mn < nm，则应该将m排在n的前面。在Java中很好实现，只需重写一个Comparator即可。使用Java 8的lambda表达式可以简化这一过程。

```java
package Chap5;

import java.util.ArrayList;
import java.util.List;

public class PrintMinNumber {
    public String printMinNumber(int[] numbers) {
        if (numbers == null || numbers.length == 0) return "";

        List<Integer> list = new ArrayList<>();
        for (int a : numbers) {
            list.add(a);
        }
	    // 这句是核心，即比较ab和ba的大小，小的排在前面
        list.sort((a, b) -> (a + "" + b).compareTo(b + "" + a));
        StringBuilder sb = new StringBuilder();
        for (int a : list) {
            sb.append(a);
        }
        return sb.toString();
    }
}

```

---

by @sunhaiyu

2018.1.22