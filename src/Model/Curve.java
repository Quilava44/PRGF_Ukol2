package Model;

import transforms.Cubic;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Curve extends SolidBase {

    public Curve(int ktera) {


        Cubic curve = null;

        List<Point3D> body = new ArrayList<>();
        body.add(new Point3D(2, 2, 2));
        body.add(new Point3D(3, 3, 3));
        body.add(new Point3D(9, 1, 3));
        body.add(new Point3D(7, 7, 10));


        switch (ktera) {
            case 1:
                curve = new Cubic(Cubic.FERGUSON, body.get(1), body.get(3), body.get(2), body.get(2));
                break;
            case 2:
                curve = new Cubic(Cubic.COONS, body.get(3), body.get(2), body.get(0), body.get(1));
                break;
            case 3:
                curve = new Cubic(Cubic.BEZIER, body.get(1), body.get(0), body.get(2), body.get(3));
            default:
        }

        for (double i = 0; i < 1; i += 0.001) {
            this.vertexBuffer.add(curve.compute(i));
        }

        for (int i = 0; i < vertexBuffer.size() - 1; i++) {
            this.indexBuffer.add(i);
            this.indexBuffer.add(i + 1);
        }
    }
}
