package Chap4;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 输入一个字符串,按字典序打印出该字符串中字符的所有全排列。
 * 例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
 * <p>
 * 通用的根据输入字符串得到全排列的程序，排序是为了保证字典序
 */
public class Permutation {
    public ArrayList<String> permutation(String str) {
        ArrayList<String> list = new ArrayList<>();
        if (str == null || str.length() == 0) {
           return list;
        }

        collect(str.toCharArray(), 0, list);
        Collections.sort(list);
        return list;
    }

    private void collect(char[] chars, int begin, ArrayList<String> list) {
        if (begin == chars.length - 1) {
            // 不能存入相同的排列
            String s = String.valueOf(chars);
            if (!list.contains(s)) {
                list.add(s);
                return;
            }
        }

        for (int i = begin; i < chars.length; i++) {
            swap(chars, i, begin);
            collect(chars, begin + 1, list);
            swap(chars, i, begin);
        }

    }

    private void swap(char[] chars, int i, int j) {
        char temp = chars[j];
        chars[j] = chars[i];
        chars[i] = temp;
    }

    public static void main(String[] args) {
        Permutation a = new Permutation();
        System.out.println(a.permutation("aab"));
    }
}
