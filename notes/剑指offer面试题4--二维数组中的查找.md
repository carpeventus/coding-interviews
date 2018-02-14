# 剑指offer面试题4--二维数组中的查找

> ```
> 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
> ```

根据题目，一个满足要求的二维数组长下面这样。

```
1	2	8	9
2	4	9	12
4	7	10	13
6	8	11	15
```

## 对每一行进行二分查找

我的做法：每一行的一维数组已经有序，所以使用**二分查找**在每一行中查找，一旦找到立即返回。对于一个M*N的二维数组，一次二分查找的时间复杂度为O(lg N), M行就是O(Mlg N)。根据上面的表述已经可以很轻松地写出代码了。

```java
package Chap2;

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
            for (int i = 0; i < array.length; i++) {
                int high = array[i].length - 1;
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
}
```

代码还可以优化，根据这个二维数组的特点，**任意一个位置其右边的所有元素和下边的所有元素都是大于它的**。所以当我们**按照从上到下的顺序**遍历每一行的一维数组时，如果某次有

```java
else if (target < array[i][mid]) 
  {high = mid - 1;}
```

那么以后的若干行，都不用再和mid之后的元素比较了。因为`array[i][mid]`处的元素和它下边、右边的值相比是最小的，这个最小的元素都大于target，下边的、右边的值不用比较也知道比target大。举个例子

```
1	2	8	9	16
2	4	9	12	17
4	7	10	13	18
6	8	11	15	19
```

如果target是7，当在第一行中二分查找时候，`target < array[i][mid] = 8` ,high被更新为1， 既然7 < 8，对于8的右边、下边所有元素都是大于7的，无需再比较。所以下次循环中数组应该缩小为

```
2	4
4	7
6	8
```

最开始的想法中，每次循环都将high重新初始化。考虑上面的优化，我们**当high的值被更新后，在后续的循环中不再重新被初始化即可。**

```java
// 优化前
for (int i = 0; i < array.length; i++) {
    int high = array[i].length - 1;
    int low = 0;
}

// 优化后
int high = array[0].length - 1;
for (int i = 0; i < array.length; i++) {
  	int low = 0;
}
```

由于是从上到下的顺序遍历每一行，所以优化操作不能用于low。（当然如果是从底向上遍历的顺序，只能优化low而不能优化high）

## 和右上角/左下角元素比较不断缩小范围

上面二分查找对high的优化是个很好的启发，如果我们将target和矩阵中的某一个数比较，如果`target > arr[i][j]`，那么`array[i][j]`的右边列所有元素、下边行的所有元素都有可能含有与target相等的数；同理如果`target < arr[i][j]`，那么`array[i][j]`的左边列所有元素、上边行的所有元素都有可能含有与target相等的数。这样问题就复杂了，编写代码时可能无从下手（到底选择往哪个方向移动？），和矩阵的左上角、右下角也是一个道理。

我们来看右上角/左下角，由于这两个位置对称，现只看右上角。**这个位置在一行中是最大的元素，在一列中是最小的元素**。根据这个特点，当右上角的值大于target，右上角元素那一列都是大于target的，所以可以直接剔除这一列；当右上角的值小于target，右上角元素那一行都是小于target的，所以可以直接剔除这一行；当右上角的值等于target说明找到，立即返回。就这样不断将范围一行一列地缩小...

根据描述可写出如下代码

```java
public class FindIn2DArray {
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
```

---

by @sunhaiyu

2017.12.14
