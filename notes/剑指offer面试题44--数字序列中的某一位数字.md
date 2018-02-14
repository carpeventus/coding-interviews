# 剑指offer面试题44--数字序列中的某一位数字

>   ```
>   数字以0123456789101112131415....的格式序列化得到一个字符序列中，在这个序列中，第5位（从0开始计数）是5，第13位是1，第19位是4，等等。请写一个函数，求任意第n位对应的数字
>   ```

## 逐个列举

有一种方法，每列举一个数字就记录当前序列长度和，比如列举到3，序列的长度和为4，列举到10，长度和为12...以此类推。当某次列举使得当前长度和大于给定的n时，停止列举，**第n位数一定在在刚列举的数中**，接下里只需从这个数中找出是哪一位即可。

```java
package Chap5;

public class ADigitInNumberSeq {
    /**
     * 方法1：逐个列举
     */
    public int numAtSeq(int index) {
        if (index < 0) return -1;

        int i = 0;
        int sum = 0;
        while (true) {
            sum += countDigits(i);
            // 一定不要包含=
            if (sum > index) break;
            i++;
        }
        // sum - n是超出的部分，减去1是因为下标从0开始
        return digitAt(i, sum - index - 1);
    }

    /**
     * 返回某数的第d位, 第0位是个位，第1位是十位，以此类推
     */
    private int digitAt(int value, int d) {
        return (value / (int) Math.pow(10, d)) % 10;
    }

    /**
     * 计算某数有多少位
     */
    private int countDigits(int num) {
        if (num == 0) return 1;

        int count = 0;
        while (num != 0) {
            num /= 10;
            count++;
        }
        return count;
    }
}

```

当前长度和sum > index时候退出循环，此时第index位一定在数字i中，那到底是数字i的第几位呢？举个例子

```
012345678910111213...
```

找index = 16的数，当列举到13时候，当前长度和sum = 18 > 16，此时i = 17，容易得知index位是1，即17的第0位。多举几个例子就容易发现只要取数字`i`的第`sum - index -1`位就是我们要找的数字。

## 更快的方法

从数字本身的规律入手。如果要找第1001位，肯定不会考虑0~9折十位吧？接下来10-99也就有180位而已，不考虑，每次不考虑的情况需要减去已缩小查找范围，因此在排除掉0-99共180+10后还剩881，也就是说我们原来是从0开始找到第1001位，现在只需从100开始找到第991位即可。接下来看100-999共2700位，由于881  < 2700，所以这个数必然在100~999的范围内。881 = 270 * 3 + 1，说明这个数是100开始之后的第270个数的第1位（从0开始计算索引），也就是370的第一位数，即7。

有了思路后，要实现几个关键的方法，首先是根据位数得到一个区间的开始数字。区间划分为：0-9， 10-99， 100-999...比如n = 1，一位数的开始数是0; n = 2,两位数的开始数是10; n = 3,三位数的开始数是100...

其次因为要缩小查找范围，所以要根据位数知道该范围内总共有多少个数字，比如n = 1, 一位数0-9的范围共10个数；n = 2，两位数10-99的范围共90个数；n = 3，三位数100-999的范围共900个数....

还需要一个方法，一旦锁定范围，根据当前的位数能知道包含index处数字的数是几，然后从该数中找到要求的那位。

 ```java
package Chap5;

public class ADigitInNumberSeq {
    /*****************************************
     * 方法2
     */
    public int numAtSeq2(int index) {
        if (index < 0) return -1;
        // 位数，digits = 1表示一位数，0-9区间；digits = 2表示两位数，10-99区间...
        int digits = 1;
        while (true) {
            int numbers = numOfRange(digits);
            // 范围锁定，numbers * digits表示该区间共有多少位数字，如digits = 2，范围10-99就有180位
            if (index < numbers * digits) {
                return digitAt2(index, digits);
            }
          	// 缩小范围
            index -= numbers * digits;
            digits++;
        }
    }

    /**
     * 根据位数得到范围内的个数，比如1位，0~9共10个
     * 2位，10~99共90个
     * 3位，100~999共900个
     * ...
     */
    private int numOfRange(int n) {
        if (n == 1) return 10;

        return (int) (9 * Math.pow(10, n - 1));
    }

    /**
     * n位数范围内的的第一个数，比如1位数，0~9，第一个是0
     * 2位数，10~99，第一个数是10
     * 3位数，100~199，第一个数是100
     */
    private int beginNumber(int n) {
        if (n == 1) return 0;
        return (int) Math.pow(10, n - 1);
    }
    // 锁定范围后，根据位数就能得到包含index处那位数的数字，然后从该数中找到要求的那位 
    private int digitAt2(int seqIndex, int digits) {
        int number = beginNumber(digits) + seqIndex / digits;
        return digitAt(number, digits - seqIndex % digits - 1);
    }

    public static void main(String[] args) {
        ADigitInNumberSeq a = new ADigitInNumberSeq();
        System.out.println(a.numAtSeq(1001)); // 7
        System.out.println(a.numAtSeq2(1001)); // 7
    }
}

 ```

重点看`digitAt2`方法

```java
private int digitAt2(int seqIndex, int digits) {
  	int number = beginNumber(digits) + seqIndex / digits;
  	return digitAt(number, digits - seqIndex % digits - 1);
}
```

`seqIndex / digits`表示该区间开始数字其后多少位是我们要找的数字，再加上`beginNumber(digits)`就得到了我们要找的数字，比如上面的例子811 / 3 = 270，说明是开始数字100其后的270位，于是我们知道了这个数是370，接下来`seqIndex % digits`取余操作可以得到数是370第1位，**由于得到的余数是从左往右数的，而digitAt方法是从右往左数的**。因此取`digits - seqIndex % digits -1`才能得到正确结果。

---

by @sunhaiyu

2018.1.20
