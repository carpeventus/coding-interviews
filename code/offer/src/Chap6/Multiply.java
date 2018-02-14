package Chap6;

/**
 * 给定一个数组A[0, 1,...n - 1],
 * 请构建一个数组A[0, 1,...n - 1]，其中B中的元素B[i] = A[0]*A[1]*...*A[i - 1]*A[i + 1]*...*A[n - 1]
 * 不能使用除法。
 */
public class Multiply {
    public int[] multiply(int[] A) {
        if (A == null || A.length < 2) return null;
        int len = A.length;
        int[] B = new int[len];
        B[0] = 1;
        for (int i = 1; i < len; i++) {
            // 计算C[i]
            B[i] = B[i - 1] * A[i - 1];
        }
        int d = 1;
        for (int j = len - 2; j >= 0; j--) {
            // 计算D[i]
            d *= A[j + 1];
            // B[i] = C[i] * D[i]
            B[j] *= d;
        }
        return B;
    }
}
