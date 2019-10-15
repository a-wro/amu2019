package point;

import java.io.Serializable;
import java.util.function.Supplier;

public class Point implements Serializable {
    private static final long serialVersionUID = 42L;
    Supplier <Point> supplier = () -> new Point(1.0f, 2.0f);

    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
