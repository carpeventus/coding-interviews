package Chap2;

/**
 * SingletonImp5使用静态代码块，如果该类中还有其他静态方法，调用后就会执行静态代码块使得对象过早创建
 * 使用一个静态类来创建Singleton，其他静态方法只要没有调用Nested.singletonImp6就不会创建Singleton
 * 实现了需要时才创建实例对象，避免过早创建
 */
public class SingletonImp6 {
    private SingletonImp6() {}

    // 专门用于创建Singleton的静态类
    private static class Nested {
        private static SingletonImp6 singletonImp6;
        static {
            singletonImp6 = new SingletonImp6();
        }
    }

    public static SingletonImp6 getInstance() {
        return Nested.singletonImp6;
    }

    public static void func() {

    }

    public static void main(String[] args) {
        System.out.println(SingletonImp6.getInstance());
    }
}
