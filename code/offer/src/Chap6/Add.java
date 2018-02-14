package Chap6;

/**
 * 写一个函数，求两个整数之和，
 * 要求在函数体内不得使用"+"、"-"、"x"、"÷"四则运算符号。
 */
public class Add {
    public int add(int num1, int num2) {
        // num2 != 0 说明还有进位需要相加
        while (num2 != 0) {
            int sum = num1 ^ num2;
            int carry = (num1 & num2) << 1;
            num1 = sum;
            num2 = carry;
        }
        return num1;
    }
}
