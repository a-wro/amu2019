package singleton;

import java.io.Serializable;

public class SerializableSingleton implements Serializable {
    private static long serialVersionUID = 123L;
    private static SerializableSingleton instance;

    public static SerializableSingleton getInstance() {
        if (instance == null) {
            instance = new SerializableSingleton();
        }
        return instance;
    }

    private SerializableSingleton() {}
}
