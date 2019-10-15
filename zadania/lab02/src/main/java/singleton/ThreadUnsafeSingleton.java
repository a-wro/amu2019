package singleton;

public class ThreadUnsafeSingleton {
    private static ThreadUnsafeSingleton instance;

    public static ThreadUnsafeSingleton getInstance() {
        if (instance == null) {
            try {
                instance = new ThreadUnsafeSingleton();
            } catch (InterruptedException e) {}
        }
        return instance;
    }

    private ThreadUnsafeSingleton() throws InterruptedException {
        Thread.sleep(500);
    }
}
