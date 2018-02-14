# 剑指offer面试题60--n个骰子的点数

> ```
> 把n个骰子扔在地上，所有骰子朝上的一面的点数之和为s。输入n，打印出s的所有可能的值出现的概率。
> ```

## 递归

n个骰子，我们可以投掷n次，累加每次掷出的点数即可。因此要求出n个骰子的点数和，可以从n个骰子先取出一个投掷，这一个骰子只可能出现1-6中的某一个数，我们需要计算的是1-6每个点数与剩下n -1个骰子的点数和；接下来进行第二次投掷，现在骰子堆中还有n - 1个骰子，对于这n -1个骰子，继续从中选一个出来投掷，并在上次掷出的点数上累加...这显然是个递归过程。不断投掷，直到最后一个骰子也被投掷并累加点数，之后骰子数为0，也达到了递归终止的条件。

一个骰子可能出现的点数和是1-6，两个骰子可能出现的点数和为2-12,三个骰子可能出现的点数和为3-18，因此n个骰子可能出现的点数为n-6n。可以用一个大小为6n - n + 1的数组存放每个可能的点数和出现的频率，比如该数组下标0处存放的是点数和为n的出现次数；而下标6n -n处存放的是点数和6n的出现次数。n个骰子，每次投掷都有6种情况，因此总的点数和情况为$6 ^ n$种。要求某个点数和出现的概率，用该点数和出现的频次除以$6 ^ n$即可。

```java
package Chap6;

public class PrintProbability {
    private int sideNum = 6;

    public void printProbability(int n) {
        if (n < 1) return;
        int maxVal = n * sideNum;
        int[] probabilities = new int[maxVal - n + 1];
        getProbabilities(n, n, 0, probabilities);
        int total = (int) Math.pow(sideNum, n);
        for (int i = n; i <= maxVal; i++) {
            System.out.println("s=" + i + ": " + probabilities[i - n] + "/" + total);
        }
    }

    private void getProbabilities(int n, int cur, int sum, int[] p) {
        if (cur == 0) p[sum - n]++;
        else for (int i = 1; i <= sideNum; i++) {
            getProbabilities(n, cur - 1, sum + i, p);
        }
    }

    public static void main(String[] args) {
        PrintProbability p = new PrintProbability();
        p.printProbability(3);
    }
}

```

注意`int[] probabilities`，获取点数和的频次时都有一个减去骰子个数n的操作，这是因为点数和最小就是n，为了节省数组空间，就将点数和最小的情况存放到了数组下标0处。

模拟投掷三个骰子的递归过程的话，就是如下顺序

```
1+1+1
1+1+2
1+1+3
1+1+4
1+1+5
1+1+6
1+2+1
1+2+2
.....
6+1+1
6+1+2
.....
6+5+6
6+6+1
6+6+2
.....
6+6+6
```

## 动态规划

假设k个骰子掷出的点数和为$s$的情况数为$f(k, s)$。**因为第k个骰子只能掷出1-6这几种可能的点数，所以k -1个骰子掷出s-1、s-2、s-3、s-4、s-5、s-6的情况数总和就是k个骰子掷出点数和s的情况数。**即

$$f(k, s) = \sum f(k-1, s-i), 1 \le i \le 6, s  > i$$

$f(1, s)$表示只有一个骰子时，掷出点数和s的情况数，显然$f(1, s) = 1$, 就是说只有一个骰子，不管是掷出1-6的哪个数，都只有一种情况（废话！）

我们最终就是要求$f(n, s)$

有了这个式子，代码写起来就简单了。用一个二维数组`int[][] f = new int[n + 1][maxVal + 1];`表示上面的$f$。`f[k][s]`就表示$f(k, s)$

```java
package Chap6;

public class PrintProbability {
    private int sideNum = 6;
    /**
     * 动态规划
     */
    public void printProbabilityDP(int n) {
        if (n < 1) return;
        int maxVal = n * sideNum;
        int[][] f = new int[n + 1][maxVal + 1];
        // 初始化f(1, i) 1 <= i <= 6, 只有一个骰子，点数和为i的情况只有一种
        for (int i = 1; i <= sideNum; i++) {
            f[1][i] = 1;
        }
        // 逐个增加骰子个数
        for (int k = 2; k <= n; k++) {
            // k个骰子可能的点数和是k~6*k
            for (int sum = k; sum <= k * sideNum; sum++) {
                for (int i = 1; sum > i && i <= sideNum; i++) {
                    f[k][sum] += f[k - 1][sum - i];
                }
            }
        }

        int total = (int) Math.pow(sideNum, n);
        for (int sum = n; sum <= maxVal; sum++) {
            System.out.println("s=" + sum + ": " + f[n][sum] + "/" + total);
        }
    }
}

```

