package Chap2;

/**
 * 现在要求输入一个整数n，请你输出斐波那契数列的第n项。
 */
public class Fibonacci {
    /**
     * 推荐迭代法
     */
    public int fib(int n) {
        if (n <= 0) {
            return 0;
        }

        int a = 0;
        int b = 1;
        while (n > 0) {
            b = a + b;
            a = b - a; // a + b -a -> a = b也就是原来的b赋值给a
            n--;
        }
        return a;
    }

    /**
     * 递归不推荐
     */
    public int fib2(int n) {
        if (n <= 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        return fib2(n-1) +fib(n-2);
    }
}
