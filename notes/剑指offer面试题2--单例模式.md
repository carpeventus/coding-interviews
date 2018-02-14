# 剑指offer面试题2--单例模式

单例模式需要满足如下规则：

- 构造函数私有化（private），使得不能直接通过new的方式创建实例对象；
- 通过new在代码内部创建一个（唯一）的实例对象；
- 定义一个public static的公有静态方法，返回上一步中创建的实例对象；由于在静态方法中，所以上一步的对象也应该是static的。

根据这个规则，我们可以写出如下模式，这种模式又被称为**饿汉模式**。不管用不用得到，先new出来再说。

```java
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

```

和饿汉模式对应的称为**懒汉模式**，实例为空时才new出来。

```java
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

```

以上代码有很多待改进的地方。下面一步步来优化。

## 能在多线程下工作，但效率不高

懒汉模式在单线程下可以很好地工作，但是如果多个线程同时执行到`if (singletonImp2 == null) `这句判空操作，那么将会同时创建多个实例对象，所以为了保证在多线程下实例只被创建一次，需要加同步锁。

```java
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

```

## 同步锁只在实例第一次被创建时候才加上

上面的代码在每次调用方法时候都会加锁（即使实例早已被创建），我们知道加锁是很耗时的，实际上我们主要是为了保证在对象为null时，只new出一个实例，只在这个时候加锁就够了。基于这点，改进如下

```java
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

```

## 利用静态代码块

我们知道在Java中，静态代码块只会在用到该类的时候（类加载，调用了静态方法等）被调用唯一的一次，因此在静态代码块中创建实例对象是个不错的选择。

```java
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

```

## 使用静态代码块，按需创建实例

上面的实现种，如果有其他的静态方法（比如上面的`func()`），我们调用它就会导致静态代码块被执行，实例也随之创建，但此时我们可能并不需要这个实例对象——这就导致了该实例被过早地创建了。我们想要的效果是：只有调用getInstance静态方法时，实例才被创建，调用其他静态方法或者其他任何时候都不会创建实例对象，即按照我们的需求在合适的时间被创建。

```java
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

```

可以看到，我们专门建立了一个**静态类**用于创建这个实例，任何静态方法只要没有用到`Nested`就不会创建实例对象。只在`getInstance`方法中才调用`Nested.singletonImp6`保证了当我们调用`SingletonImp`的其他静态方法时，实例不会创建。

---

by @suhaiyu

2017.12.12
