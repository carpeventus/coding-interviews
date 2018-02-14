# 剑指offer面试题46--把数字翻译成字符串

>   ```
>   给定一个数字，我们按照如下的规则把它翻译成字符串
>   0 -> a
>   1 -> b
>   2 -> c
>   ...
>   25 -> z
>
>   一个数字可能有多种翻译，比如12258有五种，分别是"bccfi", "bwfi","bczi","mcfi", "mzi".请实现一个函数，用来计算一个数字有多少种不同的翻译方法。
>   ```

举个简单的例子，比如258，我们可以先翻译第一个数字，得到c，也可以翻译前两个数字，得到z；如果先翻译了一个数，对于剩下的58，和上面一样可以选择只翻译一位，得到f，也可以翻译两位（在这个例子中是不合法的，58没有与之映射的字符），如果先翻译了两位，对于剩下的8，只能有一种翻译方法了，得到i。所以最后得到两种翻译方法，cfi和zi。

从这个简单的例子可以得到一般性的结论，令f(i)为从第i位开始的不同翻译的数目，因为每次可以选择值翻译一个数字，也可一次翻译两个数字，而对于剩下的数字也可以采用同样的方法，这是一个递归问题。

$$f(i) = f(i+1) + g(i, i+1)f(i+2), 0 \le i < n$$

其中n为数字的位数，$g(i, i+ 1)$可取0或者1，当一次翻译两个数字时，如果这个数字在$10 \le m \le25$的范围内，这就是一个可翻译的数，此时$g(i, i + 1)$为1，否则为0。根据题意，我们最后就是要求得$f(0)$

## 从左到右翻译的递归

根据上面的思路，可写出从左到右翻译的递归解法。

```java
package Chap5;

import java.util.ArrayList;
import java.util.List;

public class TransLateNumToString {
    /**
     * 方法一：从左到右的递归，重复计算较多，但是可以保存编码的结果
     */
    public List<String> translateNum(int n) {
        List<String> list = new ArrayList<>();
        if (n < 0) return list;

        String number = String.valueOf(n);
        StringBuilder sb = new StringBuilder();
        translate(sb, number, list);
        return list;
    }

    private void translate(StringBuilder sb, String str, List<String> list) {
        if (str.equals("")) {
            list.add(sb.toString());
            return;
        }
		// 一次只翻译一个数
        String s1 = str.substring(0, 1);
        char letter1 = numToLetter(s1);
        sb.append(letter1);
        translate(sb, str.substring(1), list);
        sb.deleteCharAt(sb.length() - 1);
	   // 一次翻译两个数
        if (str.length() >= 2) {
            String s2 = str.substring(0, 2);
            if (check(s2)) {
                char letter2 = numToLetter(s2);
                sb.append(letter2);
                translate(sb, str.substring(2), list);
                sb.deleteCharAt(sb.length() - 1);
            }
        }

    }
	/**
     * 当一次翻译两位数时，检查是否范围在10-25之间
     */
    private boolean check(String num) {
        int val = Integer.parseInt(num);
        return val >= 10 && val <= 25;
    }
    /**
     * 数字 -> 字母的映射，a的ASCII码是97，所以0-25的数字加上97就得到了题      * 目中的映射
     */ 
    private char numToLetter(String num) {
        return (char) (Integer.parseInt(num) + 97);
    }

}

```

使用StringBuilder拼接每次翻译的结果，到字符串的结尾说明翻译完毕，将该翻译结果存到一个list中，StringBuilder每次append一个翻译字符然后递归地对其后的字符进行同样的操作，但是递归结束后，StringBuilder需要删除掉这个刚添加了翻译字符，实际上就是模拟了进出栈的过程，这保证了StringBuilder在其他递归调用中表示的翻译字符结果正确。注意只有当当前字符串长度大于等于2才能一次翻译两个数字，因此在翻译两个数字时别忘了判断下。

这种方法，调用了很多次substring，空间上很浪费，而且自上而下的递归中有很多重复计算，效率并不高。但是该方法可以保存每种翻译的结果。

## 从右往左翻译的循环

如果自下而上，从小的结果出发，保存每一步计算的结果，以供下一步使用，也就是我们按照从右到左的顺序翻译。

$$f(i) = f(i+1) + g(i, i+1)f(i+2), 0 \le i < n$$

对于上面的公式，也就是先求出$f(n -1)$，然后求出$f(n -2)$，之后根据这两个值求出$f(n -3)$，然后根据$f(n-2)$和$f(n -3)$求出$f(n -4)$一直往左知道求出$f(0)$，这就是我们要的结果！

```java
package Chap5;

import java.util.ArrayList;
import java.util.List;

public class TransLateNumToString {
    /**
     * 方法二：从右到左的循环，效率高，单纯的计数
     */
    public int getTranslateCount(int n) {
        if (n < 0) return 0;
        return count(String.valueOf(n));
    }

    private int count(String num) {
        int len = num.length();
        int[] counts = new int[len];
      	// f(n -1)必然为1
        counts[len - 1] = 1;

        for (int i = len - 2; i >= 0; i--) {
            int high = num.charAt(i) - '0';
            int low = num.charAt(i + 1) - '0';
            int combineNum = high * 10 + low;
            if (combineNum >= 10 && combineNum <= 25) {
                // f(i) = f(i+1) +f(i+2),if中因为f(i+2)不存在，但是该值肯定为1
                if (i == len -2) counts[i] = counts[i+1] + 1;
                else counts[i] = counts[i+1]+counts[i+2];
            } else {
                // f(i) = f(i+1)
                counts[i] = counts[i+1];
            }

        }
        // 从第一个数字开始的不同翻译数目
        return counts[0];
    }

    public static void main(String[] args) {
        TransLateNumToString t = new TransLateNumToString();
        System.out.println(t.translateNum(187534121).size());
        System.out.println(t.getTranslateCount(187534121));
    }
}

```

上面的代码就是把公式翻译了一遍，其中counnts数组存放每一步计算的结果，即保存$f(0)$~$f(n -1)$的值。$f(n -1)$说明只有一位数，必然只有一种翻译方法；同样注意，当$i = n -2$时候，$f(i+2)$将超出范围，需要特别处理，这种情况就是说，对于两位数比如18，肯定有两种翻译方法，即$f(n -1) + 1 = 2$种。

最后一步得到counts[0]即$f(0)$返回即为答案。

这种方法只需遍历数字的每一位即可，实现了题目的要求计算出了翻译数目，但是没有办法表示出翻译结果。

---

by @suhaiyu

2018.1.22



