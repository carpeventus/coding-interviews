package Chap2;

/**
 * 单例模式，懒汉模式，为空才new
 */
public class SingletonImp2 {
    private static SingletonImp2 singletonImp2;

    private SingletonImp2() {}
    // 懒汉模式
    public static SingletonImp2 getInstance() {
        if (singletonImp2 == null) {
            singletonImp2 = new SingletonImp2();
        }
        return singletonImp2;
    }

    public static void main(String[] args) {
        System.out.println(SingletonImp2.getInstance());
    }
}
