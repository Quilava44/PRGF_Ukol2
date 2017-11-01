package Render;

import Objects.Polygon;

import java.awt.image.BufferedImage;

public class PolygonRenderer extends Renderer {

    private LineRenderer lr = new LineRenderer(img);


    public PolygonRenderer(BufferedImage img) {
        super(img);
    }

    public void drawPolygon(Polygon p){

        if(p.getSize() > 0){
            for(int i = 0;i < p.getSize();i++){
                lr.drawLine(p.getPoints(i),p.getPoints((i+1)%p.getSize()), 0xffffff);
            }
        }


    }
}
