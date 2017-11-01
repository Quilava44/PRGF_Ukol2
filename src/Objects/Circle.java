package Objects;


public class Circle {

    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int clr;

    public Circle (int x1, int y1, int x2, int y2, int clr) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.clr = clr;
    }

    public Circle (Point a, Point b, int clr) {
        this.x1 = (int)a.getX();
        this.y1 = (int)a.getY();
        this.x2 = (int)b.getX();
        this.y2 = (int)b.getY();
        this.clr = clr;
    }


    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public int getClr() {
        return clr;
    }
}
