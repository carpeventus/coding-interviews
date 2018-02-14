package Chap2;
/**
 * 单例模式，饿汉模式，不管为不为空，先直接new一个出来
 */
public class SingletonImp {
    // 饿汉模式
    private static SingletonImp singletonImp = new SingletonImp();
    // 私有化（private）该类的构造函数
    private SingletonImp() {
    }

    public static SingletonImp getInstance() {
        return singletonImp;
    }

    public static void main(String[] args) {
        System.out.println(SingletonImp.getInstance());
    }
}
