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
        int x0 = p.getX();
        int y0 = p.getY();

        int x1 = edge.a.getX();
        int x2 = edge.b.getX();
        int y1 = edge.a.getY();
        int y2 = edge.b.getY();

        double k = ((y2 - y1) * x0 + (x2 - x1) * y0 + x2 * y1 - y2 * x1);

        if (k < 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Point getIntersections(Edge a, Edge b) {
        int x1 = a.a.getX();
        int x2 = a.b.getX();
        int x3 = b.a.getX();
        int x4 = b.b.getX();

        int y1 = a.a.getY();
        int y2 = a.b.getY();
        int y3 = b.a.getY();
        int y4 = b.b.getY();

        double xtmp1 = (x1 * y2 - x2 * y1) * (x3 - x4) - (x3 * y4 - x4 * y3) * (x1 - x2);
        double xtmp2 = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        double ytmp1 = (x1 * y2 - x2 * y1) * (y3 - y4) - (x3 * y4 - x4 * y3) * (y1 - y2);
        double ytmp2 = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        double x = xtmp1/xtmp2;
        double y = ytmp1/ytmp2;

        return new Point((int)x,(int)y);

    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }
}
