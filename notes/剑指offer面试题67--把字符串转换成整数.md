# 剑指offer热身--字符串转整数

```text
将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0
```

我们知道Java内置了`Integer.parseInt(String s)`，直接调用即可。如果要自己写呢，你可能很快想出如下代码

```java
public static int str2Int {
    // 核心代码
    int number = 0;
    for (int i = 0; i < s.length(); i++) {
      // '4'的ASCII码就比'0'的ASCII码大4，所以可以减去'0'的ASCII码将字符转数字
          number = number * 10 + s.charAt(i) - '0';
    }
}
```

`s.charAt(i)`取出的每一位是ASCII码，需要将其转换为数字。**我们知道字符'0' ~'9'在ASCII码表中是连续的，这意味着字符'4'就比字符'0'大4，因此任何数字字符减去'0'的ASCII码得到的值就是该数字字符对应的数字。**

可是这样的程序稳定吗？试想下面的测试用例`[null,"", "+34", "-56"]`，任意一个都不能通过测试。**因此一定要考虑边界条件和错误检查。**

首先判断是否为空指针，否则一切方法调用都会引发空指针异常，然后是空字符串判断；之后对于字符串的首字符要特殊对待，首字符除了可以是数字，还可以是正负号，首字符之后的其他字符只能是数字。

再看细节，如果首字符是负号，那么在返回数字的时候也应该是负数。如果首字符是正号，我们知道符号可以省略不写。再次，如果字符串转换成数字后**溢出**，应该返回0。最后对于包括溢出在内的各种非法输入，约定返回0，可如果我们输入的就是'0'，转换的结果也是0，这种情况下根据返回值0要怎么区分到底是非法输入呢还是本身就是输入了0？这里我们准备使用一个全局的布尔变量用以区分。

考虑了那么多，可以写出如下严谨很多的代码。

```java
package Chap7;

public class Str2Int {
    public static boolean valid;

    public static int strToInt(String str) {
        valid = false;
        if (str == null || str.length() == 0) {
            return 0;
        }

        boolean isNegative = false;
        long number = 0;
        for (int i = 0; i < str.length(); i++) {
            // 第一位是正负号就跳过
            if (i == 0 && str.charAt(i) == '+' || str.charAt(i) == '-') {
                if (str.charAt(i) == '-') {
                    isNegative = true;
                }
                if (str.length() == 1) {
                    return 0;
                }
                continue;
            }
            // 中间有任何字符不是数字直接返回
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return 0;
            }

            int flag = isNegative ? -1 : 1;
            // '4'的ASCII码就比'0'的ASCII码大4，所以可以减去'0'的ASCII码将字符转数字
            number = number * 10 + flag * (str.charAt(i) - '0');
            if (!isNegative && number > Integer.MAX_VALUE || isNegative && number < Integer.MIN_VALUE) {
                return 0;
            }
        }
        // 全部字符都检查过了，说明字符串合法
        valid = true;
        return (int)number;
    }

    public static void main(String[] args) {
        System.out.println(strToInt("12"));
        System.out.println(strToInt("-12"));
        System.out.println(strToInt("+12"));
        System.out.println(strToInt("+")+ " "+ Str2Int.valid);
        System.out.println(strToInt("0")+ " "+ Str2Int.valid);
        System.out.println(strToInt("12345678901112")+ " "+ Str2Int.valid);
    }
}

```

---

by @sunhaiyu

2018.2.14
