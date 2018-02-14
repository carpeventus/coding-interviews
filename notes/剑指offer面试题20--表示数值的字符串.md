# 剑指offer面试题20--表示数值的字符串

> ```
> 请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
> ```

## 正则表达式

这道题有点没意思，就是考虑各种情况，实际上可能还是考虑得不全。第一想到就是直接使用正则表达式。对正则不熟写起来可能要花些时间。

```java
public boolean isNumeric(char[] str) {
  	if (str == null) {
    	return false;
  	}
  	return new String(str).matches("[+-]?[0-9]*(\\.[0-9]*)?([eE][+-]?[0-9]+)?");
}
```

首先第1位可是是+-号中的任意一个，也可以没有；然后是整数部分0~9中任意多位，也可以没有整数部分；然后是小数部分，也是可有可无的，所以用`?`表示小数部分只能出现0次或者1次。e或者E部分也是只能出现0次或者1次，但是一旦出现e或者E，后面必须有整数，所以是`[0-9]+`，表示这个整数至少是一位。

## 考虑多种情况，自己实现

如果不使用字符串自带的`matches`方法呢？

- 字符串长度为1，这个字符必须是0~9中的其中一个
- 第二次出现正负号，该正负号的前一个字符必须是e或者E，比如`-5E-3`
- 第一次出现正负号，且不在开头出现，该正负号的前一个字符必须是e或者E，比如`5E-3`
- 小数点只能出现一次，且不能出现在e或者E的后面
- e或者E只能出现一次，e和E的后一个字符必须是整数
- 如果字符不是`+-eE.`，那么它必须是0~9中任意一个；否则不匹配。

在遍历完所有字符后，如果上面的条件都满足了，就返回true表示这是一个数字。

```java
package Chap3;

public class Numeric {
    public boolean isNumeric_2(char[] str) {
        // null或者空字符串不是数字
        if (str == null || str.length == 0) {
            return false;
        }
        // 长度只为1，必须是数0~9之间
        if (str.length == 1) {
            return str[0] >= '0' && str[0] <= '9';
        }

        boolean hasDot = false;
        boolean hasE = false;
        boolean hasSign = false;

        for (int i = 0; i < str.length; i++) {
            if (str[i] == '+' || str[i] == '-') {
                // 第二次出现正负号,前一个字符必须是e或者E
                if (hasSign && str[i - 1] != 'e' && str[i - 1] != 'E') return false;
                // 第一次出现正负号且不在开头，前一个字符也必须是e或者E
                if (!hasSign && i > 0 && str[i - 1] != 'e' && str[i - 1] != 'E') return false;
                hasSign = true;
            } else if (str[i] == '.') {
                // 只能出现一次'.'，e和E之后不可出现'.'
                if (hasDot || hasE) return false;
                hasDot = true;
            } else if (str[i] == 'e' || str[i] == 'E') {
                // e或E后必须有数字
                if (i == str.length -1) return false;
                // 只能有一个e或者E
                if (hasE) return false;
                hasE = true;
                // 最后判断如果是 +-eE.之外的字符，不匹配
            } else if (str[i] < '0' || str[i] > '9') {
                return false;
            }
        }
        return true;
    }

}

```

还是想说真麻烦，可能还有情况没有考虑到的...

---

by @sunhaiyu

2017.12.25
