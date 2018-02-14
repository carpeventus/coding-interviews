package Chap2;

/**
 * SingletonImp2在多线程中，如果多个线程同时运行到if (singletonImp2 == null) 就会创建多个对象
 * 所以加上同步锁
 */
public class SingletonImp3 {
    private static SingletonImp3 singletonImp3;

    private SingletonImp3() {}
    // 懒汉模式
    public static SingletonImp3 getInstance() {
        // 同步锁的加入
        synchronized (SingletonImp3.class) {
            if (singletonImp3 == null) {
                singletonImp3 = new SingletonImp3();
            }
        }
        return singletonImp3;
    }
}
