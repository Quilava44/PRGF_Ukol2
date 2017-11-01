package Objects;


public class Circle {

    private final int x1, y1, radius;

    public Circle(int x1, int y1, int x2,int y2, int color) {
        this.x1 = x1;
        this.y1 = y1;
        radius = calculateRadius(x1, y1, x2, y2);
    }

    public Circle(int x1, int y1,int x2,int y2) {
        this.x1 = x1;
        this.y1 = y1;
        radius = calculateRadius(x1, y1, x2, y2);
    }


    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getRadius() {
        return radius;
    }


    private int calculateRadius(int x1, int y1,int x2,int y2){

        return (int)Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
    }


}
