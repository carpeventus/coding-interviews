package Chap5;

/**
 * 输入一个整数n，求1~n这n个整数的十进制表示中1出现的次数，
 * 例如输入12, 1~12中出现1的有1、10、11、12共5次
 */
public class NumOf1 {
    /**
     * 方法1，计算每个数字中1的个数，复杂度O(nlgn)
     */
    public int NumberOf1From1To(int n) {
        // 正负数不影响1的个数，统一变成非负数
        if (n < 0) n = Math.abs(n);

        int count = 0;
        // 循环求n个数字，共O(nlgn)的时间
        for (int i = 1; i <= n; i++) {
            count += numOf1(i);
        }
        return count;
    }

    /**
     * O(lgn)的复杂度求一个数中含有1的数量
     */
    private int numOf1(int n) {
        int count = 0;
        while (n != 0) {
            if (n % 10 == 1) count++;
            n = n / 10;
        }
        return count;
    }

    /**
     * 方法2：使用StringBuilder将所有数字拼接，无脑数数
     */
    public int numOf1Between1AndN(int n) {
        // 正负数不影响1的个数，统一变成非负数
        if (n < 0) n = Math.abs(n);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(i);
        }

        int count = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '1') {
                count++;
            }
        }
        return count;
    }

    /**
     * 方法三：神一样的方法
     */
    public int numberOf1(int n) {
        int ones = 0;
        for (long m = 1; m <= n; m *= 10) {
            long a = n / m;
            long b = n % m;
            if (a % 10 == 0) ones += a / 10 * m;
            else if (a % 10 == 1) ones += (a / 10 * m) + (b + 1);
            else ones += (a / 10 + 1) * m;
        }
        return ones;
    }

}
