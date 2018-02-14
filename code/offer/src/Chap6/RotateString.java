package Chap6;

/**
 * 字符串的左旋操作是把字符串前面的若干个字符转移到字符串的尾部。
 * 比如输入字符串"abcdefg"和一个数字2，则左旋转后得到字符串"cdefgab"
 */
public class RotateString {
    /**
     * 方法1：使用StringBuilder
     */
    public String leftRotateString(String str, int n) {
        if (str == null || n < 0 || n > str.length()) return null;
        StringBuilder sb = new StringBuilder(str);
        sb.append(sb.substring(0, n));
        return sb.substring(n, sb.length());
    }

    /**
     * 三次反转：索引n将字符串分成两个子字符串，分别反转这两个子字符串，然后反转整个字符串。
     */
    public String leftRotateString2(String str, int n) {
        if (str == null || n < 0 || n > str.length()) return "";
        char[] chars = str.toCharArray();
        reverse(chars, 0, n-1);
        reverse(chars, n, str.length() - 1);
        reverse(chars, 0, str.length() - 1);
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
        RotateString rotateString = new RotateString();
        System.out.println(rotateString.leftRotateString("abcdefg", 2));
    }
}
