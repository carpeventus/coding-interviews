package Chap2;

/**
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。
 * 如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。 例如下面矩阵

 a b t g
 c f c s
 j d e h

 包含一条字符串"bfce"的路径，但是矩阵中不包含"abfb"路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
 */
public class PathIn2DArray {
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        // 用于将当前路径上的访问过的点标记为“已访问”，防止同一个点访问两次
        boolean[] marked = new boolean[matrix.length];
        // 所有点都作为起点搜索一次
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (hasPathTo(matrix, rows, cols, row, col, str, 0, marked)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPathTo(char[] matrix, int rows, int cols, int row, int col, char[] str, int len, boolean[] marked) {
        int index = row * cols + col;
        if (row < 0 || row >= rows || col < 0 || col >= cols || matrix[index] != str[len] || marked[index]) {
            return false;
        }
        // 递归深度能到字符串末尾，说明有这条路径
        if (len == str.length - 1) {
            return true;
        }

        marked[index] = true;
        // 四个方向上有没有可以到达下一个字符的路径，有任意一个方向可以就继续下一个字符的搜索
        if (hasPathTo(matrix, rows, cols, row, col - 1, str, len + 1, marked) ||
                hasPathTo(matrix, rows, cols, row - 1, col, str, len + 1, marked) ||
                hasPathTo(matrix, rows, cols, row, col + 1, str, len + 1, marked) ||
                hasPathTo(matrix, rows, cols, row + 1, col, str, len + 1, marked)) {
            return true;
        }
        // 对于搜索失败需要回溯的路径上的点，则要重新标记为“未访问”，方便另辟蹊径时能访问到
        marked[index] = false;
        return false;
    }
}
