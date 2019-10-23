package uam.aleksy.lab1.tool;

public interface Tool {
    default void hello() {
        System.out.println("I am a tool");
    }
}
