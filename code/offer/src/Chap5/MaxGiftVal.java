package Chap5;

/**
 * 在一个mxn的棋盘的每一格斗放油一个礼物，每个礼物都有一定的价值（大于0）
 * 从棋盘的左上角开始，每次可以往右边或者下边移动一格，知道到达棋盘的右下角。
 * 给定一个棋盘和上面的礼物，计算我们最多可以拿到多少价值的礼物
 */
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
    // 上面动态方法的优化，将二位数阿奴换成一维数组
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

    public static void main(String[] args) {
        MaxGiftVal m = new MaxGiftVal();
        int[] gifts = {1, 10, 43, 23, 12, 2, 9, 6, 15, 7, 14, 1, 3, 7, 6, 5};
        System.out.println(m.getMax(gifts, 4, 4));
        System.out.println(m.getMaxVal(gifts, 4, 4));
        System.out.println(m.betterGetMaxVal(gifts, 4, 4));

    }
}
