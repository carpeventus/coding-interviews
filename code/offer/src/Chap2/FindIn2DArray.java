package Chap2;

/**
 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class FindIn2DArray {
    /**
     * 我想到的做法，对每一行的一维数组作二分查找.
     * 如果矩阵是M*N的，一次二分查找是O(lg N), M行就是O(Mlg N)
     *
     * @param target 要查找的数
     * @param array  二维数组
     * @return true如果在数组中找到target
     */
    public boolean Find2(int target, int[][] array) {
        if (array != null && array.length > 0) {
            // 注意high在循环外，一旦值更新下次循环不会再被初始化，可减少比较次数
            int high = array[0].length - 1;
            for (int i = 0; i < array.length; i++) {
                int low = 0;
                while (low <= high) {
                    int mid = low + (high - low) / 2;
                    if (target > array[i][mid]) {
                        low = mid + 1;
                    } else if (target < array[i][mid]) {
                        high = mid - 1;
                    } else {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    /**
     * 更推荐的做法，由于矩阵从上到下递增，从左到右递增。
     * 总是和二维矩阵的右上角元素比较（对称地，左下角也可以）
     * 如果目标比右上角的大，删除该行，行指针向下移动；如果比右上角的小，删除该列，列指针左移
     */
    public boolean Find(int target, int[][] array) {
        if (array != null && array.length > 0) {
            int N = array.length; // 总行数
            int col = array[0].length - 1; // 列索引
            int row = 0; // 行索引
            // array[row][col]是右上角的元素
            while (row < N && col >= 0) {
                if (target < array[row][col]) {
                    col--;
                } else if (target > array[row][col]) {
                    row++;
                } else {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}