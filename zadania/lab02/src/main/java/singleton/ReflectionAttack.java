package singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

public class ReflectionAttack {
    private static Logger logger = Logger.getLogger(ReflectionAttack.class.getName());

    public static void main(String[] args) {
        try {
            Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Singleton singleton = constructor.newInstance();
            Singleton singleton1 = Singleton.getInstance();
            logger.info("singleton equals singleton1 -> " + (singleton.equals(singleton1)));
            // dwa różne obiekty
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            logger.info(e.toString());
        }
    }
}
