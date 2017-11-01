package Objects;


import java.util.ArrayList;
import java.util.List;


public class Polygon {


    private final List<Point> points = new ArrayList<>();

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

    public void delPoints() {

        points.clear();

    }


    public int getSize(){
        return points.size();
    }

}
