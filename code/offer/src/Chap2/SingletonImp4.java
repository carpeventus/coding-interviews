package Chap2;

/**
 * SingletonImp3中每次调用getInstance都会加同步锁，而加锁是一个很耗时的过程
 * 实际上加锁只需要在第一次创建对象时
 */
public class SingletonImp4 {
    private static SingletonImp4 singletonImp4;

    private SingletonImp4() {}

    public static SingletonImp4 getInstance() {
        // 第一次创建时才加锁
        if (singletonImp4 == null) {
            synchronized (SingletonImp4.class) {
                if (singletonImp4 == null) {
                    singletonImp4 = new SingletonImp4();
                }
            }
        }

        return singletonImp4;
    }
}
