# 剑指offer面试题11--旋转数组中的最小数字

> ```
> 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。 NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
> ```

**题目给出的是非递减排序数组，所以不是严格递增的，可能有相同元素的情况。**

## 顺序遍历

我的解法：两个子数组都是递增的，只有在两个子数组的分界线处，才会有前一个字符大于后一个字符。时间复杂度为O(n)

```java
public int minNumberInRotateArray(int [] array) {
  	if (array.length == 0) {
    	return 0;
  	}

  	for (int i = 0;i < array.length - 1;i++) {
    	if (array[i] > array[i + 1]) {
      		return array[i + 1];
    	}
  	}
  	return array[0];
}
```

## 二分查找

由于有序数组旋转后，被分成了两个有序的子数组，因此可以用**二分查找**，且左半数组的值全大于等于右半数组的值。我们要找的最小元素恰好是右半数组的第一个元素，或者说左半数组末尾后一个元素。

和二分查找一样，一个指针low指向数组首元素，一个high指向尾元素。还有一个指针mid，这里注意：mid不是要和哪个特定的值比较来缩小范围，根据旋转数组的特点，我们始终将mid和high处的值比较。分三种情况

- array[mid] > array[high];此时mid一定还处于左半数组中，而要找的最小值在右半数组中，最小值肯定在mid的右边，所以可以直接将low移动到mid的下一个位置，即low = mid + 1。举个例子{3, 4, 5, 1, 2}，mid处的5比high处的2大，直接更新low = 3，数组被缩小到{1, 2}
- array[mid] < array[high];此时mid一定处于右半数组中，最小值可能在mid处也可能在mid的左边。所以high只能缩小到mid处，即high = low。举个例子{4, 5, 1, 2, 3}，mid处的1小于high处的3，只能将high移动到1处，数组缩小为{4, 5, 1}，如果像上面类似令high = mid - 1，最小值再这个例子中就被跳过了！
- array[mid] == array[high];此时无法分辨mid处于左半数组还是右半数组。比如{1, 0, 1, 1, 1}和{1, 1, 1, 0, 1}都是数组{0, 1, 1, 1, 1}的旋转数组。此时mid处和high处的值一样，若根据low缩小范围，对于{1, 0, 1, 1, 1}最小值将被跳过；如果根据high缩小范围，对于{1, 1, 1, 0, 1}最小值也会被跳过。此时的处理方法是暂时放弃二分查找，**既然mid处和high处值相同，那么让`high--`，让mid和high的前一个值继续比较。**如果mid和high处都是最小值，就算放弃了high最后还是会在mid处找到最小值。

**只要low < high(不含等于)，就不断重复上面过程，最后将范围缩小到只有一个元素后，low == high跳出循环。**其实low == high时候还进入循环，也没有错，此时只会造成high--，而我们返回的是array[low]，值不会影响，但是何必进行这次无意义的比较呢。

根据上面的描述已经可以写出代码了。

```java
package Chap2;

public class MinNumberInRotateArray {

    public int minNumberInRotateArray(int [] array) {
        if (array==null || array.length == 0) {
            return 0;
        }

        int low = 0;
        int high = array.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
          	// 此时mid一定在左半数组中，最小值在mid的右边
            if (array[mid] > array[high]) {
                low = mid + 1;
              // 此时mid一定在右半数组中，最小值在mid的左边或就是mid本身
            } else if (array[mid] < array[high]) {
                high = mid;
              // 暂时放弃二分查找，和前一个字符继续比较
            } else {
                high--;
            }
        }
      	// low == high时推出循环并返回
        return array[low];
    }
}

```

时间复杂度为O(lg n)。

上面始终将mid和high比较，可不可以mid和low比较呢，可以但是和与high比较相比更麻烦。

试想如果最后范围缩小到剩下{1, 2}此时array[low] == array[mid]，如果low++就跳过最小元素了，此种情况可以写一个`min(int low, int high)`方法，直接返回[low, high]范围内的最小值。比起用high来和mid比较，麻烦了不少。

---

by @sunhaiyu

2017.12.18
