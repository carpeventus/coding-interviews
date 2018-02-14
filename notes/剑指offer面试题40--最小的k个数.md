# 剑指offer面试题40--最小的k个数

>   ```
>   输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
>   ```

## 切分法，时间复杂度O(n)

如果做了面试题39，这道题很容易想到直接使用切分法。几乎一模一样的代码。注意k的取值范围，不能比输入数组的长度还要大，也不能为非正数。

```java
public class LeastKNums {
    /**
     * 方法1：基于快排的切分
     */
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k > input.length || k <= 0) return list;
        select(input, k - 1);
        for (int i = 0; i < k; i++) {
            list.add(input[i]);
        }
        return list;
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

直接调用`select(k)`选择排名为k的元素即可，这里无需用到返回值，select方法在执行后，就完成了部分排序，是的索引k左边部分都比索引k处的值小，k右边部分的都比k处的大，我们要做的就是`[0, k-1]`范围内的元素，复制过去即可。

切分法只是部分排序，时间复杂度为O(n)

## 基于堆

我的思路是：既然要求得前k个最小的元素，而最小堆可以以O(1)的复杂度得到最小元素。堆的构造，注意一点就是了，输入数组arr[0]是有元素的，而下面的堆是按照heap[0]不存数据的原则实现，即heap[1]才是堆顶，也就是说输入数组中的元素到了堆中索引都要+1。因此在代码中可以看到很多索引-1的操作，就是说堆认为是在操作索引k处的值，实际上这个值在原数组中对应的索引是k-1.

```java
package Chap5;

import java.util.ArrayList;

public class LeastKNums {
    /**
     * 方法2：基于最小堆
     */
    public ArrayList<Integer> leastK(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k > input.length || k == 0) return list;

        int N = input.length;
        // 堆的构造
        for (int i = N / 2; i >= 1; i--) {
            sink(input, i, N);
        }

        // 和堆顶交换k次
        for (int i = 0; i < k; i++) {
            // 存入堆顶的最小元素
            list.add(input[0]);
            swapForHeap(input, 1, N--);
            sink(input, 1, N);
        }
        return list;
    }

    private void sink(int[] arr, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            // 左右子结点中选择小的
            if (j < N && greater(arr, j, j + 1)) j++;
            if (!greater(arr, k, j)) break;
            swapForHeap(arr, k, j);
            k = j;
        }
    }

    private void swapForHeap(int[] array, int i, int j) {
        int temp = array[i - 1];
        array[i - 1] = array[j - 1];
        array[j - 1] = temp;
    }

    private boolean greater(int[] arr, int i, int j) {
        return arr[i - 1] > arr[j - 1];
    }

}

```

堆一旦构造好，就不断将堆顶元素和数组最后一位元素（位于堆底）交换，同时存入list中，交换到堆底后数组长度N--，说明将这个最小值删除了，被交换到堆顶的元素需要进行“下沉”操作来恢复堆的有序状态。由于只需要最小的k位，因此这个交换k次即可。

## 基于最大堆的优先队列

有一个很简单的想法：设想有一个容器，其大小不超过k时，就不断存入元素，只要容量超过k，就剔除其中最大的元素，重复该过程，当遍历所有元素后，该容器中剩下的刚好就是最小的k个元素。

哪种容器能快速得到最大元素，可能第一想到的就是**最大堆**。

上面的两个方法还是太麻烦了，面试了哪有那么多时间让你手写一个堆和一大堆代码的切分方法。而且上述两种方法都改变了原数组。Java内置了优先队列，就是基于堆实现的，默认是最小堆，可以传入Comparator改变堆的形式。

```java
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b.compareTo(a)); // 注意参数a、b的顺序和compareTo方法中a、b的位置
// 或者直观的写法
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reversOrder());
```

基于以上思路，实现如下

```java
package Chap5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class LeastKNums {
    /**
     * 上面两个方法都改变了输入数组
     * 直接使用Java内置的优先队列
     */
    public ArrayList<Integer> getLeastK(int[] input, int k) {
        ArrayList<Integer> list = new ArrayList<>();
        if (input == null || input.length == 0 || k > input.length || k == 0) return list;

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int a : input) {
            maxHeap.offer(a);
            // 只要size大于k，不断剔除最大值，最后优先队列里只剩最小的k个
            if (maxHeap.size() > k) maxHeap.poll();
        }
        list.addAll(maxHeap);
        return list;
    }

}

```

优先队列可以以O(1)的时间删除堆顶元素，但是由于删除后还要恢复堆有序状态，而优先队列的大小不会超过k + 1，因此时间复杂度为O(lg k)。又由于输入中有n个数，故总的时间复杂度为O(nlgk)。

优先队列中的元素从来不会超过k + 1，因此即使有海量的数据不用担心，因为无需将海量数据一次性加载进内存，只需要每次读取一个值，然后剔除一个最大值，优先队列的大小将长期稳定在k。

比较一下这几个方法，切分法是最快的，但是不适合用于处理海量数据；基于优先队列的实现虽然速度满了点但是可以进行海量数据处理，看具体应用场景了。

至于最小堆的方法二嘛，自己瞎写的，虽然也是对的，但是有点复杂了。

---

by @sunhaiyu

2018.1.17
