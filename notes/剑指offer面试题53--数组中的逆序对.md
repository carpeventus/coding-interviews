# 剑指offer面试题53--数组中的逆序对

>   ```
>   在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组,求出这个数组中的逆序对的总数.
>   ```

暴力法很直观，拿第一个数和之后的每个数比较，然后拿第二个数和之后的每个数比较.....需要两个for循环可得到结果，时间复杂度为$O(n^2)$

用数组的归并过程来分析这道题，归并排序就是自上而下地将数组分割成左右两半子数组，然后递归地将子数组不断分割下去，最后子数组的大小为1，对于大小为1的子数组没有排序和归并的必要，因此递归到此结束；之后自下而上地对这若干个子数组两两归并、四四归并......每次归并后左右子数组都分别有序，最后再将整个数组归并，因而整个数组也有序了。

现在分析一个简单的例子，对于数组{7, 5, 6, 4}，先分成了两个子数组，左子数组{7, 5}和右子数组{6, 4}，进而分成左子数组{7}和右子数组{5}....7大于5，所以这是一个逆序对，同样6大于4也是一个逆序对。现在得到两个逆序对了。然后开始两两归并，左子数组和右子数组现已分别排序，{5, 7}和 {4, 6}，**因为大的数在右边，所以考虑从右边开始比较**。比如7大于6，那么7肯定也大于4，所以如果左边某个数p1比右边某个数p2大了，p1无需再和p2之前的所有数进行比较，这就减少了比较次数。那么在右子数组中比7小的有多少呢？多举几个例子就能发现通用的公式：**p2及其之前的元素个数减去左子数组的长度。**要求整个数组的逆序对总数，只需将每个子数组中的逆序对个数累加即可。

理解上述分析后，其实本题就可以直接使用归并排序，只是在左子数组中的某个元素大于右子数组某个元素时，多加一步——计算逆序对个数即可。

```java
package Chap5;

public class InversePairs {
    public int inversePairs(int[] array) {
        if (array == null) return 0;
        int[] aux = new int[array.length];
        return sort(array, aux, 0, array.length - 1);
    }

    private int sort(int[] array, int[] aux, int low, int high) {
        if (high <= low) return 0;
        int mid = low + (high - low) / 2;
        int left = sort(array, aux, low, mid);
        int right = sort(array, aux, mid + 1, high);
        int merged = merge(array, aux, low, mid, high);
        return left + right + merged;
    }

    private int merge(int[] array, int[] aux, int low, int mid, int high) {
        int count = 0;
        int len = (high - low) / 2;
        int i = mid;
        int j = high;

        for (int k = low; k <= high; k++) {
            aux[k] = array[k];
        }

        for (int k = high; k >= low; k--) {
            if (i < low) array[k] = aux[j--];
            else if (j < mid + 1) array[k] = aux[i--];
            else if (aux[i] > aux[j]) {
                // 在归并排序的基础上，在这里求count
                count += j - low - len;
                array[k] = aux[i--];
            } else array[k] = aux[j--];
        }
        return count;
    }
}

```

关键就是merge方法，和传统的归并排序相比，有了返回值，在`aux[i] > aux[j]`时多了一句`count += j - low - len;`以计算逆序对的个数。**j - low得到p2及其之前元素的个数，len表示左子数组的大小，按上面的分析，这个值就是逆序对的个数。**

我们知道归并排序的时间复杂度是$O(nlgn)$，但是需要O(n)的空间，所以这是个那空间换时间的例子。

---

by @sunhaiyu

2018.1.27
