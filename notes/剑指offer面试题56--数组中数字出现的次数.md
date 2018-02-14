# 剑指offer面试题56--数组中数字出现的次数

>   ```
>   一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
>   要求时间复杂度为O(n)，空间复杂度为O(1).
>   ```

例如输入数组{2, 4, 3, 6, 3, 2, 5, 5},只有4和6这两个数字只出现了一次，其他数字都出现了两次，因此输出4和6。

如果不考虑空间，用哈希表统计频率倒是很简单.....

好吧，没有思路。书中使用的是位运算。

先考虑简单的情况，如果数组中只有一个数字出现了一次而其他数都出现了两次。那么堆数组中的每个数都做异或运算，因为两个相同的数每一位都相同，因此他们异或值为0，所有最后得到的结果就是那个只出现了一次的数。

现在只出现了一次的数有两个，只需要将这两个数分开，使得其中一个数在一个子数组中，另外一个数在另一个子数组中，再使用上面的方法即可。

由于有两个只出现了一次的数，对数组中所有数异或，得到的将是那两个只出现了一次的数的异或值。

就以上面的例子来说，最后会得到4和6的异或值，即100和110的异或值010（省略了前面29个0，因为int型是32位的），可以看到从右往左数的第2位是1，说明4和6在从右往左数的第2位不一样。**在异或结果中找到第一个1的位置，假设是m（这说明那两个只出现了一次的数的第m位一个是1一个是0）。现在以第m位是不是1为标准将数组分成两部分，出现过两次的数一定会被分到同一个部分中，因为他们每一位都相同，第m位当然也相同；只出现过一次的两个数一定会被分到不同的部分中。**

对这两部分分别异或，每一部分就得到了那么只出现了一次的数。

```java
package Chap6;

public class FindNumsAppearOnce {
    //num1,num2分别为长度为1的数组。传出参数
    //将num1[0],num2[0]设置为返回结果
    public void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        if (array == null || array.length < 2) return;
        int res = 0;
        // 这步得到两个只出现一次的数的异或值
        for (int i = 0; i < array.length; i++) {
            res ^= array[i];
        }
        // res肯定不为0，那么res必然有某一位是1，找到第一个1的索引，假设为n;
        // 第n位的异或值为1，也说明了这两个数的第n位不相同
        int indexOfFirstOne = firstBitOfOne(res);
        // 以第n位是不是1为标准，将数组拆分成两个
        // 相同数字一定会被分到同一个子数组，因为相同的数字第n位也是相同的；只出现一次的那两个数字肯定不会分到一个数组中，因为他们的第n位异或值为1，说明他们第n位不相同
        for (int i = 0; i < array.length; i++) {
            if (isBitOfOne(array[i], indexOfFirstOne)) num1[0] ^= array[i];
            else num2[0] ^= array[i];
        }
    }

    /**
     * 找到从右往左数的第一个1的索引，如0110的第一个1索引为1
     */
    private int firstBitOfOne(int val) {
        int index = 0;
        while (val != 0) {
            if ((val & 1) == 1) return index;
            val = val >> 1;
            index++;
        }
        return -1;
    }

    /**
     * 判断从右往左数的第index位是不是1
     */
    private boolean isBitOfOne(int val, int index) {
        val = val >> index;
        return (val & 1) == 1;
    }
}

```

## 相关题目

>   ```java
>   数组中唯一出现一次的数字。
>   在一个数组中除了一个数字只出现一次之外，其他数字都出现了三次，请找出那个只出现一次的数字.
>   ```

使用排序需要O(nlgn)的时间，使用哈希表需要O(n)的空间。有没有更好的？

一个int型有32位，每一位不是0就是1。对于三个相同的数，统计每一位出现的频率，那么每一位的频率和一定能被3整除，也就是说频率和不是3就是0。如果有多组三个相同的数，统计的结果也是类似：频率和不是0就是3的倍数。

现在其中混进了一个只出现一次的数，没关系也统计到频率和中。如果第n位的频率和还是3的倍数，说明只出现一次的这个数第n位是0；如果不能被3整除了，说明只出现一次的这个数第n位是1。由此可以确定这个只出现一次的数的二进制表示，想得到十进制还不简单吗。

```java
package Chap6;

public class AppearOnlyOnce {
    public int findNumberAppearOnlyOnce(int[] numbers) {
        if (numbers == null || numbers.length == 0) throw new RuntimeException("无效输入");
        int[] bitSum = new int[32];
        int bitMask = 1;
      	// 注意先对最低位做位运算，bitSum[0]存的最高位，bitSum[31]存的最低位
        for (int i = 31; i >= 0; i--) {
            for (int number : numbers) {
                int bit = number & bitMask;
                if (bit != 0) bitSum[i] += 1;
            }
            bitMask = bitMask << 1;
        }
        int result = 0;
      	// 转换成十进制时，从最高位开始，从由左至右第一个不为0的位开始
        for (int i = 0; i < 32; i++) {
            result = result << 1;
          	// bitSum[i] % 3为0说明只出现一次的那个数第i为也是0；反之亦反
            result  += bitSum[i] % 3;
        }
        return result;
    }
}

```

要注意的一点是，统计每一位的频率时，是从最低位开始的，bitSum[31]存的是最低位的频率和，而bitSum[0]存的是最高位的频率和，这和人从左往右的阅读习惯一致。从二进制转换成十进制时，则是从最高位开始的，从由左至右第一个不为0的位开始累加，最后得到该数的十进制表示，返回即可。

该方法只需要O(n)的时间，空间复杂度为O(1)。

---

by @sunhaiyu