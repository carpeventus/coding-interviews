package Chap5;

/**
 * 请从字符串中找出一个最长的不包含重复字符的子字符串，计算该最长子字符串的长度。
 * 假设字符串中只包含'a'~'z'之间的字符，例如在字符串"arabcacfr"中，最长的不含重复字符的子字符串是"acfr"，长度为4
 */
public class LongestSubstring {
    public int findLongestSubstring(String str) {
        int curLen = 0;
        int maxLen = 0;
        // 0~25表示a~z，position[0] = index,表明a上次出现在index处
        int[] position = new int[26];
        for (int i = 0; i < 26; i++) {
            position[i] = -1;
        }

        for (int i = 0; i < str.length(); i++) {
            int preIndex = position[str.charAt(i) - 'a'];
            if (preIndex == -1 || i - preIndex > curLen) curLen++;
            else {
                if (curLen > maxLen) maxLen = curLen;
                curLen = i - preIndex;
            }
            position[str.charAt(i) - 'a'] = i;
        }
        if (curLen > maxLen) maxLen = curLen;
        return maxLen;
    }

    public static void main(String[] args) {
        LongestSubstring l = new LongestSubstring();
        System.out.println(l.findLongestSubstring("abcdefaz"));
    }
}
