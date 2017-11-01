package Render;

import Objects.Line;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LineRenderer extends Renderer {

    public LineRenderer(BufferedImage img) {

        super(img);

    }

    /**
     * Vykresleni usecky pomoci DDA alg.
     */


    public void lineDda(int x1, int y1, int x2, int y2, int clr) {

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
            try { img.setRGB((int) x, (int) y, clr); } catch(Exception e) {}
            x += q;
            y += k;
        }

    }

    public void drawLine(Line ln){

        int x1 = ln.getX1();
        int x2 = ln.getX2();
        int y1 = ln.getY1();
        int y2 = ln.getY2();

        lineDda(x1,y1,x2,y2,ln.getClr());

    }

    /**
     * AA Usecka, pouzit Xiaolin Wu's line algorithm
     */


    public void drawLineAa(Line ln) {

        double x0 = ln.getX1();
        double y0 = ln.getY1();
        double x1 = ln.getX2();
        double y1 = ln.getY2();

        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);


        if (steep) {
            double tmp = x0;
            x0 = y0;
            y0 = tmp;

            tmp = x1;
            x1 = y1;
            y1 = tmp;
        }
        if (x0 > x1) {
            double tmp = x0;
            x0 = x1;
            x1 = tmp;

            tmp = y0;
            y0 = y1;
            y1 = tmp;
        }

        double dx = x1 - x0;
        double dy = y1 - y0;
        double gradient = dy / dx;
        if (dx == 0.0)
            gradient = 1.0;

        double xend = Math.round(x0);
        double yend = y0 + gradient * (xend - x0);
        double xgap = rfpart(x0 + 0.5);
        double x11 = xend;
        double y11 = ipart(yend);

        if (steep) {
            draw(y11, x11, rfpart(yend) * xgap);
            draw(y11 + 1, x11, fpart(yend) * xgap);
        } else {
            draw(x11, y11, rfpart(yend) * xgap);
            draw(x11, y11 + 1, fpart(yend) * xgap);
        }

        double intery = yend + gradient;

        xend = Math.round(x1);
        yend = y1 + gradient * (xend - x1);
        xgap = fpart(x1 + 0.5);
        double x12 = xend;
        double y12 = ipart(yend);

        if (steep) {
            draw(y12, x12, rfpart(yend) * xgap);
            draw(y12 + 1, x12, fpart(yend) * xgap);
        } else {
            draw(x12, y12, rfpart(yend) * xgap);
            draw(x12, y12 + 1, fpart(yend) * xgap);
        }

        for (double x = x11 + 1; x <= x12 - 1; x++) {
            if (steep) {
                draw(ipart(intery), x, rfpart(intery));
                draw(ipart(intery) + 1, x, fpart(intery));
            } else {
                draw(x, ipart(intery), rfpart(intery));
                draw(x, ipart(intery) + 1, fpart(intery));
            }
            intery = intery + gradient;
        }
    }

    public void draw(double x, double y, double c) {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0f, 0f, 0f, (float)c));
        gr.fillOval((int) x, (int) y, 2, 2);

    }

    public int ipart(double x) {
        return (int) x;
    }

    public double fpart(double x) {
        return x - Math.floor(x);
    }

    public double rfpart(double x) {
        return 1.0 - fpart(x);
    }

}

