# Serializacja
### 7.
```
public class Point implements Serializable {
    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
```
### 8, 9:
```
FileOutputStream fileOutputStream= new FileOutputStream(
        new File(FILE_PATH)
);
ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

outputStream.writeObject(p1);
outputStream.writeObject(p2);

outputStream.close();
fileOutputStream.close();

FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
```
### 10.
Po deserializacji tworzony jest nowy obiekt na podstawie zserializowanych bajtów i
rezultat jest taki że zdeserializowany obiekt jest kopią oryginału, jednak z punktu widzenia różnych JVM'ów
nie jest to ta sama instancja obiektu (obiekty mają różne adresy), test metodą `equals` lub operatorem `==` skutkuje wartością false.
### 11.
```
public class Point implements Serializable {
    private static final long serialVersionUID = 42L;
    ...
}

```
### 12.
Pole `serialVersionUID` jest używane do nadawania wersji serializowanym obiektom - po dodaniu tego pola nie znaleziono klasy
która pasowałaby do zserializowanego obiektu, jeżeli został on wcześniej zserializowany pod inną wersją.
```
SEVERE: java.io.InvalidClassException: point.Point; local class incompatible: stream classdesc serialVersionUID = 6277311675344571679, local class serialVersionUID = 42
```
### 13.
```
SEVERE: java.io.NotSerializableException: point.Point$$Lambda$18/0x0000000840068c40
```
### 14. TODO

# Singleton
### 15.
```
public class Singleton {
    private static Singleton instance;

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private Singleton() {}
}
```

### 16.
```
  try {
            Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Singleton singleton = constructor.newInstance();
            Singleton singleton1 = Singleton.getInstance();
            // dwa różne obiekty
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            ...
        }
```
### 17.
```
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


public class SerializationAttack {
    private static String FILE_PATH = "serialized_singleton.ser";
    private static Logger logger = Logger.getLogger(SerializationAttack.class.getName());

    public static void main(String[] args) {
        SerializableSingleton s = SerializableSingleton.getInstance();

        try {
            FileOutputStream fileOutputStream= new FileOutputStream(
                    new File(FILE_PATH)
            );
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);

            outputStream.writeObject(s);

            outputStream.close();
            fileOutputStream.close();

            FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            SerializableSingleton s2;
            SerializableSingleton s3;

            s2 = (SerializableSingleton) objectInputStream.readObject();

            FileInputStream fileInputStream2 = new FileInputStream(FILE_PATH);
            ObjectInputStream objectInputStream2 = new ObjectInputStream(fileInputStream2);
            s3 = (SerializableSingleton) objectInputStream2.readObject();

            logger.info(Boolean.toString(s2.equals(s3)));
            // inne obiekty (chyba, że dodamy w singletonie metodę readResolve)


    } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            logger.info(e.toString());
        }
    }
}

```
### 18.
```
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
```

### 19.
- atak refleksją można zapobiec używając enumów, według niektórych źródeł jest to bezpieczne a według innych nie do końca
```
public enum EnumSingleton {
    INSTANCE;

    String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
```


- zabezpieczenie przed atakiem wielowątkowym polega na dodaniu modifiera `synchronized` do statycznego gettera (lub innymi słowy użycie locków)


- atak serializacją można powstrzymać enumem, podobnie jak atak refleksją, ale też dodając metodę readResolve:
```
private static final Singleton instance;
...

private Object readResolve() {
    return instance;
}
```