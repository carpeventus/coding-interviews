# 剑指offer面试题13--机器人的运动范围

> ```
> 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
> ```

此题和*面试题12——矩阵中的路径*有相似之处，依然是回溯法。每来到一个新的且满足条件的格子时，计数加1。除矩形的边界外，任意一个方格都有四个方向可以选择，选择任一方向后来到新的格子又可以选择四个方向，但是一个到达过的格子不能进入两次，因为这将导致对同一个格子的重复计数。也就是说，**一个格子一旦满足条件进入后，就被永久标记为“访问过”，一个满足条件的格子只能使计数值加1。**这是和面试题12有区别的地方（那个例子中是搜索路径，失败路径上的点要重新标记为“未访问”，因为另开辟的新路径需要探索这些点）。

这道题用通俗的话来讲就是：m行n列的所有方格中，有多少个满足**行坐标和列坐标的数位之和小于等于门限值k**的格子？

代码和面试题12长得有点像，但是两个问题是有明显区别的！

```java
package Chap2;

public class RobotMove {
    public int movingCount(int threshold, int rows, int cols) {
        if (rows <= 0 || cols <= 0 || threshold < 0) {
            return 0;
        }

        boolean[] marked = new boolean[rows * cols];
      	// 从(0, 0)处开始
        return move(0, 0, threshold, rows, cols, marked);

    }

    /**
     * 递归方法，每到一个格子就四个方向搜索
     *
     * @param row 当前行
     * @param col 当前列
     * @param threshold 门限值
     * @param rows 总行数
     * @param cols 总列数
     * @param marked 是否访问过
     * @return 当前格子数(等于1)加上4个方向能访问到的格子数的总和
     */
    private int move(int row, int col, int threshold, int rows, int cols, boolean[] marked) {
        int count = 0;
        if (checked(row, col, threshold, rows, cols, marked)) {
            marked[row * cols + col] = true;
		   // 递归对四个方向计数，注意四个方向的搜索不是同时发生，
            count = move(row - 1, col, threshold, rows, cols, marked) +
                    move(row + 1, col, threshold, rows, cols, marked) +
                    move(row, col - 1, threshold, rows, cols, marked) +
                    move(row, col + 1, threshold, rows, cols, marked) + 1;
        }
        return count;
    }

    /**
     * 判断当前格子是否超过门限值，以及边界值的判断
     *
     * @return true如果当前格子满足条件
     */
    private boolean checked(int row, int col, int threshold, int rows, int cols, boolean[] marked) {
        return row >= 0 && row < rows && col >= 0 && col < cols && !marked[row * cols + col] && digitSum(row) + digitSum(col) <= threshold;
    }

    /**
     * 比如数字1234,每位数相加的和将返回10
     * @param number 某数字
     * @return 该数字的数位之和
     */
    private int digitSum(int number) {
        int sum = 0;
        while (number > 0) {
            sum += number % 10;
            number /= 10;
        }
        return sum;
    }
}

```

`checked`方法用于判断当前坐标是否满足边界条件，是否超过门限值。

递归方法`move`是核心，注意递归对四个方向计数时，四个方向的搜索不是同时发生的，一个方向搜索失败（遇到边界或超过门限值）后，退回来进行下一个方向的搜索，回溯法就体现在此。

---

by @sunhaiyu

2017.12.18
