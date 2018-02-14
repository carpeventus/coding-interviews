package Chap6;

/**
 * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
 * 要求时间复杂度为O(n)，空间复杂度为O(1).
 */
public class FindNumsAppearOnce {
    //num1,num2分别为长度为1的数组。传出参数
    //将num1[0],num2[0]设置为返回结果
    public void findNumsAppearOnce(int[] array, int num1[], int num2[]) {
        if (array == null || array.length < 2) return;
        int res = 0;
        // 这步得到两个只出现一次的数的异或值
        for (int i = 0; i < array.length; i++) {
            res ^= array[i];
        }
        // res肯定不为0，那么res必然有某一位是1，找到第一个1的索引，假设为n;
        // 第n位的异或值为1，也说明了这两个数的第n位不相同
        int indexOfFirstOne = firstBitOfOne(res);
        // 以第n位是不是1为标准，将数组拆分成两个
        // 相同数字一定会被分到同一个子数组，因为相同的数字第n位也是相同的；只出现一次的那两个数字肯定不会分到一个数组中，因为他们的第n位异或值为1，说明他们第n位不相同
        for (int i = 0; i < array.length; i++) {
            if (isBitOfOne(array[i], indexOfFirstOne)) num1[0] ^= array[i];
            else num2[0] ^= array[i];
        }
    }

    /**
     * 找到从右往左数的第一个1的索引，如0110的第一个1索引为1
     */
    private int firstBitOfOne(int val) {
        int index = 0;
        while (val != 0) {
            if ((val & 1) == 1) return index;
            val = val >> 1;
            index++;
        }
        return -1;
    }

    /**
     * 判断从右往左数的第index位是不是1
     */
    private boolean isBitOfOne(int val, int index) {
        val = val >> index;
        return (val & 1) == 1;
    }
}
