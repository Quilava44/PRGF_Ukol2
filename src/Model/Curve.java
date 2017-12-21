package Model;

import transforms.Cubic;
import transforms.Point3D;


public class Curve extends SolidBase {

    private Cubic cc;

    public Curve(int type) {

        Point3D p1 = new Point3D(2, 2, 2);
        Point3D p2 = new Point3D(1, 1, 1);
        Point3D p3 = new Point3D(6, 5, 2);
        Point3D p4 = new Point3D(4, 4, 4);

        switch (type) {
            case 1:
                cc = new Cubic(Cubic.FERGUSON, p1, p2, p3, p4);
                break;
            case 2:
                cc = new Cubic(Cubic.BEZIER, p3, p4, p1, p2);
                break;
            case 3:
                cc = new Cubic(Cubic.COONS, p4, p3, p2, p1);
                break;
        }

        for (double i = 0; i < 1; i += 0.01) {
            this.vertexBuffer.add(cc.compute(i));
        }

        for (int i = 0; i < vertexBuffer.size() - 1; i++) {
            this.indexBuffer.add(i);
            this.indexBuffer.add(i + 1);
        }
    }
}
