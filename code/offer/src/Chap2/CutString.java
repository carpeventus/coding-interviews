package Chap2;

/**
 * 给你一根长度为n的绳子，把绳子剪成m段（m、n都是整数且m > 1, n > 1）,m段绳子的长度依然是整数，求m段绳子的长度乘积最大为多少？
 * 比如绳子长度为8，我们可以分成2段、3段、4段...8段，只有分成3段且长度分别是2、3、3时能得到最大乘积18
 */
public class CutString {
    /**
     * 动态规划版本
     * f(n)定义为将长度为n的绳子分成若干段后的各段长度的最大乘积（最优解），在剪第一刀时有n-1种剪法，可选择在0 < i < n处下刀
     * 在i处下刀，分成长度为i的左半绳子和长度为n-i的右半绳子，对于这两根绳子，定义最优解为f(i)和f(n-i)，于是f(n) = max(f(i) * f(n-i))，即求出各种相乘可能中的最大值就是f(n)的最优解
     * 就这样从上到下的分下去，但是问题的解决从下到上。即先求出f(2)、f(3)的最优解，然后根据这两个值求出f(4)、f(5)...直到f(n)
     * f(2) = 1，因为只能分成两半
     * f(3) = 2，因为分成两段2*1 大于分成三段的1*1*1
     * ...
     */
    public static int maxProductAfterCutting(int length) {
        // 长度为1时不满足题意，返回0
        if (length < 2) {
            return 0;
        }
        // f(2)
        if (length == 2) {
            return 1;
        }
        // f(3)
        if (length == 3) {
            return 2;
        }
        // 加1是因为需要访问到products[length]
        int[] products = new int[length + 1];
        // 下面这三个存的不是f(1)、f(2)、f(3)，只是单纯的长度而已
        products[1] = 1;
        products[2] = 2;
        products[3] = 3;
        // 从products[4]到products[length]放的是f(4)~f(n)的值
        for (int i = 4; i <= length; i++) {
            int max = 0;

            // 对所有相乘情况进行遍历求出f(i)的最优解
            for (int j = 1; j <= i / 2; j++) {
                int product = products[j] * products[i - j];
                if (product > max) {
                    max = product;
                }
            }
            // 得到f(i)的最优解
            products[i] = max;
        }
        // 返回f(n)
        return products[length];
    }

    /**
     * 贪婪法，不断分出长度为3的绳子，如果最后只剩下长度为1的绳子，退一步，将得到长度为4的绳子，然后将这长度为4的绳子分成2*2(这样分是因为2*2大于原来的3*1)
     * 因此n = 4时不能分出长度为3的绳子,而n = 2，n = 3可直接返回
     * 当n >=5时候，满足n >=5这个不等式的有2*(n-2) > n以及3*(n-3) > n
     * 注意到2+n-2 = 3+n-3 = n,也就是说分出的两个相乘的数要满足和为n，且同样的n，3*(n-3)的值更大，这就是为什么要不断分出长度为3的绳子的原因
     */
    public static int maxProductAfterCutting2(int length) {
        // 长度为1时不满足题意，返回0
        if (length < 2) {
            return 0;
        }
        // f(2)
        if (length == 2) {
            return 1;
        }
        // f(3)
        if (length == 3) {
            return 2;
        }
        // 	统计能分出多少段长度为3的绳子
        int timesOf3 = length / 3;
        // 如果最有只剩下长度为1的绳子，需要退一步，得到长度为4的绳子，重新分成2*2的
        if (length - timesOf3 * 3 == 1) {
            timesOf3--;
        }
        // 到这步length - timesOf3 * 3的值只可能是0,2,4，所以timesOf2只可能是0, 1, 2
        int timesOf2 = (length - timesOf3 * 3) / 2;
        return (int) Math.pow(3, timesOf3) * (int) Math.pow(2, timesOf2);

    }
    public static void main(String[] args) {
        System.out.println(maxProductAfterCutting(8));
        System.out.println(maxProductAfterCutting2(8));
    }
}
