# 剑指offer面试题29--顺时针打印矩阵

> ```
> 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下矩阵：
> 1 2 3 4 
> 5 6 7 8
> 9 10 11 12
> 13 14 15 16 
> 则依次打印出数字
> 1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
> ```

顺时针打印一个矩阵需要按照如下顺序：

1. 从左往右遍历每个数字，只需保证至少有一行即可。
2. 从上往下遍历每个数字，保证至少有两行。
3. 从右往左遍历每个数字，除了保证至少有两行，还要保证至少两列。
4. 从下往上遍历每个数字，保证至少有三行两列。

每打印完一圈，矩形区域缩小，对内层的矩形再次执行上述操作（按顺序）....到最后一个矩形时，矩形区域很小，上述操作可能不会全部执行，那也没有关系，只要限制了条件，后续的遍历是不会得到执行的。

关键是如何控制矩形的边界，正确地缩小矩形的边界。为此设置了4个变量

- left表示矩形的左边界
- right表示矩形的右边界
- top表示矩形的上边界
- bottom表示矩形的下边界

一开始

```
left = top = 0;
right = 列数
bottom = 行数
```

每次打印完一圈后，缩小矩形区域，于是

```
left++;right--;
top++;bottom--;
```

搞清楚上面打印顺序和相应的条件后，可写出如下程序：

```java
package Chap4;

import java.util.ArrayList;

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

```

只要`left <= right && top <= bottom`说明还存在矩形区域，需要继续打印。

---

by @sunhaiyu

2018.1.5