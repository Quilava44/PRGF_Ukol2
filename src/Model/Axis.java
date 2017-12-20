package Model;

import transforms.Point3D;


public class Axis extends SolidBase {

    public Axis() {

        vertexBuffer.add(new Point3D(0, 0, 0));
        vertexBuffer.add(new Point3D(5, 0, 0));
        vertexBuffer.add(new Point3D(0, 5, 0));
        vertexBuffer.add(new Point3D(0, 0, 5));

        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(0);
        indexBuffer.add(2);
        indexBuffer.add(0);
        indexBuffer.add(3);


    }
}
