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

        double k = (double)(b.getX() - a.getX()) / (double)(b.getY() - a.getY());
        double q = (double)a.getX() - k * (double)a.getY();

        return (int)((k * y) + q);

    }

    public boolean isInside(Edge edge, Point p) {
        return (edge.a.getX() - p.getX()) * (edge.b.getY() - p.getY()) > (edge.a.getY() - p.getY()) * (edge.b.getX() - p.getX());
    }

    public Point getIntersections(Edge a, Edge b) {
        double x1 = (a.a.getX() * a.b.getY() - a.a.getY() * a.b.getX()) * (b.a.getX() - b.b.getX()) - (a.a.getX() - a.b.getX()) * (b.a.getX() * b.b.getY() - b.a.getY() * b.b.getX());
        double x2 = (a.a.getX() - a.b.getX()) * (b.a.getY() - b.b.getY()) - (a.a.getY() - a.b.getY()) * (b.a.getX() - b.b.getX());


        double y1 = (a.a.getX() * a.b.getY() - a.a.getY() * a.b.getX()) * (b.a.getY() - b.b.getY()) - (a.a.getY() - a.b.getY()) * (b.a.getX() * b.b.getY() - b.a.getY() * b.b.getX());
        double y2 = (a.a.getX() - a.b.getX()) * (b.a.getY() - b.b.getY()) - (a.a.getY() - a.b.getY()) * (b.a.getX() - b.b.getX());

        double x = x1/x2;
        double y = y1/y2;

        return new Point((int)x,(int)y);

    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }
}
