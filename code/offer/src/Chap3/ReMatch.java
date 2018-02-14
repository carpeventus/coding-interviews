package Chap3;

/**
 * 请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。
 * 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配
 */
public class ReMatch {
    public boolean match(char[] str, char[] pattern)
    {
        if (str == null || pattern == null) {
            return false;
        }

        return matchRecur(str, pattern, 0, 0);
    }

    private boolean matchRecur(char[] str, char[] pattern, int s, int p) {
        if (s == str.length && p == pattern.length) {
            return true;
        }
        // 模式串比文本串先到末尾，肯定没有匹配成功
        if (p == pattern.length && s < str.length) {
            return false;
        }
        // 两种情况，1、模式和文本都没有到结尾
        // 2、或者文本到了结尾而文本还没有到结尾，此时肯定会调用else分支
        // 第二个字符是*
        if (p < pattern.length-1 && pattern[p + 1] == '*') {
            if ((s < str.length && str[s] == pattern[p]) || (pattern[p]== '.' && s < str.length))
                return matchRecur(str, pattern, s, p + 2) ||
                        matchRecur(str, pattern, s + 1, p+2) ||
                        matchRecur(str,pattern,s + 1, p);
            else
                return matchRecur(str, pattern, s, p + 2);
        }
        // 第二个字符不是*
        if ((s < str.length && str[s] == pattern[p]) || (pattern[p]== '.' && s < str.length)) {
            return matchRecur(str, pattern, s + 1, p + 1);
        }
        return false;
    }
}
