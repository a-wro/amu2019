package singleton;

import java.util.logging.Logger;

public class ConcurrencyAttack {
    private static Logger logger = Logger.getLogger(ConcurrencyAttack.class.getName());

    private static ThreadUnsafeSingleton s1;
    private static ThreadUnsafeSingleton s2;
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            s1 = ThreadUnsafeSingleton.getInstance();
        };

        Runnable runnable1 = () -> {
            s2 = ThreadUnsafeSingleton.getInstance();
        };

        Thread a = new Thread(runnable);
        Thread b = new Thread(runnable1);
        a.start();
        b.start();

        Thread.sleep(1000);
        logger.info(Boolean.toString(s1.equals(s2)));

    }
}
