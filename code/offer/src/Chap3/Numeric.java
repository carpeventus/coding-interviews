package Chap3;

/**
 * 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
 * 例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
 * 但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
 */
public class Numeric {
    public boolean isNumeric(char[] str) {
        if (str == null) {
            return false;
        }
        return new String(str).matches("[+-]?[0-9]*(\\.[0-9]*)?([eE][+-]?[0-9]+)?");
    }

    public boolean isNumeric_2(char[] str) {
        // null或者空字符串不是数字
        if (str == null || str.length == 0) {
            return false;
        }
        // 长度只为1，必须是数0~9之间
        if (str.length == 1) {
            return str[0] >= '0' && str[0] <= '9';
        }

        boolean hasDot = false;
        boolean hasE = false;
        boolean hasSign = false;

        for (int i = 0; i < str.length; i++) {
            if (str[i] == '+' || str[i] == '-') {
                // 第二次出现正负号,前一个字符必须是e或者E
                if (hasSign && str[i - 1] != 'e' && str[i - 1] != 'E') return false;
                // 第一次出现正负号且不在开头，前一个字符也必须是e或者E
                if (!hasSign && i > 0 && str[i - 1] != 'e' && str[i - 1] != 'E') return false;
                hasSign = true;
            } else if (str[i] == '.') {
                // 只能出现一次'.'，e和E之后不可出现'.'
                if (hasDot || hasE) return false;
                hasDot = true;
            } else if (str[i] == 'e' || str[i] == 'E') {
                // e或E后必须有数字
                if (i == str.length -1) return false;
                // 只能有一个e或者E
                if (hasE) return false;
                hasE = true;
                // 最后判断如果是 +-eE.之外的字符，不匹配
            } else if (str[i] < '0' || str[i] > '9') {
                return false;
            }
        }
        return true;
    }

}
