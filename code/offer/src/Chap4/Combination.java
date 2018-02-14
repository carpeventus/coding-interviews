package Chap4;

import java.util.*;

/**
 * 求字符的所有排列,允许组合中有重复元素
 */
public class Combination {
    /**
     * 其实就是求C(n, m) 其中n == str.length; m == num
     *
     * @param str 字符序列
     * @param num 选几个字符进行组合
     * @return C(n, m)的集合
     */
    public List<String> combinationAccordingToNum(String str, int num) {
        List<String> list = new ArrayList<>();
        if (str == null || str.length() == 0 || num > str.length()) {
            return list;
        }
        StringBuilder sb = new StringBuilder();

        collect(str, sb, num, list);
        return list;
    }

    /**
     * 求所有组合情况
     */
    public List<String> combination(String str) {
        List<String> list = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return list;
        }
        StringBuilder sb = new StringBuilder();

        // 收集字符个数为i的组合
        for (int i = 1; i <= str.length(); i++) {
            collect(str, sb, i, list);
        }
        return list;
    }

    private void collect(String str, StringBuilder sb, int number, List<String> list) {
        // 两个if顺序不可交换，否则C(n, n)不会存入到list中：即collect("", sb, 0)时，要先判断num==0存入后，再判断str.length ==0决定不再递归
        if (number == 0) {
            if (!list.contains(sb.toString()))
                // 字符已经去重过，无需判断 !list.contains
                list.add(sb.toString());
            return;
        }

        // 当str为""时候直接返回，不然下一句charAt(0)就会越界
        if (str.length() == 0) {
            return;
        }

        // 公式C(n, m) = C(n-1, m-1)+ C(n-1, m)
        // 第一个字符是组合中的第一个字符，在剩下的n-1个字符中选m-1个字符
        sb.append(str.charAt(0)); // 第一个字符选中
        collect(str.substring(1), sb, number - 1, list);
        sb.deleteCharAt(sb.length() - 1); // 取消选中第一个字符
        // 第一个字符不是组合中的第一个字符，在剩下的n-1个字符中选m个字符
        collect(str.substring(1), sb, number, list);
    }

    public static void main(String[] args) {
        Combination c = new Combination();
        System.out.println(c.combination("abcca"));
        System.out.println(c.combination("abc"));
        System.out.println(c.combinationAccordingToNum("aabbc", 2));
    }
}
