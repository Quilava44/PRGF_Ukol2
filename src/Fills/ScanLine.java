package Fills;

import Objects.Edge;
import Objects.Polygon;
import Objects.Point;
import Render.Renderer;
import Render.LineRenderer;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ScanLine extends Renderer {

    private List<Edge> edges = new ArrayList<>();
    private List<Integer> inter = new ArrayList<>();

    public ScanLine(BufferedImage img) {

        super(img);

    }

    public void fill(Polygon p) {

        float minY = p.getPoints(0).getY();
        float maxY = p.getPoints(0).getY();

        LineRenderer lr = new LineRenderer(img);

        for (int i = 0; i < p.getSize(); i++) {

            if (p.getPoints(i).getY() < minY)
                minY = p.getPoints(i).getY();
            else if (p.getPoints(i).getY() > maxY)
                maxY = p.getPoints(i).getY();

            Point a = p.getPoints(i);
            Point b = p.getPoints((i + 1) % p.getSize());

            Edge edge = new Edge(a, b);

            if (!edge.isHorizontal())
                edges.add(edge.getOrientedEdge());
        }

        for (int y = (int) minY; y <= (int) maxY; y++) {

            inter.clear();

            for (Edge ed : edges)
                if (ed.isIntersection(y))
                    inter.add(ed.getIntersection(y));

            selectionSort();

            for (int x = 0; x < inter.size() - 1; x += 2)
                lr.lineDda(inter.get(x), y, inter.get(x + 1), y, 0xff0000);
        }
    }

    public void selectionSort() {

        for (int i = 0; i < inter.size()-1; i++)
        {
            int min = i;
            for (int j = i+1; j < inter.size()-1; j++)
                if (inter.get(j) < inter.get(min))
                    min = j;

            int tmp = inter.get(min);
            inter.set(min, inter.get(i));
            inter.set(i, tmp);
        }
    }

}
