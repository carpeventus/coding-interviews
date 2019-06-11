# 剑指offer面试题51--在排序数组中查找数字

>   ```
>   统计一个数字在排序数组中出现的次数。
>   ```

## 遍历

这是很容易想到的方法了，但时间复杂度O(n)，不推荐。

```java
package Chap6;

public class NumOfK {
    /**
     * 方法一：遍历，O(n)复杂度，不推荐
     */
    public int getNumberOfK(int[] array, int k) {
        if (array == null) return 0;

        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == k) {
                count++;
            }
        }
        return count;
    }
}
```

## 二分法

既然是排序数组，自然容易想到二分法查找。假设要查找的数字为k，只需找到第一个k的下标、最后一个k的下标，就达到了计数的目的。

先来找第一个k的下标：由于有多个k，因此当在mid处找到k时，不要急于返回。如果k之前的数还是k，那么我们还要继续在左子数组中查找，直到某个mid处的值为k但是它前面的值不是k、或者mid都等于0了（即第一个k出现在下标0处）；

然后找最后一个k的下标：同样地，如果mid处是k，且它的后面还是k，需要在右子数组中继续查找，直到mid处为k但它后面不是k、或者mid都等于array.length -1(即最后一个k出现在数组的最后一个位置)；

得到第一个k和最后一个k的下标后，只需将两个索引相减再加上1即可。

根据上面的描述，可写出如下代码

```java
package Chap6;

public class NumOfK {
    /**
     * 方法二：二分法找到第一个k和最后一个k，时间复杂度O(nlgn)
     */
    public int numberOfK(int[] array, int k) {
        if (array == null) return 0;
        int from = getFirstOfK(array, k, 0, array.length - 1);
        int to = getLastOfK(array, k, 0, array.length - 1);
        if (from == -1 && to == -1) return 0;
        else return to - from + 1;
    }
	// 找到第一个k的下标
    private int getFirstOfK(int[] array, int k, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (k < array[mid]) high = mid - 1;
            else if (k > array[mid]) low = mid + 1;
            else {
                if (mid > 0 && array[mid - 1] == k) high = mid - 1;
                else return mid;
            }
        }
        return -1;
    }
	// 找到最后一个k的下标
    private int getLastOfK(int[] array, int k, int low, int high) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (k < array[mid]) high = mid - 1;
            else if (k > array[mid]) low = mid + 1;
            else {
                if (mid < array.length - 1 && array[mid + 1] == k) low = mid + 1;
                else return mid;
            }
        }
        return -1;
    }
}
```

## 更巧妙的二分法

注意到要查找的数组元素都是int型的，我们知道普通二分查找只需稍微改变下返回值（返回low），就能得到一个排名方法——在数组中比k小的数有多少个，即k在数组中排名多少。有个很聪明的思路就是，在数组中查找接近k的浮点数。

```java
private int rank(int[] array, double k) {
  	int low = 0;
  	int high = array.length - 1;
 	while (low <= high) {
    	int mid = low + (high - low) / 2;
    	if (k < array[mid]) high = mid - 1;
    	else if (k > array[mid]) low = mid + 1;
  	}
  	// 和普通二分查找不同，从原来的-1改成返回low
  	return low;
}
```

比如数组{1, 2, 3 ,3, 3, 3, 4, 5}中要查找3的个数。我们在数组中查找2.5，得到2.5在数组中的排名是2！同样在数组中查找3.5，得到3.5在数组中的排名是6。注意2.5和3.5之间夹的都是3！用这两个下标6 -2 = 4得到的刚好就是3的个数。

这是种很巧妙的方法，同样是O(lgn)的复杂度，这个方法的代码量少了很多。

```java
public int numOfK(int[] array, int k) {
  	if (array == null) return 0;
  	return rank(array, k + 0.5) - rank(array, k - 0.5);
}
```

## 题目2

>   ```
>   0~n-1中缺失的数
>   一个长度为n -1的递增排序数组中的所有数字都是唯一的，并且每个数字的都在范围0~n-1之内。在范围内0~n-1内的n个数字中有且只有一个数字不在该数组中，找出这个数字
>   ```

举个简单的例子来找到规律。比如数组长度为8，那么该数组中的数字都是0-8之间的，但是缺了一个数字，比如缺了4，则该数组为{0, 1, 2, 3, 5, 6, 7,8}

可以发现，缺失数字还没出现时，始终有**该数字在数组中的下标等于该数，即array[i] == i**,但是从5开始不再有这样的关系。对于5及其之后的元素有array[i] == i+1，但是5之前的3仍然有array[3] == 3, 可以看到这是一个分界线。此时返回5所在的下标4就是我们要的答案。

如果找到这个分界线呢？数组是有序的，仍然采用二分查找。当mid处满足array[mid] == mid，说明mid处及其之前的数都没有缺失，因此可以直接在mid右边数组查找；当array[mid] != mid说明mid处之前有元素丢失，此时再判断一下mid前的一个元素是否也有元素丢失，如果没有说明mid处是第一个值和下标不相等的元素，返回下标mid就是答案；如果mid处的前一个元素也有元素丢失，就继续缩小查找范围，在mid的左边数组继续查找即可。

根据上面的描述，写出如下代码：

```java
package Chap6;

public class FindTheLossNumber {
    public int findLoss(int[] array) {
        if (array == null) return -1;
        int low = 0;
        int len = array.length;
        int high = len - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid != array[mid]) {
                if (mid == 0 || mid -1 == array[mid -1]) return mid;
                else high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if (low == len) return len;
        //  无效的输入数组，如不是递增排序，或者有的数字超出了0~n-1的范围
        return -1;
    }
}

```

注意上面还有句当mid != array[mid]时，如果mid == 0的情况，说明数组的第一个元素其下标就不等于值，很容易想到就是缺失了0，因此返回mid == 0也是正确的。这种情况也就是{1, 2, 3, 4, 5, 6,7,8} 缺少了0；

如果缺少的是0~n-1中的最后一位，即{0, 1, 2, 3, 4, 5, 6,7}, 此时缺少8，正好是数组的长度。这种情况最后会因为low = mid +1而使得low变成array.length，代码中也考虑到了，出了循环后判断了一下，此时返回array.length就是正确答案。

## 题目3

>   ```
>   数组中数值和下标相等的元素。
>   假设一个单调递增的数组里的每个元素都是整数并且是唯一的。找出数组中任意一个数值等于其下标的元素。比如在数组{-3， -1， 1， 3， 5}，数字3和它的下标相等
>   ```

又是递增数组，二分查找。

这次只需查找到array[mid] == mid就可以直接返回了，因为题目要求任意一个都行嘛。考虑array[mid] < mid的情况，由于数组有序，那么mid之前的元素同样有array[i] < i，这就是说我们不用考虑mid的左边数组，在右边数组查找即可；当array[mid] > mid时，由于数组有序，mid后面的元素都有array[i] > i, 因此不用考虑mid的右边数组，只在左边数组中查找即可。

听起来和普通的二分查找很像，实际上代码几乎一样。只是拿mid和array[mid]比较了，而不是某个要查找的k和array[mid]比较。

```java
package Chap6;

public class ValEqualsIndex {
    public int findValEqualsIndex(int[] array) {
        if (array == null) return -1;
        int low = 0;
        int high = array.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid > array[mid]) low = mid + 1;
            else if (mid < array[mid]) high = mid - 1;
            if (mid == array[mid]) return mid;
        }
        return -1;
    }
}

```

---

by @sunhaiyu

2018.1.29
