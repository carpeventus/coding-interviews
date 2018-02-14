package Chap6;

import java.util.Arrays;

/**
 * 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这五张牌是不是连续的。2~10是数字本身，A为1，J为11，Q为12，K为13，而大小王可以看成任意数字。
 */
public class PlayCard {
    /**
     * 方法1：排序，计算大小王的个数和总间隔
     */
    public boolean isContinuous(int[] numbers) {
        if (numbers == null || numbers.length != 5) return false;
        Arrays.sort(numbers);
        int joker = 0;
        // 统计大小王的个数，题目说了最多四个
        for (int i = 0; i < 4; i++) {
            if (numbers[i] == 0) joker++;
        }
        // 是对子直接返回false
        // 相邻数字gap为0，4~6之间间隔1，所以有减1的操作
        int totalGap = 0;
        for (int i = numbers.length - 1; i > joker; i--) {
            if (numbers[i] == numbers[i - 1]) return false;
            // 统计总间隔
            totalGap += numbers[i] - numbers[i - 1] - 1;
        }
        // 总的间隔要小于joker的数量才是顺子
        return totalGap <= joker;
    }

    /**
     * 方法2：除了0之外，其他数字不可重复出现；最大最小值只差不得超过5
     */
    public boolean isContinuous2(int [] numbers) {
        if (numbers == null || numbers.length != 5) return false;
        int[] count = new int[14];
        int max = -1;
        int min = 14;
        for (int number : numbers) {
            count[number]++;
            // 对除了0之外的其他数计算最大最小值
            if (number != 0) {
                if (count[number] > 1) return false;
                if (number > max) max = number;
                if (number < min) min = number;
            }
        }
        // 如果没有0，最大最小值之差为4，有0还能凑成顺子的，差值小于4；大于4肯定不能凑成顺子
        return max - min < 5;
    }
}
