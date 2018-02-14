package Chap2;

/**
 * 静态代码块只在类加载的时候调用一次（静态方法调用等第一次用到该类的时候）
 */
public class SingletonImp5 {
    private static SingletonImp5 singletonImp5;
    private SingletonImp5() {}

    static {
        singletonImp5 = new SingletonImp5();
    }

    public static SingletonImp5 getInstance() {
        return singletonImp5;
    }


    public static void func() {

    }

    public static void main(String[] args) {
        // 调用任意静态方法，都会创建实例，导致过早创建
        SingletonImp5.func();
        System.out.println(SingletonImp5.singletonImp5);
    }
}
