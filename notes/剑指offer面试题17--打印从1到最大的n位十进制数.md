# 剑指offer面试题17--打印从1到最大的n位十进制数

> ```
> 输入数字n，按顺序打印处1到最大的n位十进制数，比如输入3，则打印1~999之间的数
> ```

这道题有陷阱，可能容易想到输入4就打印1~9999，输入5就打印1~99999...那我要是输入100呢？int型不能表示出来吧，甚至更大的值，即便是long型也不能表示出来。

这是一道**大数问题**，牵涉到大数问题我们可以将其转化为字符串表示。因为字符串任意长度都行。

## 在字符串上模拟数字的加法

本题要求按照递增顺序打印出1~最大的n位十进制数，所以字符串的长度定也应该是n。首先将长度为n的字符串中每个字符初始化为0，比如n = 3时，字符串一开始为`000`。我们要做的只有两步：

- 模拟数字那样在字符串上做加法；
- 将字符串表达的数字打印出来，为了可读性需要忽略不必要的0；

从第一步得知，需要经常改变字符串，我们知道**Java中字符串是不可变对象，意味着每次对String的改变都会产生一个新的字符串对象**，这将浪费大量空间，所以在下面的程序中将使用`StringBuilder`。根据这些描述，可写出如下代码。

```java
public class PrintFrom1ToMaxOfNDigit {
    public void printFrom1ToMaxOfNDigit(int n) {
        if (n <= 0) {
            return;
        }

        StringBuilder sb = new StringBuilder();
      	// 初始化字符串，比如n = 3就初始化为"000"
        for (int i = 0; i < n; i++) {
            sb.append("0");
        }

        while (stillIncrease(sb, n)) {
            print(sb);
        }

        System.out.println();
    }
}

 private boolean stillIncrease(StringBuilder sb, int len) {
        // 进位,应该要给下一位相加，所以设置在循环外
        int toTen = 0;
        // 从个位开始加，如果有进位就看十位...如果到最高位还有进位，说明溢出；
        for (int i = len - 1; i >= 0; i--) {
            // 加上进位toTen
            int sum = sb.charAt(i) - '0' + toTen;
            // 在个位上，先自增
            if (i == len - 1) {
                sum++;
            }

            if (sum == 10) {
                // 进位溢出
                if (i == 0) {
                    return false;
                    // 需要进位，当前位设为0
                } else {
                    sb.setCharAt(i, '0');
                    // 进位了
                    toTen = 1;
                }
            } else {
                sb.setCharAt(i, (char) (sum + '0'));
                // 在某位上自增后不再进位，自增完成立即退出循环
                break;
            }
        }
        return true;
    }

    private void print(StringBuilder sb) {
        int start = sb.length();
        // 找到第一个不为0的索引
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) != '0') {
                start = i;
                break;
            }
        }
        // 如果全是0，就不会打印
        if (start < sb.length()) {
            System.out.print(sb.substring(start) + " ");
        }
    }
}
```

`stillIncrease`方法会对当前数进行加1操作，该方法返回一个布尔值，用来表明当前数还能不能继续增长。比如n = 3时，当前数为999已经是最大了，不能再增长，此时如果调用该方法就应该返回false，因此跳出循环，不会打印1000（虽然不打印，但实际在该方法中字符串已经从"999"被更新到了“1000”）。当当前数可以继续增长时，会先对个位上的数进行自增操作，如果此时得到的sum < 10，说明不需向前进位，直接退出并返回；如果sum == 10，说明需要向前进位，需要将当前位设置为0，然后进位设置为1，在下一循环中，需要加到在前一位上，继续判断这一位上需不需要进位......直到某位上sum  < 10循环才终止。

`print`方法，为了符合人的阅读习惯，比如"002"其实就是数字2，应保证**从左到右第一个不为0的数前面的0不会被打印出来。**

## 递归的方法排列出所有可能的情况

n = 3时候我们需要打印的条目依次是

```
001
002
003
004
...
009
010
011
012
...
099
100
101
...
990
991
...
999
```

可以发现规律：我们首先对个位进行了0~9的排列。之后对十位也进行了0~9的排列，最后才是百位的0~9排列。由此可以想到**用递归的方式，从最高位开始，不断设置下一位要递归的数，一直递归到个位，这样就会先对个位进行排列，递归调用一层层返回，最后才会对最高位进行排列**——正好符合上面的分析。

```java
public void printFrom1ToMaxOfNDigitRecur(int n) {
  	if (n <= 0) {
    	return;
  	}

  	StringBuilder sb = new StringBuilder();
  	for (int i = 0; i < n; i++) {
    	sb.append("0");
  	}
	// 递归中的方法为了对下一位排列，所以是index + 1，为了对最高位排列应该传入-1
  	printRecur(sb, n, -1);
  	System.out.println();
}

private void printRecur(StringBuilder sb, int len, int index) {
  	if (index == len - 1) {
    	print(sb);
    	return;
  	}

  	for (int i = 0; i < 10; i++) {
    	// 一定是先设置了字符，然后再递归
    	sb.setCharAt(index + 1, (char) (i + '0'));
    	printRecur(sb, len, index + 1);
  	}
}
```

下面这两行不可交换顺序，必须是先设置好了字符才能打印啊。

```java
sb.setCharAt(index + 1, (char) (i + '0'));
printRecur(sb, len, index + 1);
```

另外递归方法中是不断对`index + 1`，即下一位数进行排列，所以为了对最高位进行排列，public方法中参数应该传入-1。当设置好个位数后，进入下一层递归 中就会调用print方法执行打印。

---

by @sunhaiyu

2017.12.21
