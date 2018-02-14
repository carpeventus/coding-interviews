package Chap7;

/**
 * 将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。
 * 数值为0或者字符串不是一个合法的数值则返回0
 */
public class Str2Int {
    public static boolean valid;

    public static int strToInt(String str) {
        valid = false;
        if (str == null || str.length() == 0) {
            return 0;
        }

        boolean isNegative = false;
        long number = 0;
        for (int i = 0; i < str.length(); i++) {
            // 第一位是正负号就跳过
            if (i == 0 && str.charAt(i) == '+' || str.charAt(i) == '-') {
                if (str.charAt(i) == '-') {
                    isNegative = true;
                }
                if (str.length() == 1) {
                    return 0;
                }
                continue;
            }
            // 中间有任何字符不是数字直接返回
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return 0;
            }

            int flag = isNegative ? -1 : 1;
            // '4'的ASCII码就比'0'的ASCII码大4，所以可以减去'0'的ASCII码将字符转数字
            number = number * 10 + flag * (str.charAt(i) - '0');
            if (!isNegative && number > Integer.MAX_VALUE || isNegative && number < Integer.MIN_VALUE) {
                return 0;
            }
        }

        valid = true;
        return (int)number;
    }

    public static void main(String[] args) {
        System.out.println(strToInt("12"));
        System.out.println(strToInt("-12"));
        System.out.println(strToInt("+12"));
        System.out.println(strToInt("+")+ " "+ Str2Int.valid);
        System.out.println(strToInt("0")+ " "+ Str2Int.valid);
        System.out.println(strToInt("12345678901112")+ " "+ Str2Int.valid);
    }
}
