# 剑指offer面试题12--矩阵中的路径

> ```
> 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。 例如下面矩阵
>
> a b t g 
> c f c s 
> j d e h
>
> 包含一条字符串"bfce"的路径，但是矩阵中不包含"abfb"路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
> ```

这有点像图的深度优先搜索。除了矩阵边界，每个点都可以在4个方向上选择任意一个前进。当某一条路径失败后，需要回溯到上一次选择处，选择另一个方向再尝试。如果该处的方向都被尝试过了，继续回溯到上次选择处...每一次选择都会来到一个新的格子，在这个格子处又有若干个方向可选择，就这样不断前进、回溯、再前进，直到找到一条满足要求的路径为止；如果所有点都作为起点搜索一遍后还是没有找到满足要求的路径，说明在这个矩阵中不存在该条路径。

上面的描述，使用递归比较好理解。还有一点需要注意，由于路径上访问过的点不能进入第二次，所以需要一个`boolean[] marked`标记那些**当前路径上被访问过的点**。

```java
package Chap2;

public class PathIn2DArray {
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if (matrix == null || rows <= 0|| cols <=0 || str == null) return false;
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

      	// 由于用一维数组表示二维矩阵，第row行第col列，就是row * cols + col
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

```

递归方法` hasPathTo`中有个参数len，它表示当前递归的深度。第一次调用传入0，之后在其基础上的每一次递归调用len都会加1，递归的深度也反映了当前路径上有几个字符匹配相等了。能**绕过**下面的if判断，说明当前字符匹配相等了。接下来判断递归深度是否到达字符串末尾，比如字符串`abc`，第一次调用hasPath（传入len为0，递归深度为0）绕过了第一个if，说明字符a已经匹配相等，再判断`len == str.length - 1`不通过；再次递归，此时深度为1，绕过了第一个if，说明字符b已经匹配相等，再判断`len == str.length - 1`不通过；再次递归，此时深度为2，绕过了第一个if，说明字符c已经匹配相等，所有字符匹配相等，因此再次判断`len == str.length - 1`通过，应该返回true.

```java
if (row < 0 || row >= rows || col < 0 || col >= cols || matrix[index] != str[len] || marked[index]) {
	return false;
}

// 递归深度能到字符串末尾，说明有这条路径
if (len == str.length - 1) {
  	return true;
}

```

还有注意：四个方向搜索不是同时发生的，当某一个方向搜索失败后，会退回来进行下一个方向的搜索，回溯法就体现在此。

当搜索失败时，别忘了`marked[index] = false;`，将搜索失败路径上点重新标记为“未访问”，以便回溯后选择其他方向继续前进时能再次访问到这些点。

---

by @sunhaiyu

2017.12.18
