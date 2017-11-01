package Render;

import Objects.Point;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineRenderer extends Renderer {

    public LineRenderer(BufferedImage img) {

        super(img);

    }

    public void drawLine(int x1, int y1, int x2, int y2, int clr) { /*vykresleni usecky pomoci DDA algoritmu*/

        int min, max;
        float k, q, dx, dy, x, y;
        x = x1;
        y = y1;

        dx = x2 - x1;
        dy = y2 - y1;

        k = dy / dx;

        if (Math.abs(dx) > Math.abs(dy)){
            if(x2 < x1) {
                x = min = x2;
                y = y2;
                max = x1;
            }
            else {
                min = x1;
                max = x2;
            }
            q = 1;
        }
        else {
            if (y2 < y1) {
                x = x2;
                y = min = y2;
                max = y1;
            }
            else {
                min = y1;
                max = y2;
            }
            q = 1 / k;
            k = 1;
        }

        for (int i = min; i <= max; i++) {
            if(x > 0 && x < img.getWidth() && y > 0 && y < img.getHeight())
                img.setRGB((int) x, (int) y, clr);
            x += q;
            y += k;
        }

    }

    public void drawLine(Point a, Point b, int clr){

        int x1 = (int) a.getX();
        int x2 = (int) b.getX();
        int y1 = (int) a.getY();
        int y2 = (int) b.getY();

        lineDDA(x1,y1,x2,y2,clr);

    }
}

