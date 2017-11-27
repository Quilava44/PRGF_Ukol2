package Objects;


import java.util.ArrayList;
import java.util.List;


public class Polygon {


    private final List<Point> points = new ArrayList<>();

    public Polygon(){

    }

    public Polygon(List<Point> points) {
        this.points.addAll(points);
    }

    public void addPoint(Point point){
        points.add(point);

    }

    public Point getPoints(int i) {


        if(i < 0 || i >= points.size() )
        {
            throw new IndexOutOfBoundsException();
        }
        return points.get(i);
    }

    public List<Point> getPointsList() {
        return points;
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < points.size(); i++) {
            edges.add(new Edge(points.get(i), points.get((i + 1) % points.size())));
        }

        return edges;
    }

    public void delPoints() {

        points.clear();

    }


    public int getSize(){
        return points.size();
    }

}