$$f(k, s) = \sum f(k-1, s-i), 1 \le i \le 6, s  > i$$这个关键公式体现在下面的循环中

```java
for (int i = 1; sum > i && i <= sideNum; i++) {
	f[k][sum] += f[k - 1][sum - i];
}
```

### 更省空间的版本，但是没有上面的直观

上面的算法，使用了一个二维数组，空间消耗较大。仔细观察，最后求概率时只用到了`f[n][sum]`，也就是说`f[0]~f[n-1]`的值都没必要保存。它们作为中间计算的临时变量足以，只要最后能求得n个骰子的点数和情况总数即可。

因此我们将数组降维到`int[][] f = new int[2][maxVal + 1];`同时设置一个int 型的flag，只能取值0或者1。

其中`f[flag][i]`始终表示$f(k -1, i)$，即`f[flag][i]`表示k -1个骰子点数和i的情况总数，而`f[1-flag][s]`表示$f(k, s)$,即`f[1-flag][s]`表示k个骰子点数和为s的情况总数，根据上面的推导，有`f[1-flag][s] = sum{f[flag][s-i]}, i=1, 2,..6`

一旦求出$f(k, s)$，$f(k -1, i)$就没有用了，因为$f(k, s)$马上就会成为$f(k -1, i)$用于计算新的$f(k, s)$，直到最后k增加到等于n。这句话的意思是：本轮中是$f(k, s)$的在下一轮将变成$f(k -1, i)$，所以每次求得`f[1-flag][s]`，都需要使`flag = 1 -flag`，通过改变flag的值不断交换`f[0]`和`f[1]`以达到$f(k, s)$和$f(k -1, i)$的交替。

这样交替下来，为了存放最新的计算结果，旧的计算结果被不断丢弃,最后数组中只保留了最新两次的计算结果。因为$f(k ,s)用$`f[1-flag][s]`表示，但跳出循环之前会执行flag = 1 -flag，所以最终$f(n, s)$实际上是`f[flag][s]`，一定要搞清楚。

```java
package Chap6;

public class PrintProbability {
    private int sideNum = 6;
    /**
     * 更省空间的动态规划
     */
    public void printProbabilityBetterDP(int n) {
        if (n < 1) return;
        int maxVal = n * sideNum;
        int[][] f = new int[2][maxVal + 1];
        int flag = 0;
        // 初始化f(1, i) 1 <= i <= 6, 只有一个骰子，点数和为i的情况只有一种
        for (int i = 1; i <= sideNum; i++) {
            f[flag][i] = 1;
        }
        // 逐个增加骰子个数
        for (int k = 2; k <= n; k++) {
            // k个骰子可能的点数和是k~6k
            for (int sum = k; sum <= k * sideNum; sum++) {
                // 求f(k -1, s-1)~f(k-1, s-6)的情况和
                int s = 0;
                for (int i = 1; sum > i && i <= sideNum; i++) {
                    s += f[flag][sum - i];
                }
                // 重新给f(k, s)赋值
                f[1 - flag][sum] = s;
            }
            flag = 1 - flag;
        }

        int total = (int) Math.pow(sideNum, n);
        for (int sum = n; sum <= maxVal; sum++) {
            // f(k, s)也就是f[1-flag][sum], 但之后flag = 1 -flag,所以调用f[flag]才能得到f(k, s)
            System.out.println("s=" + sum + ": " + f[flag][sum] + "/" + total);
        }
    }
}

```

代码中有一个局部变量`int s`,用于计算本轮的$f(k, s)$，之后才赋值给`f[1 -flag][sum]`。不能`f[1-flag][sum] += f[flag][sum - i]`这样写，因为`f[1-flag][sum] `还存放的是前一轮的值，这会影响本轮的累加和，除非累加前先将`f[1-flag][sum] `里的值重置为0。这里没有这样做，而是用一个局部变量s直接覆盖原来的值。

---

by @sunhaiyu

2018.2.7
