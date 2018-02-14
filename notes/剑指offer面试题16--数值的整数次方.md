# 剑指offer面试题16--数值的整数次方

> ```
> 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。不得使用库函数直接实现，无需考虑大数问题。
> ```

## 连乘

下面是我写的蹩脚程序，注意要考虑次幂为负数的情况。由于负次幂等于base的正次幂的倒数，所以取绝对值直接计算正次幂的情况就行，最后再根据exponent是否为负决定取不取倒数。当base为0时候，结果和次幂无关（都为0，假设0的非正数次幂都为0，因为它们在数学中并没有定义），所以把base == 0的情况单独抽出来直接返回。算法采用了连乘，时间复杂度是O(n)

```java
package Chap3;

public class Power {
    /**
     * @param base     基数
     * @param exponent 次幂
     * @return base^exponent
     */
    public double power_2(double base, int exponent) {
        if (base == 0) {
            return 0;
        }

        double result = 1.0;
        int positiveExponent = Math.abs(exponent);
        for (int i = 0; i < positiveExponent; i++) {
            result *= base;
        }
        return exponent < 0 ? 1 /result : result;
    }
}

```

有没有更快的方法呢？

## 快速幂

我们要求$a^n$，分n为奇数和偶数两种情况，如下

$a^n = a ^{n /2}  \times  a ^{n /2} $，n为偶数

$a^n = a ^{(n-1) /2}  \times  a ^{(n-1) /2} \times a $，n为奇数

假如要求$2^{32}$，按照上面连乘的思路，需要进行31次乘法；采用上面的公式，只需要5次：先求平方，然后求四次方、八次方、十六次方，最后三十二次方。将时间复杂度降到了O(lg n)。

```java
package Chap3;

public class Power {
    /**
     * 非递归。推荐的做法，复杂度O(lg n)
     */
    public double power(double base, int exponent) {
        if (base == 0) {
            return 0;
        }

        double result = 1.0;
        int positiveExponent = Math.abs(exponent);

        while (positiveExponent != 0) {
          	// positiveExponent & 1这句是判断奇数的
            if ((positiveExponent & 1) == 1) {
                result *= base;
            }

            base *= base;
          	// 右移1位等于除以2
            positiveExponent = positiveExponent >> 1;
        }

        return exponent < 0 ? 1 / result : result;
    }

    /**
     * 和上面是同一个思路，递归版本。选择上一个非递归版本的
     */
    private double powerUnsigned(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        }
        if (exponent == 1) {
            return base;
        }

        double result = powerUnsigned(base, exponent >> 1);
        result *= result;
        if ((exponent & 1) == 1) {
            result *= base;
        }
        return result;
    }

    public double power_1(double base, int exponent) {
        if (base == 0) {
            return 0;
        }
        int positiveExponent = Math.abs(exponent);
        double result = powerUnsigned(base, positiveExponent);
        return exponent < 0 ? 1 / result : result;
    }
}

```

上面给出了这个思路的两个实现，一个使用了递归，一个使用了非递归——一般来说非递归的实现更好。

非递归版本看上去和连乘的实现很像，只是循环里面的内容不一样。使用了`positiveExponent & 1`来判断一个正数是否为奇数：**一个正数和1相与，如果值等于1就可以说这个数是奇数，这是因为奇数的二进制表示，最低位一定是1。**这步操作相当于上述数学公式中，n为奇数的情况，此时需要多乘一个base。注意当n奇数时，次幂应该是$(n-1)/ 2$，但在Java的除法中，对于一个奇数n，有`n / 2 == (n-1) /2`，所以不管奇偶，统一右移一位（等同于除以2）。再注意一点，**我们是先判断次幂是否为奇数后再对结果进行平方的，这个顺序不可交换**——至于为什么可以随便举个例子模拟一下能不能得到正确的结果。

说到判断奇数的顺序，在递归版本中恰恰和非递归版本相反。递归版本中是**先进行平方操作后才判断次幂是否为奇数**。对于exponent等于0或1两种简单情况直接返回，这也是终止递归的条件。递归版本基本就是翻译上面的公式——一般来说递归版本的代码简洁易懂。

---

by @sunhaiyu

2017.12.20






