# 剑指offer面试题19--正则表达式匹配

> ```
> 请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"和"ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配
> ```

注意`.`就是一个字符，而`*`前面必须有一个字符（可以是`.`）才有意义，所以可以将`x*`看成一个整体，其中x表示任意字符。`*`在匹配时有两种情况，第二个字符是`*`或者不是。

- 第二个字符不是`*`号。这种情况很简单，第二个字符要么是`.`要么是一个具体的字符。此时如果第一个字符匹配成功了，只需将模式和文本指针都前进一位。比如`ab`和`ac`以及`ab`和`.b`，分别对应着字符一样、模式字符为`.`的情况。**第一个字符匹配失败了，直接就可以得出结论——匹配失败。**

- 第二个字符是`*`。有几种情况：

  1、`*`匹配0次，比如`a*ab`和`ab`匹配，此时需要将模式指针前移2位，文本指针保持不动；

  2、`*`匹配了1次，比如`a*b`和`ab`匹配，此时需要将模式指针前移2位，文本指针前移1位；

  3、`*`匹配了多次，比如`a*b`和`aaab`匹配，此时需要将模式保持不动，文本指针前移1位；

  **同样的比较第二个字符的前提是第一个字符已经匹配成功。**



我们将长度任意的模式和文本分解成一个或者两个字符，可以用递归地方法写出如下程序：

```java
  package Chap3;

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

```

如果匹配的最后两个指针都到达末尾，说明完全匹配上了，返回true。如果模式指针先于文本到达末尾，一定是匹配失败了，举个例子，`a*bcd`和`abcdefg`。

接下来的两个if分别是第二个字符为`*`和不为`*`的情况。

- 第二个字符为`*`。在保证第一个字符已经匹配上的情况下，`*`可以有三种匹配方式，有任一种方式匹配成功即可，所以return语句中用的是`||`。
- 第二个字符不为`*`。当第一个字符已经匹配上的情况下，直接将模式和文本的指针前移一位，即比较下一个字符。

如果不是上面的情况（比如第一个字符就匹配失败了），返回false。

---

by @sunhaiyu

2017.12.25