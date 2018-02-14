package Chap5;

import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。
 */
public class MedianInStream {

    private PriorityQueue<Integer> maxPQ = new PriorityQueue<>(Comparator.reverseOrder());
    private PriorityQueue<Integer> minPQ = new PriorityQueue<>();
    private int count;

    public void Insert(Integer num) {
        if (count == 0) {
            maxPQ.offer(num);
            // 当前数据流为奇数个时，存入最小堆中
        } else if ((count & 1) == 1) {
            // 如果要存入最小堆的元素比最大堆的最大元素小，将不能保证最小堆的最小元素大于最大堆的最大元素
            // 此时需要将最大堆的最大元素给最小堆，然后将这个元素存入最大堆中
            if (num < maxPQ.peek()) {
                minPQ.offer(maxPQ.poll());
                maxPQ.offer(num);
            } else {
                minPQ.offer(num);
            }
            // 当前数据流为偶数个时，存入最大堆
        } else if ((count & 1) == 0) {
            // 如果要存入最大堆的元素比最小堆的最小元素大，将不能保证最小堆的最小元素大于最大堆的最大元素
            // 此时需要将最小堆的最小元素给最大堆，然后将这个元素存入最小堆中
            if (num > minPQ.peek()) {
                maxPQ.offer(minPQ.poll());
                minPQ.offer(num);
            } else {
                maxPQ.offer(num);
            }
        }
        count++;
    }

    public Double GetMedian() {
        // 当数据流读个数为奇数时，最大堆的元素个数比最小堆多1,因此中位数在最大堆中
        if ((count & 1) == 1) return Double.valueOf(maxPQ.peek());
        // 当数据流个数为偶数时，最大堆和最小堆的元素个数一样，两个堆的元素都要用到
        return Double.valueOf((maxPQ.peek() + minPQ.peek())) / 2;
    }
}
