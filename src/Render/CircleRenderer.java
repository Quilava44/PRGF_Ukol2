package Render;

import Objects.Circle;
import Objects.Line;

import java.awt.image.BufferedImage;


public class CircleRenderer extends Renderer {

    private LineRenderer lr = new LineRenderer(img);

    public CircleRenderer (BufferedImage img) {
            super(img);
        }

    /**
     * Kruznice, pouziti Mid-point alg.
     */

    public void drawMidpoint(int a, int b, int rad, int clr) {

        int x = rad - 1;
        int y = 0;
        int dx = 1;
        int dy = 1;
        int tmp = dx - (rad << 1);

        while(x >= y) {

            try { img.setRGB(a + x, b + y, clr); } catch(Exception e) {}
            try { img.setRGB(a + y, b + x, clr); } catch(Exception e) {}
            try { img.setRGB(a - y, b + x, clr); } catch(Exception e) {}
            try { img.setRGB(a - x, b + y, clr); } catch(Exception e) {}
            try { img.setRGB(a - x, b - y, clr); } catch(Exception e) {}
            try { img.setRGB(a - y, b - x, clr); } catch(Exception e) {}
            try { img.setRGB(a + y, b - x, clr); } catch(Exception e) {}
            try { img.setRGB(a + x, b - y, clr); } catch(Exception e) {}

            if (tmp <= 0) {
                y++;
                tmp += dy;
                dy += 2;
            } else {
                x--;
                dx += 2;
                tmp += (-rad << 1) + dx;
            }
        }

    }

    public void drawCircle(Circle cr) {
            drawMidpoint(cr.getX1(), cr.getY1(), getRadius(cr), cr.getClr());
        }

    public int getRadius(Circle cr) {
        return (int)Math.sqrt((cr.getX2()-cr.getX1())*(cr.getX2()-cr.getX1()) + (cr.getY2()-cr.getY1())*(cr.getY2()-cr.getY1()));
    }

    /**
     * Vysec
     */

    public void drawSec(Circle cr) {
        double startAngle = -Math.PI;
        double endAngle = Math.atan2(cr.getY2() - cr.getY1(), cr.getX2() - cr.getX1());
        int x = 0;
        int y = 0;
        int rad = getRadius(cr);

        lr.drawLine(new Line(cr.getX1(), cr.getY1(), cr.getX2(), cr.getY2(), 0xffffff));

        boolean startAngleOld = true;
        while (startAngle <= endAngle) {
            x = (int) (cr.getX1() + rad * Math.cos(startAngle));
            y = (int) (cr.getY1() + rad * Math.sin(startAngle));
            try {
                img.setRGB(x, y, 0xffffff);
            } catch (Exception e) {
            }
            startAngle = startAngle + 0.01;
            if (startAngleOld) {
                lr.drawLine(new Line(cr.getX1(), cr.getY1(), x, y, 0xffffff));
                startAngleOld = false;
            }
        }
    }
}
