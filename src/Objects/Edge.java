package Objects;

public class Edge {

    private final Point a;
    private final Point b;

    public Edge(Point a, Point b) {

        this.a = a;
        this.b = b;

    }

    public boolean isHorizontal() {

        return (a.getY() == b.getY());

    }

    public Edge getOrientedEdge() {

        if ((a.getY() > b.getY()))
            return new Edge(b,a);
        return this;

    }

    public boolean isIntersection(int y) {

        return (y >= a.getY() && y < b.getY());

    }

    public int getIntersection(int y) {

        double k = (b.getX() - a.getX()) / (b.getY() - a.getY());
        double q = a.getX() - k * a.getY();

        return (int)((k * y) + q);

    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }
}
