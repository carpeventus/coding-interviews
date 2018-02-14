package Chap6;

/**
 * 求1+2+3+...+n,
 * 要求不能使用乘除法、for、while、if、else、switch、case等关键词以及三元运算符等。
 */
public class Sum {
    /**
     * 递归，因为要求不能用if，那就用逻辑与的短路特性
     */
    public int Sum_Solution(int n) {
        int sum = n;
        boolean b = n > 0 && (sum += Sum_Solution(n - 1)) > 0;
        return sum;
    }

    /**
     * n(n+1)/2 == (n^2 + n) >> 1;
     */
    public int sum2(int n) {
        return  ((int)Math.pow(n, 2) + n) >> 1;
    }

    public static void main(String[] args) {
        Sum sum = new Sum();
        System.out.println(sum.sum2(100));
    }
}
