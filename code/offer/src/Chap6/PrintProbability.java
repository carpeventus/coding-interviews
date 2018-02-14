package Chap6;

public class PrintProbability {
    private int sideNum = 6;
    /**
     * 递归
     */
    public void printProbability(int n) {
        if (n < 1) return;
        int maxVal = n * sideNum;
        int[] probabilities = new int[maxVal - n + 1];
        getProbabilities(n, n, 0, probabilities);
        int total = (int) Math.pow(sideNum, n);
        for (int i = n; i <= maxVal; i++) {
            System.out.println("s=" + i + ": " + probabilities[i - n] + "/" + total);
        }
    }

    private void getProbabilities(int n, int cur, int sum, int[] p) {
        if (cur == 0) p[sum - n]++;
        else for (int i = 1; i <= sideNum; i++) {
            getProbabilities(n, cur - 1, sum + i, p);
        }
    }

    /**
     * 动态规划
     */
    public void printProbabilityDP(int n) {
        if (n < 1) return;
        int maxVal = n * sideNum;
        int[][] f = new int[n + 1][maxVal + 1];
        // 初始化f(1, i) 1 <= i <= 6, 只有一个骰子，点数和为i的情况只有一种
        for (int i = 1; i <= sideNum; i++) {
            f[1][i] = 1;
        }
        // 逐个增加骰子个数
        for (int k = 2; k <= n; k++) {
            // k个骰子可能的点数和是k~6k
            for (int sum = k; sum <= k * sideNum; sum++) {
                for (int i = 1; sum > i && i <= sideNum; i++) {
                    f[k][sum] += f[k - 1][sum - i];
                }
            }
        }

        int total = (int) Math.pow(sideNum, n);
        for (int sum = n; sum <= maxVal; sum++) {
            System.out.println("s=" + sum + ": " + f[n][sum] + "/" + total);
        }
    }

    /**
     * 更省空间的动态规划
     */
    public void printProbabilityBetterDP(int n) {
        if (n < 1) return;
        int maxVal = n * sideNum;
        int[][] f = new int[2][maxVal + 1];
        int flag = 0;
        // 初始化f(1, i) 1 <= i <= 6, 只有一个骰子，点数和为i的情况只有一种
        for (int i = 1; i <= sideNum; i++) {
            f[flag][i] = 1;
        }
        // 逐个增加骰子个数
        for (int k = 2; k <= n; k++) {
            // k个骰子可能的点数和是k~6k
            for (int sum = k; sum <= k * sideNum; sum++) {
                // 求f(k -1, s-1)~f(k-1, s-6)的情况和
                int s = 0;
                for (int i = 1; sum > i && i <= sideNum; i++) {
                    s += f[flag][sum - i];
                }
                // 重新给f(k, s)赋值
                f[1 - flag][sum] = s;
            }
            flag = 1 - flag;
        }

        int total = (int) Math.pow(sideNum, n);
        for (int sum = n; sum <= maxVal; sum++) {
            // f(k, s)也就是f[1-flag][sum], 但之后flag = 1 -flag,所以调用f[flag]才能得到f(k, s)
            System.out.println("s=" + sum + ": " + f[flag][sum] + "/" + total);
        }
    }

    public static void main(String[] args) {
        PrintProbability p = new PrintProbability();
        p.printProbability(3);
        System.out.println();
        p.printProbabilityBetterDP(3);
    }
}
