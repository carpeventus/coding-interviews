package Chap5;

/**
 * 数字以0123456789101112131415....的格式序列化得到一个字符序列中，在这个序列中，第5位（从0开始计数）是5，第13位是1，第19位是4，等等。
 * 请写一个函数，求任意第n位对应的数字
 */
public class ADigitInNumberSeq {
    /**
     * 方法1：逐个列举
     */
    public int numAtSeq(int index) {
        if (index < 0) return -1;

        int i = 0;
        int sum = 0;
        while (true) {
            sum += countDigits(i);
            // 一定不要包含=
            if (sum > index) break;
            i++;
        }
        // sum - n是超出的部分，减去1是因为下标从0开始
        return digitAt(i, sum - index - 1);
    }

    /**
     * 返回某数的第d位, 第0位是个位，第1位是十位，以此类推
     */
    private int digitAt(int value, int d) {
        return (value / (int) Math.pow(10, d)) % 10;
    }

    /**
     * 计算某数有多少位
     */
    private int countDigits(int num) {
        if (num == 0) return 1;

        int count = 0;
        while (num != 0) {
            num /= 10;
            count++;
        }
        return count;
    }

    /*****************************************
     * 方法2
     */
    public int numAtSeq2(int index) {
        if (index < 0) return -1;
        // 位数，digits = 1表示一位数，0-9区间；digits = 2表示两位数，10-99区间...
        int digits = 1;
        while (true) {
            int numbers = numOfRange(digits);
            if (index < numbers * digits) {
                return digitAt2(index, digits);
            }
            index -= numbers * digits;
            digits++;
        }
    }

    /**
     * 根据位数得到范围内的个数，比如1位，0~9共10个
     * 2位，10~99共90个
     * 3位，100~999共900个
     * ...
     */
    private int numOfRange(int n) {
        if (n == 1) return 10;

        return (int) (9 * Math.pow(10, n - 1));
    }

    /**
     * n位数范围内的的第一个数，比如1位数，0~9，第一个是0
     * 2位数，10~99，第一个数是10
     * 3位数，100~199，第一个数是100
     */
    private int beginNumber(int n) {
        if (n == 1) return 0;
        return (int) Math.pow(10, n - 1);
    }

    private int digitAt2(int seqIndex, int digits) {
        int number = beginNumber(digits) + seqIndex / digits;
        return digitAt(number, digits - seqIndex % digits - 1);
    }

    public static void main(String[] args) {
        ADigitInNumberSeq a = new ADigitInNumberSeq();
        System.out.println(a.numAtSeq(1001));
        System.out.println(a.numAtSeq2(1001));
    }
}
