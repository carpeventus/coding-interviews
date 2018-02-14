# 剑指offer面试题47--礼物的最大价值

>   ```
>   在一个mxn的棋盘的每一格斗放油一个礼物，每个礼物都有一定的价值（大于0）从棋盘的左上角开始，每次可以往右边或者下边移动一格，知道到达棋盘的右下角。给定一个棋盘和上面的礼物，计算我们最多可以拿到多少价值的礼物
>   ```

## 递归--两个方向的深度优先搜索

我第一想到的是递归，然后被书上打脸。不管，还是实现一下吧。

```java
package Chap5;

public class MaxGiftVal {
    /**
     * 方法一：递归，两个方向的深度优先搜索，用一个对象数组保存最大值（只需一个长度）
     */
    public int getMax(int[] gifts, int rows, int cols) {
        if (gifts == null || gifts.length == 0) return 0;
        int[] max = {0};
        select(gifts, 0, 0, rows, cols, 0, max);
        return max[0];
    }

    private void select(int[] gifts, int row, int col, int rows, int cols, int val, int[] max) {
        if (row >= rows || col >= cols) return;
        // 一维数组表示，对应着二维数组中的array[row][col]
        val += gifts[row * cols + col];
        // 到达右下角，和max比较
        if (row == rows - 1 && col == cols - 1) {
            if (val > max[0]) max[0] = val;
        }
        select(gifts, row + 1, col, rows, cols, val, max);
        select(gifts, row, col + 1, rows, cols, val, max);
    }
}

```

每进入一个格子，累加礼物价值。当到达右下角时，将累加和与全局的max变量比较，如果某条路径的累加和比max大，就更新max。边界控制很重要，在超出行或者超出列的范围后，直接返回。然后就不断在两个方向递归——右边或者下边。全局max由于是int型，作为参数并不能在递归调用后被改变，所以需要一个**对象**，由于只需要存放一个值，一个长度为1的对象数组即可。

## 动态规划--要到达当前格子有两个方向

设当前格子能获得的最大礼物价值为$f(i, j)$, 要到达该位置，只有两种情况：

-   从该位置的左边来，即$f(i, j-1)$
-   从该位置的上边来，即$f(i-1, j)$

$f(i, j)$处的礼物价值设为$gift(i, j)$

那么到达$f(i, j)$处能收集到的最大礼物价值为

$$max[f(i, j- 1), f(i-1, j)]+gift[i, j]$$

可以发现，要知道当前格子能获得最大礼物价值，需要用到当前格子左边一个和上面一个格子的最大礼物价值和。所以从左上角开始，计算到达每一个格子能获得最大礼物价值是多少，并保存下这些结果。在后面求其他格子的最大礼物价值时会用到前面的结果。基于这个思路可写出如下代码。

```java
package Chap5;

    /**
     * 方法2：动态规划，到达f(i,j)处拥有的礼物价值和有两种情况：
     * 1、从左边来，即f(i, j) = f(i, j -1) + gift(i, j)
     * 2、从上边来，即f(i, j) = f(i -1, j) + gift(i, j)
     *
     * 保证到达每一个格子得到的礼物价值之和都是最大的，也就是取max[f(i, j-1), f(i-1, j)] +gift(i, j)
     * 可以发现，要知道当前格子能获得最大礼物价值，需要用到当前格子左边一个和上面一个格子的最大礼物价值和
     */

    public int getMaxVal(int[] gifts, int rows, int cols) {
        if (gifts == null || gifts.length == 0) return 0;
        int[][] maxVal = new int[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int left = 0;
                int up = 0;
                if (row > 0) up = maxVal[row -1][col];
                if (col > 0) left = maxVal[row][col -1];
                maxVal[row][col] = Math.max(up, left) + gifts[row *cols+col];
            }
        }
        return maxVal[rows-1][cols-1];
    }
}

```

用到一个二维数组保存到达每一个格子时能获得的最大礼物价值。up和left分别是上面说的$f(i-1, j)$和$f(i, j -1)$，循环完毕后，返回到达右下角处能获得最大礼物价值即可。

## 上面方法的优化——用一维数组代替二维数组

当前礼物的最大价值只依赖$f(i-1, j)$和$f(i, j -1)$这两个格子，因此只需要当前行i，第j列的前面几个格子，也就是$f(i, 0)$~$f(i, j-1)$；以及i -1行的，第j列及其之后的几个格子，也就是$f(i-1, j)$~$f(i-1, cols-1)$

两部分加起来的个数刚好是棋盘的列数cols。所以只需要一个长度为cols的一维数组即可，优化如下。

```java
package Chap5;

    public int betterGetMaxVal(int[] gifts, int rows, int cols) {
        if (gifts == null || gifts.length == 0) return 0;
        int[] maxVal = new int[cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int left = 0;
                int up = 0;
                if (row > 0) up = maxVal[col];
                if (col > 0) left = maxVal[col -1];
                maxVal[col] = Math.max(up, left) + gifts[row *cols+col];
            }
        }
        return maxVal[cols-1];
    }
}

```

`int[] maxVal = new int[cols];` 中，索引为`0~j-1`的就是$f(i, 0)$~$f(i, j-1)$，索引`j~cols-1`的就是$f(i-1, j)$~$f(i-1, cols-1)$

---

by @sunhaiyu

2018.1.24
