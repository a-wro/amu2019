package singleton;

public class EnumSingletonTest {
    public static void main(String[] args) {
        EnumSingleton enumSingleton = EnumSingleton.INSTANCE;
        enumSingleton.setTitle("abcd");
    }
}
