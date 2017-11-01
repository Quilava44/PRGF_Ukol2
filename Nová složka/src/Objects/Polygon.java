package Objects;


import java.util.ArrayList;
import java.util.List;
import Render.LineRenderer;

import javax.sound.sampled.Line;

public class Polygon {


    private final List<Point> points = new ArrayList<>();
    private LineRenderer lr;

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

    public List<Point> givePoints() {

        return points;
    }

    public void delPoints() {

        points.clear();

    }

    public int getCount() {

       return points.size();

    }

    public int getSize(){
        return points.size();
    }

}
