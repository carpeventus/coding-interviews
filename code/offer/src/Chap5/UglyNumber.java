package Chap5;

/**
 * 把只包含因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含因子7。
 * 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。
 */
public class UglyNumber {
    public int uglyNumber(int index) {
        if (index <= 0) return 0;
        int t2 = 0;
        int t3 = 0;
        int t5 = 0;
        int[] res = new int[index];
        res[0] = 1;
        for (int i = 1; i < index; i++) {
            int m2 = res[t2] * 2;
            int m3 = res[t3] * 3;
            int m5 = res[t5] * 5;
            // 三个候选中最小的就是下一个丑数
            res[i] = Math.min(m2, Math.min(m3, m5));
            // 选择某个丑数后ti * i，指针右移从丑数集合中选择下一个丑数和i相乘
            // 注意是三个连续的if，也就是三个if都有可能执行。这种情况发生在三个候选中有多个最小值，指针都要右移，不然会存入重复的丑数
            if (res[i] == m2) t2++;
            if (res[i] == m3) t3++;
            if (res[i] == m5) t5++;
        }
        return res[index - 1];
    }
}
