package Chap4;

import java.util.LinkedList;

/**
 * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。
 * 假设压入栈的所有数字均不相等。例如序列1,2,3,4,5是某栈的压入顺序，序列4，5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列
 * （注意：这两个序列的长度是相等的）
 */
public class StackPopOrder {

    public boolean IsPopOrder(int [] pushA,int [] popA) {
        if (pushA == null || popA == null || pushA.length == 0 || popA.length == 0) {
            return false;
        }
        // 辅助栈
        LinkedList<Integer> stackAux = new LinkedList<>();
        int popIndex = 0;
        for (int i = 0;i < pushA.length;i++) {
            // 按照入栈序列依次压入辅助栈中
            stackAux.push(pushA[i]);
            // 每入栈一次和出栈序列比较，如果栈顶和当前出栈元素相同，则弹出同时当前弹出元素指针前移；
            // 如果下一个栈顶元素还和当前弹出元素相同，继续弹出
            while (!stackAux.isEmpty() && stackAux.peek() == popA[popIndex]) {
                stackAux.pop();
                popIndex++;
            }
        }
        // 如果出栈顺序正确，模拟一次进出栈后，辅助栈应该为空。不为空说明序列不正确
        return stackAux.isEmpty();
    }
}