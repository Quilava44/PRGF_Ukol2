package Model;

import transforms.Point3D;


public class Tetrahedron extends SolidBase{

    public Tetrahedron() {

        /*vertexBuffer.add(new Point3D(0, 1.133, -1.732));
        vertexBuffer.add(new Point3D(2.309, 1.133, 1.732));
        vertexBuffer.add(new Point3D(-2.309, 1.133, 1.732));
        vertexBuffer.add(new Point3D(0, -2.133, 0));*/

        vertexBuffer.add(new Point3D(8, 8, 3));
        vertexBuffer.add(new Point3D(8, 7, 3));
        vertexBuffer.add(new Point3D(10, 10, 2));
        vertexBuffer.add(new Point3D(6, 6, 8));

        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(0);
        indexBuffer.add(2);
        indexBuffer.add(0);
        indexBuffer.add(3);
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(1);
        indexBuffer.add(3);
        indexBuffer.add(2);
        indexBuffer.add(3);
    }
}
