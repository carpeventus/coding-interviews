package Chap6;

/**
 * 数组中唯一出现一次的数字。
 * 在一个数组中除了一个数字只出现一次之外，其他数字都出现了三次，请找出那个只出现一次的数字
 */
public class AppearOnlyOnce {
    public int findNumberAppearOnlyOnce(int[] numbers) {
        if (numbers == null || numbers.length == 0) throw new RuntimeException("无效输入");
        int[] bitSum = new int[32];
        int bitMask = 1;
        // 注意先对最低位做位运算，bitSum[0]存的最高位，bitSum[31]存的最低位
        for (int i = 31; i >= 0; i--) {
            for (int number : numbers) {
                int bit = number & bitMask;
                if (bit != 0) bitSum[i] += 1;
            }
            bitMask = bitMask << 1;
        }
        int result = 0;
        // 转换成十进制时，从最高位开始，从由左至右第一个不为0的位数开始
        for (int i = 0; i < 32; i++) {
            result = result << 1;
            // bitSum[i] % 3为0说明只出现一次的那个数第i为也是0；反之亦反
            result += bitSum[i] % 3;
        }
        return result;
    }


    public static void main(String[] args) {
        int[] a = {1, 1, 1, 4};
        AppearOnlyOnce appearOnlyOnce = new AppearOnlyOnce();
        System.out.println(appearOnlyOnce.findNumberAppearOnlyOnce(a));
    }
}
