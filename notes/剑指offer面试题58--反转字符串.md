# 剑指offer面试题58--反转字符串

## 题目1

>   ```
>   输入一个英文句子，翻转句子中单词的顺序，但单词内的顺序不变。为简单起见，标点符号和普通字母一样处理。
>   例如输入"I am a student."则输出"student. a am I"
>   ```

### 方法1：以空格为分隔符split

我直观的解法，以空格为分隔符使用split，这就将字符串变成了单词数组。然后逆序遍历字单词数组，在除了最后一个单词外的其他每个单词后添加一个空格即可。

这道题需要考虑字符串中的字符全是空格的特殊情况，这种情况下没有任何单词，所以直接返回空字符串即可。

```java
package Chap6;

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
}

```

因为题目说了是英文句子，所以假定给出的字符串各个单词之间真的只有一个空格。如果不巧单词之间有不止一个空格，因为我们是无脑只添加一个空格，所以最后输出的结果和原字符串比消除了多余的空格......

比如输入“a     man”, 按理说应该返回“man     a”，但是上面的程序会返回“man a”....不过既然AC了，说明给出的测试用例真的是标准的英文句子...

### 方法2：先整体反转再局部反转

比如字符串"I am a student."，整个翻转后得到".tneduts a ma I"，然后翻转每个单词即可，将单词分隔开的依然是空格。该字符串有4个单词，做四次局部反转后就得到了结果"student. a am I"。

关键是要如何反转字符串的局部。可以设置两个指针，一个low指向局部字符串的头部，一个high指向局部字符串的尾部，一开始low和high都位于字符串的头部。当**low指向的不是空格且high指向的字符是空格**，此时就可以开始反转[low,high]内的字符串了....然后low和high继续向右移动，直到四个单词都被翻转。

```java
package Chap6;

public class ReverseWords {
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
}

```

注意到最后一个单词后面没有空格，但仍然需要翻转，所以**翻转一个单词的条件是单词后面有空格或者单词的字符到达整个字符串的末尾。**代码中先是--high使得子字符串中不含空格，之后后将++high赋值给low，那么下次判断low处必然是空格，此时`low == high == ' '`，因此两个指针都要右移一位，以跳过空格。

如果low和high处的字符都不是空格，说明还没有到单词的末尾，因此直接将high右移即可。

这种方法对于单词之间有多个空格的情况也能正确处理。

## 题目2

>   ```
>   字符串的左旋操作是把字符串前面的若干个字符转移到字符串的尾部。
>   比如输入字符串"abcdefg"和一个数字2，则左旋转后得到字符串"cdefgab"
>   ```

### 方法1：使用StringBuilder

我一下就想到用StringBuilder，先把要旋转的子字符串append字符串后，然后取下substring就好了...

```java
package Chap6;

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
}

```

几行就搞定了，不过使用了写额外空间。

这应该不是本题想要的解答...

### 方法2：三次翻转——先局部翻转再整体翻转

举个简单的例子"hello world"，按照上题的要求，会得到"world hello". 而在此题中，假如要求将前五个字符左旋转，会得到" worldhello"(注意w前哟一个空格)，是不是接近了。

所以本题可以延续上题的思路，不过这次先局部翻转再整体反转。如字符串"abcdefg"要求左旋转前两个字符，先反转ab和cdefg得到bagfedc，然后反转这个字符串得到cdefgab即是正确答案。

```java
package Chap6;

public class RotateString {
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
}

```

当然也可以先整体翻转再局部翻转，不过要注意控制两个局部字符串的区间。

---

by @sunhaiyu

2018.1.31
