package Render;

import Objects.Line;
import Objects.Polygon;

import java.awt.image.BufferedImage;

public class PolygonRenderer extends Renderer {

    private LineRenderer lr = new LineRenderer(img);


    public PolygonRenderer(BufferedImage img) {
        super(img);
    }

    public void drawPolygon(Polygon p){

        if (p.getSize() >= 3) {
            lr.drawLine(new Line(p.getPoints(p.getSize()-2), p.getPoints(0), 0x2f2f2f));
        }
        if(p.getSize() > 0) {
            for (int i = 0; i < p.getSize(); i++) {
                lr.drawLine(new Line(p.getPoints(i),p.getPoints((i+1)%p.getSize()), 0xffffff));
            }
        }
    }
}
