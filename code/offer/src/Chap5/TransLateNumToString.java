package Chap5;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个数字，我们按照如下的规则把它翻译成字符串
 >   0 -> a
 >   1 -> b
 >   2 -> c
 >   ...
 >   25 -> z
 >
 >   一个数字可能有多种翻译，比如12258有五种，分别是"bccfi", "bwfi","bczi","mcfi", "mzi".
     请实现一个函数，用来计算一个数字有多少种不同的翻译方法。
 */
public class TransLateNumToString {
    /**
     * 方法一：从左到右的递归，重复计算较多，但是可以保存编码的结果
     */
    public List<String> translateNum(int n) {
        List<String> list = new ArrayList<>();
        if (n < 0) return list;

        String number = String.valueOf(n);
        StringBuilder sb = new StringBuilder();
        translate(sb, number, list);
        return list;
    }

    private void translate(StringBuilder sb, String str, List<String> list) {
        if (str.equals("")) {
            list.add(sb.toString());
            return;
        }

        String s1 = str.substring(0, 1);
        char letter1 = numToLetter(s1);
        sb.append(letter1);
        translate(sb, str.substring(1), list);
        sb.deleteCharAt(sb.length() - 1);

        if (str.length() >= 2) {
            String s2 = str.substring(0, 2);
            if (check(s2)) {
                char letter2 = numToLetter(s2);
                sb.append(letter2);
                translate(sb, str.substring(2), list);
                sb.deleteCharAt(sb.length() - 1);
            }
        }

    }

    /**
     * 当一次翻译两位数时，检查是否范围在10-25之间
     */
    private boolean check(String num) {
        int val = Integer.parseInt(num);
        return val >= 10 && val <= 25;
    }

    /**
     * 数字 -> 字母的映射，a的ASCII码是97，所以0-25的数字加上97就得到了题目中的映射
     */
    private char numToLetter(String num) {
        return (char) (Integer.parseInt(num) + 97);
    }

    /**
     * 方法二：从右到左的循环，效率高，单纯的计数
     */
    public int getTranslateCount(int n) {
        if (n < 0) return 0;
        return count(String.valueOf(n));
    }

    private int count(String num) {
        int len = num.length();
        int[] counts = new int[len];
        counts[len - 1] = 1;

        for (int i = len - 2; i >= 0; i--) {
            int high = num.charAt(i) - '0';
            int low = num.charAt(i + 1) - '0';
            int combineNum = high * 10 + low;
            if (combineNum >= 10 && combineNum <= 25) {
                // f(i) = f(i+1) +f(i+2),if中因为f(i+2)不存在，但是该值肯定为1
                if (i == len -2) counts[i] = counts[i+1] + 1;
                else counts[i] = counts[i+1]+counts[i+2];
            } else {
                // f(i) = f(i+1)
                counts[i] = counts[i+1];
            }

        }
        // 从第一个数字开始的不同翻译数目
        return counts[0];
    }

    public static void main(String[] args) {
        TransLateNumToString t = new TransLateNumToString();
        System.out.println(t.translateNum(187534121).size());
        System.out.println(t.getTranslateCount(187534121));
    }
}
