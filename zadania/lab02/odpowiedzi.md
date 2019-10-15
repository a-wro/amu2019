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