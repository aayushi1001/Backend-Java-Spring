package Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

//TODO: Topic: Blocking reflection manually

public class Singleton {
    private static final Singleton INSTANCE = new Singleton();
    private static boolean instanceCreated = false;

    private Singleton() {
        if (instanceCreated) {
            throw new UnsupportedOperationException("Use getInstance() instead");
        }
        instanceCreated = true;
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }
}

class Main {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Without defense:
        Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Singleton instance = constructor.newInstance(); // would succeed

        // With defense:
        constructor.newInstance(); // throws UnsupportedOperationException
    }
}


