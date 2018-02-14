package Chap5;

/**
 * 找出字符串中找出第一个只出现一次的字符，比如输入“abacceff",则输出'b'
 */
public class FirstAppearOnceChar {
    /**
     * 返回第一个不重复字符
     */
    public char firstNotRepeatingChar(String str) {
        if (str == null || str.length() == 0) return '\0';
        int[] count = new int[256];

        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i)]++;
        }
        for (int i = 0; i < str.length(); i++) {
            if (count[str.charAt(i)] == 1) return str.charAt(i);
        }
        return '\0';
    }

    /**
     * 返回第一个不重复字符在字符串中的索引
     */
    public int firstAppearOnceChar(String str) {
        if (str == null || str.length() == 0) return -1;
        int[] count = new int[256];

        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i)]++;
        }
        for (int i = 0; i < str.length(); i++) {
            if (count[str.charAt(i)] == 1) return i;
        }
        return -1;
    }

    /**
     * 删除字符串中所有的重复字符
     */
    public String deleteRepeating(String str) {
        if (str == null || str.length() == 0) return str;

        boolean[] occur = new boolean[256];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!occur[ch]) sb.append(ch);
            occur[ch] = true;
        }
        return sb.toString();
    }

    /**
     * 从第一个字符串中删除第二个字符串中出现过的所有字符
     */
    public String deleteFromAnother(String str, String another) {
        if (str == null || str.length() == 0 || another == null || another.length() == 0) return str;
        boolean[] occur = new boolean[256];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < another.length(); i++) {
            occur[another.charAt(i)] = true;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!occur[str.charAt(i)]) sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /**
     * 变位词
     */
    public boolean hasSameChar(String s1, String s2) {
        if (s1 == null || s2 ==null) return false;
        int[] count = new int[256];
        // 统计第一个字符串
        for (int i = 0; i < s1.length(); i++) {
            count[s1.charAt(i)]++;
        }
        // 第二个字符串中如果有该字符，就减去
        for (int i = 0; i < s2.length(); i++) {
            count[s2.charAt(i)]--;
        }
        // 如果是变位词，最后count数组每个位置都是0
        for (int i = 0; i < 256; i++) {
            if (count[i] != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        FirstAppearOnceChar f = new FirstAppearOnceChar();
        System.out.println(f.firstAppearOnceChar("google"));
        System.out.println(f.deleteRepeating("google"));
        System.out.println(f.deleteFromAnother("google", "aeiou"));
        System.out.println(f.hasSameChar("google", "eggloo"));
    }
}
