package singleton;

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
