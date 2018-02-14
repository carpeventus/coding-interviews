# 剑指offer面试题3--数组中的重复数字

> ```
> 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道有几个数字是重复的。也不知道每个数字重复几次。请找出数组中任意一个重复的数字。例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2或者3。
> ```

## 排序后相邻元素两两比较

我想到的方法是：先对数组排序，如果有重复元素排序后将会相邻。然后相邻元素两两比较，有相等的情况就找到了重复数字。排序一个长度为n的数组时间复杂度为`O(nlg n)`。

代码如下

```java
public boolean duplicate2(int numbers[],int length,int [] duplication) {
      if (numbers == null || length == 0){
        return false;
      }

      Arrays.sort(numbers);
      for (int i = 0;i < length - 1;i++) {
        if (numbers[i] == numbers[i + 1]) {
          duplication[0] = numbers[i];
          return true;
        }
      }
      return false;
}
```

## 利用题干信息——长度为n的数组里的所有数字都在0到n-1的范围内

上面的实现对于任意数组都是通用的。**我们要善于抓住题干的已知信息**，比如题目中明确说明了长度为n的数组里面的数字全是[0, n-1]之间的。从这句话能获得什么信息呢？如果将数组排好序，那么数组每一个位置都有`numbers[i] = i`，如果有重复元素，说明[0, n-1]中某些数字空缺了，某些数字有多个。那么必然有某个`j !=i`的`numbers[j] == numbers[i]`，如果找到满足上述条件的数字，就找到了重复数字。

- 如果值i没有在正确的位置（满足`numbers[i] == i`），通过交换两个元素，将值i放到正确的位置。这个过程可以看成是**排序**。
- 当`numbers[i] != i`，若令`numbers[i] = j`，显然`j != i`；如果此时`numbers[j] == numbers[i]`说明在与i不同的位置j找到重复元素。否则重复上一步。



```java
package Chap2;

import java.util.Arrays;

public class FindDuplicate {
    /**
     * 推荐的做法，通过交换元素，将值i保存到numbers[i]
     * 在numbers[i]不和i相等时，如果numbers[i]和numbers[numbers[i]]相等就说明重复元素；
     * 否则就交换这两个元素，这个过程相当于排序。举个例子，通过交换将2放入numbers[2]。

     * @param numbers 待查找的数组
     * @param length 数组的长度，其实就是numbers.length
     * @param duplication 用于保存重复数字，第一个被找到的重复数字存放在duplication[0]中
     * @return 如果在数组中有重复元素
     */
    public boolean duplicate(int numbers[],int length,int [] duplication) {
        if (numbers == null || length <= 0) {
            return false;
        }
        for (int i = 0;i < length;i++){
            if (numbers[i] < 0 || numbers[i] > length -1) {
                return false;
            }
        }

        for (int i = 0; i< length; i++) {
            while (numbers[i] != i) {
                // 现在numbers[i] != i ，设numbers[i] = j,所以如果下面的if成立,就是numbers[i] == numbers[j],说明找到 重复
                if (numbers[i] == numbers[numbers[i]]) {
                    duplication[0] = numbers[i];
                    return true;
                }
                swap(numbers, i, numbers[i]);
            }
        }
        return false;
    }
    // 交换numbers[i]和numbers[numbers[i]]
    private void swap(int[] numbers, int p, int q) {
        int temp = numbers[p];
        numbers[p] = numbers[q];
        numbers[q] = temp;
    }
}

```

代码中尽管有一个两重循环，但是每个数字只需交换一两次就能被放在正确的位置上，因此总的时间复杂度为O(n)。而且所有操作都在原数组上进行，没有引入额外的空间，空间复杂度为O(1)。

---

by @sunhaiyu

2017.12.14
