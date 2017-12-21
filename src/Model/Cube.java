package Model;

import transforms.Point3D;


public class Cube extends SolidBase{

    public Cube() {


        vertexBuffer.add(new Point3D(1, 1, 1));
        vertexBuffer.add(new Point3D(2, 1, 1));
        vertexBuffer.add(new Point3D(2, 2, 1));
        vertexBuffer.add(new Point3D(1, 2, 1));
        vertexBuffer.add(new Point3D(1, 1, 2));
        vertexBuffer.add(new Point3D(2, 1, 2));
        vertexBuffer.add(new Point3D(2, 2, 2));
        vertexBuffer.add(new Point3D(1, 2, 2));


        indexBuffer.add(0);
        indexBuffer.add(4);
        indexBuffer.add(0);

        indexBuffer.add(1);
        indexBuffer.add(5);
        indexBuffer.add(4);

        indexBuffer.add(1);
        indexBuffer.add(5);
        indexBuffer.add(1);

        indexBuffer.add(2);
        indexBuffer.add(6);
        indexBuffer.add(5);

        indexBuffer.add(2);
        indexBuffer.add(6);
        indexBuffer.add(2);

        indexBuffer.add(3);
        indexBuffer.add(7);
        indexBuffer.add(6);

        indexBuffer.add(3);
        indexBuffer.add(7);
        indexBuffer.add(3);

        indexBuffer.add(0);
        indexBuffer.add(4);
        indexBuffer.add(7);
    }
}
