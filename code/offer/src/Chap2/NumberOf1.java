package Chap2;

/**
 * 输入一个整数，输出该数二进制表示中1的个数。其中负数用补码表示。
 */
public class NumberOf1 {
    /**
     * 减1后的值比原值相与，就是将二进制表示中最后一个1变成0
     */
    public int numberOf1(int n) {
        int count = 0;

        while (n != 0) {
            n = (n-1) & n;
            count++;
        }
        return count;
    }

    /**
     * 左移版本
     */
    public int numberOf1_1(int n) {
        int count = 0;
        int flag = 1;
        while (flag != 0) {
            if ((n & flag)!= 0) {
                count++;
            }
            flag = flag << 1;
        }
        return count;
    }

    /**
     * Java中的无符号右移 >>> 不论正负数都是补0
     */
    public int numberOf1_2(int n) {
        int count = 0;

        while (n != 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n = n >>> 1;
        }
        return count;
    }
    /**
     * 判断一个整数是不是2的正整数次方。
     */
    public boolean isExponOf2(int n) {
        return numberOf1(n) == 1;
    }

    /**
     * 输入两个整数m和n，计算需要改变m的二进制表示中的几位才能得到n。
     * 比如10的二进制是1010，13的二进制是1101，则需要改变3次。
     * @param m 一个整数
     * @param n 另一个整数
     * @return 需要改变的位数
     */
    public int bitNumNeedsToBeChanged(int m, int n) {
        return numberOf1(m ^ n);
    }

    public static void main(String[] args) {
        NumberOf1 a = new NumberOf1();
        System.out.println(a.isExponOf2(8));
        System.out.println(a.bitNumNeedsToBeChanged(10, 13));
    }
}
