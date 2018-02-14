package Chap4;

import java.util.ArrayList;

/**
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字
 */
public class PrintMatrix {

    public ArrayList<Integer> printMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return null;
        }

        ArrayList<Integer> list = new ArrayList<>();
        int left = 0;
        int right = matrix[0].length - 1;
        int top = 0;
        int bottom = matrix.length - 1;

        while (left <= right && top <= bottom) {
            // 从左往右，有一行即可
            for (int col = left; col <= right; col++) list.add(matrix[top][col]);
            // 从上往下,保证至少有两行
            if (top < bottom) {
                for (int row = top + 1; row <= bottom; row++) list.add(matrix[row][right]);
            }
            // 从右往左，至少两行两列
            if (top < bottom && left < right) {
                for (int col = right - 1; col >= left; col--) list.add(matrix[bottom][col]);
            }
            // 从下往上，保证至少三行两列
            if (top < bottom - 1 && left < right) {
                for (int row = bottom - 1; row > top; row--) list.add(matrix[row][left]);
            }
            // 缩小矩形
            left++;
            right--;
            top++;
            bottom--;
        }
        return list;
    }
}
