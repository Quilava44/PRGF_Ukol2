package App;

import Objects.Point;
import Objects.Polygon;
import Objects.Edge;

import java.util.ArrayList;
import java.util.List;

public class Clipper {

    private Polygon clipArea;

    private List<Point> out = new ArrayList<>();

    public void setClipArea(Polygon clipArea){
        this.clipArea = clipArea;
    }

    public Polygon clip(Polygon in) {

        for (Edge edge : clipArea.getEdges()){
            out.clear();
            Point v1 = in.getPoints(in.getSize()-1);
            for (Point v2: in.getPointsList()) {
                if (edge.isInside(edge, v2)) {
                    if (!edge.isInside(edge, v1))
                        out.add(edge.getIntersections(new Edge(v1, v2), edge));
                    out.add(v2);
                }
                else {
                    if (edge.isInside(edge,v1))
                        out.add(edge.getIntersections(new Edge(v1,v2), edge));
                }

                v1 = v2;
            }
        }

        return new Polygon(out);

    }

}
