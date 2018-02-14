# 剑指offer面试题50--第一个只出现一次的字符

>   ```
>   找出字符串中找出第一个只出现一次的字符，比如输入“abacceff",则输出'b'
>   ```

要想知道某个字符是不是只出现了一次，必须遍历字符串的每个字符。**因此可以先遍历一次，统计每个字符出现次数。再遍历一次，遇到某个字符出现字符为1就立即返回。**统计每个字符出现次数，可以用哈希表，不过如果输入中都是ASCII码，那么使用0-255表示即可。这样使用一个`int[] count = new int[256]`就能代替哈希表了，以`count[someChar] = times`这种方式表示某个字符出现的次数。比如‘a’的ASCII码是97，那么count[97]就表示了字符'a'的出现次数，以此类推。

```java
package Chap5;

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
}

```

上面两个方法，一个是返回第一个只出现一次的字符，一个返回第一个只出现一个的字符的索引，思路都一样。根据`count[someChar]`获取某个字符的出现次数时间复杂度为O(1),对于长度为n的字符串，总的复杂度为O(n).

不过如果输入中含有特殊符号或者中文等，256位的ASCII表就不够用了，需要上Unicode了，总之看题目要求吧，要想通用就哈希表。

## 相关题目

### 扩展一

>   ```
>   定义一个函数，输入两个字符串，从第一个字符串中删除在第二个字符串中出现过的所有字符。比如第一个字符串"google"，第二个字符串为"aeiou"，删除后得到"ggl".
>   ```

使用一个`boolean occur[] = new int[256]`布尔型数组，对于第二个字符串中的每个字符，标记为true表示出现过。遍历第一个字符串，判断每个字符在occur中是不是fale，为false说明该字符没有在第二个字符串中出现过，保留。

```java
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
```

### 扩展二

>   ```
>   定义一个函数，删除一个字符串中所有重复出现的字符，比如输入"google"返回"gole"
>   ```

使用一个`boolean occur[] = new int[256]`布尔型数组，记录某个字符是否出现过。刚开始都初始化false，每添加一个字符就标记为true，这样下次遇到重复字符就不会再添加了。

```java
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
```

### 扩展三

>   ```
>   变位词，如果两个单词含有相同的字母且每个字母出现的次数还一样，那么这两个单词互为变位词。定义一个函数判断两个字符串是不是互为变位词。
>   ```

两个字符串含有相同的字母、每个字母出现的次数一样，先统计第一个字符串每个字符出现的次数，然后遍历第二个字符串，对于每个出现的字符，将统计表中相应字符的出现次数减1，**如果啷个字符串是变位词，那么遍历结束后，统计表中每个字符出现的字符都是0**。

```java
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
```

## 题目二

>   ```
>   字符流中第一个只出现一次的字符。
>   这次字符串是动态变化的了，比如现在只从字符流中读取了两个字符为"go"那么字符流中第一个只出现一次的字符是'g'，等到从字符流中读取了前6个字符"google"时，第一个只出现一次的字符变成了'l'.
>   ```

使用一个insert函数模拟从字符流中读到一个字符。这次统计表`int[] occur = new int[256]`记录的是字符出现的索引.

-   如果某个字符出现过，那么`occur[someChar] >= 0`;
-   对于没有出现过的字符，令`occur[someChar] = -1`;
-   如果某个字符第二次出现，令`occur[someChar] = -2`。

要获得当前字符串中第一个只出现一次的，只需从所有`occur[someChar] >= 0`中结果中找出出现索引最小的那个字符即可。

```java
package Chap5;

public class AppearOnceInStream {
  	// 记录某个字符出现的索引
    private int[] count;
    // 当前读取到的字符在字符串中的索引
    private int index;
    public AppearOnceInStream() {
        count =  new int[256];
        for (int i = 0; i < count.length; i++) {
            count[i] = -1;
        }
    }
	// 模拟读取字符流中的下一个字符
    public void insert(char c) {
        if (count[c] == -1) count[c] = index;
        else if (count[c] >= 0) count[c] = -2;
        index++;
    }

    public char firstAppearOnceChar() {
        int minIndex = Integer.MAX_VALUE;
        char c = '\0';
        for (int i = 0; i < count.length; i++) {
          	// 从所有count[i] >= 0的结果中找出最小的索引就是第一个只出现一次的字符
            if (count[i] >= 0 && count[i] < minIndex) {
                minIndex = count[i];
                c = (char)i;
            }
        }
        return c;
    }

    public static void main(String[] args) {
        AppearOnceInStream a = new AppearOnceInStream();
        a.insert('g');
        a.insert('o');
        System.out.println(a.firstAppearOnceChar());
        a.insert('o');
        a.insert('g');
        a.insert('l');
        a.insert('e');
        System.out.println(a.firstAppearOnceChar());
    }
}

```

---

by @sunhaiyu

2018.1.25
