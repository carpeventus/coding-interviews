package Chap6;

/**
 * 输入一个英文句子，翻转句子中单词的顺序，但单词内的顺序不变。为简单起见，标点符号和普通字母一样处理。
 * 例如输入"I am a student."则输出"student. a am I"
 */
public class ReverseWords {
    /**
     * 方法1：使用split,不能处理单词之间有多个空格的情况
     */
    public String ReverseSentence(String str) {
        if (str == null || str.trim().equals("")) return str;

        String[] words = str.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i > 0) sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 方法2：先整体反转，再局部反转
     */
    public String reverseWords(String str) {
        if (str == null) return null;
        char[] chars = str.toCharArray();
        int len = chars.length;
        reverse(chars, 0, len -1);
        int low = 0;
        int high = 0;
        while (low < len) {
            if (chars[low] == ' ') {
                low++;
                high++;
                // chars[low]不为空格
            } else if (high == len || chars[high] == ' ') {
                reverse(chars, low, --high);
                low = ++high;
                // chars[low]和chars[high]都不为空格
            } else high++;
        }
        return new String(chars);
    }

    private void reverse(char[] chars, int low, int high) {
        while (low < high) {
            char c = chars[high];
            chars[high] = chars[low];
            chars[low] = c;
            low++;
            high--;
        }
    }

    public static void main(String[] args) {
        ReverseWords reverseWords = new ReverseWords();
        System.out.println(reverseWords.ReverseSentence("b    a"));
        System.out.println(reverseWords.reverseWords("b     a"));
    }
}
