package Chap2;

/**
 * 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于k的格子。
 * 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
 */
public class RobotMove {
    public int movingCount(int threshold, int rows, int cols) {
        if (rows <= 0 || cols <= 0 || threshold < 0) {
            return 0;
        }

        boolean[] marked = new boolean[rows * cols];
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
     * @return 当前格子加上4个方向能访问到的格子数的总和
     */
    private int move(int row, int col, int threshold, int rows, int cols, boolean[] marked) {
        int count = 0;
        if (checked(row, col, threshold, rows, cols, marked)) {
            marked[row * cols + col] = true;

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
