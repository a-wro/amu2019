package reflection;

import singleton.Singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class ReflectionTest {

     private static Logger logger = Logger.getGlobal();

    private static Object createObject(String qualifiedName) {
        Class<?> clazz;
        try {
            clazz = Class.forName(qualifiedName);
            Constructor<?> constructor = clazz.getConstructor();
            return constructor.newInstance();
            } catch (ClassNotFoundException | NoSuchMethodException
                    | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static void main(String[] args) {
            try {
//			    Object obj = createObject(Singleton.class.getCanonicalName());

//			    logger.info(obj.toString());

                Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
                constructor.setAccessible(true);
                Singleton singleton = constructor.newInstance();
                logger.info(singleton.toString());
                constructor.setAccessible(false);


            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

            Method[] methods = Singleton.class.getDeclaredMethods();
            for (Method method : methods) {
//			logger.info(method.getName() + " returns " + method.getReturnType().getSimpleName());
                Class<?>[] parameterClasses = method.getParameterTypes();
                for (Class c : parameterClasses) {
//				logger.info(c.getSimpleName());
                }
            }
        }
    }

