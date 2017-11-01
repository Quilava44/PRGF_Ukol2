package Objects;

public class Point {

    private final float x;
    private final float y;

    public Point() { /*default konstruktor v pripade zadnych vstupnich parametru*/
        this.x = 0;
        this.y = 0;
    }

    public Point(float x, float y) { /*pretizeny konstruktor v pripade vstupnich parametru XY*/
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
