# 剑指offer面试题39--数组中出现次数超过一半的数字

>   ```
>   数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
>   ```

可以先将数组排序，然后统计数字出现的次数，先将第一个数字出现次数初始化为1，如果遇到同样的数字，就加1，遇到不一样的就重新初始化为1重新开始计数，知道某个数字计数值大于n / 2（n是数组的长度），终止循环，返回当前数字就是我们要的答案。

但是排序算法的时间复杂度为O(nlgn)，有没有更快的方法呢。

## 切分法，时间复杂度O(n)

注**意到排序之后，如果数组中存在某个数字超过数组长度的一半，那么数组中间的数字必然就是那个出现次数超过一半的数字。**这就将问题转化成了求数组的中位数，快速排序使用的切分算法可以方便地找出中位数，且时间复杂度为O(n)，找出中位数后还需要再遍历一边数组，检查该中位数是否出现次数超过数组长度的一半。总结一下，基于切分法有如下两个步骤：

-   切分法找出中位数
-   检查中位数

```java
public class MoreThanHalf {

    public int moreThanHalfNum(int[] array) {
        if (array == null || array.length == 0) return 0;

        int mid = select(array, array.length / 2);
        return checkMoreThanHalf(array, mid);
    }

    /**
     * 统计中位数是否超过数组元素个数的一半，若没有默认返回0
     */
    private int checkMoreThanHalf(int[] array, int number) {
        int count = 0;
        for (int a : array) {
            if (a == number) count++;
        }

        return count > array.length / 2 ? number : 0;
    }

    /**
     * 选择排名为k的元素,只是部分排序，时间复杂度为O(N)
     */
    private int select(int[] array, int k) {
        int low = 0;
        int high = array.length - 1;
        // high==low时只有一个元素，不切分
        while (high > low) {
            int j = partition(array, low, high);
            if (j == k) return array[k];
            else if (j > k) high = j - 1;
            else if (j < k) low = j + 1;
        }

        return array[k];
    }

    /**
     * 快速排序的切分方法
     */
    private int partition(int[] array, int low, int high) {
        int i = low;
        int j = high + 1;
        int v = array[low];
        while (true) {
            while (array[++i] < v) if (i == high) break;
            while (array[--j] > v) if (j == low) break;
            if (i >= j) break;
            swap(array, i, j);
        }
        swap(array, low, j);
        return j;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
```

select方法是通用的选择排名为k的元素，只要参数传入n / 2即可求得中位数。partition方法会返回一个索引`j`，该索引的左半部分全小于索引出的值，右半部分全大于索引处的值，如果指定的排名k == j，那么问题就解决了；如果k < j，则需要在左半数组中继续查找；如果k > j，则需要在右半数组继续查找。循环中保证了low左边的值都小于arr[low, high]，而high右边的值都大于arr[low, high]，通过不断切分和k比较，直到子数组中只含有第k个元素，此时arr[0]~arr[k - 1]都小于a[k]而arr[k+1]即其后的所有都大于a[k]，a[k]刚好是排名第k的元素。

另外while循环外还有一个`return a[k]`，保证了当数组长度为1，即high == low时不能进入while循环，应该直接返回a[k].

找到中位数之后遍历一边数组，检查中位数出现次数是否超过数组长度一半即可。

## 基于数组特点，时间复杂度O(n)

**数组中有一个数字出现次数超过了一半，说明这个数字的出现次数比其他所有数字的出现次数之和加起来还要多**。于是可以考虑下面的方法：使用两个变量，result表示当前数字，count表示当前数字的计数值。在遍历到一个新的数字时候，计数为1，将该数字赋值给result，之后遇到的数字如果和result相同，那么count加1,；遇到不一样的count减1，直到count等于0，此时需要重新初始化，即将新的数字赋值给result并令count为1......这样遍历完所有数字时，最后result表示的数值就**可能**是我们要找的值，注意是可能，因为输入数组可能本身就不满足里面有某个数字出现次数超过一半。因此和上面的找中位数方法一样，得到的result还需要进一步检验。

```java
public int findNumMoreThanHalf(int[] array) {
  	if (array == null || array.length == 0) return 0;

  	int count = 1;
  	int result = array[0];
  	for (int i = 1; i < array.length; i++) {
    	if (count == 0) {
      	result = array[i];
      	count = 1;
    	}

    	else if (array[i] == result) count++;
    	else count--;
  	}
  	return checkMoreThanHalf(array, result);
}
```

`checkMoreThanHalf`方法和上面的一样，不在赘述。

---

by @sunhaiyu

2018.1.17

