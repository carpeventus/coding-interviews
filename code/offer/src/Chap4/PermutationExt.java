package Chap4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通用的根据数组输入得到全排列的程序
 */
public class PermutationExt {
    /**
     * 应用排列解决问题1：输入一个含有8个数字的数组，
     * 判断有没有可能吧这8个数字分别放到正方体的8个顶点，使得正方体三对相对的面上的4个顶点的和相等
     */
    public List<int[]> possibilitiesOfCube(int[] array) {
        List<int[]> list = new ArrayList<>();
        if (array == null || array.length == 0) {
            return list;
        }

        // 得到全排列集合
        List<int[]> all = permutation(array);
        // 筛选：满足三个对立面的值相等才会被加入最终结果集中
        for (int[] arr : all) {
            if (checkSum(arr)) list.add(arr);
        }
        return list;
    }

    public List<int[]> permutation(int[] array) {
        List<int[]> list = new ArrayList<>();
        collect(array, 0, list);
        return list;
    }

    private void collect(int[] array, int begin, List<int[]> list) {
        if (begin == array.length - 1) {
            // list中没有同样的序列
            if (!has(list, array)) {
                // 必须使用副本，不能直接传入引用，否则list所有的int[]对象最后都一样
                list.add(Arrays.copyOf(array, array.length));
                return;
            }
        }

        for (int i = begin; i < array.length; i++) {
            swap(array, i, begin);
            collect(array, begin + 1, list);
            swap(array, i, begin);
        }
    }

    private boolean checkSum(int[] array) {
        if ((array[0] + array[2] + array[4] + array[6] == array[1] + array[3] + array[5] + array[7]) &&
                (array[0] + array[1] + array[2] + array[3] == array[4] + array[5] + array[6] + array[7]) &&
                (array[2] + array[3] + array[6] + array[7] == array[0] + array[1] + array[4] + array[5])) {
            return true;
        }
        return false;
    }

    private boolean has(List<int[]> list, int[] a) {
        for (int[] arr : list) {
            if (equal(arr, a)) return true;
        }

        return false;
    }

    private boolean equal(int[] a, int[] b) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        PermutationExt p = new PermutationExt();
        int[] a = {8, 3, 5, 4, 1, 2, 5, 6};
        List<int[]> list = p.possibilitiesOfCube(a);
        System.out.println("有" + list.size() + "种可能");
        for (int[] arr : list) {
            System.out.println(Arrays.toString(arr));
        }
    }
}
