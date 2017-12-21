package Model;

import transforms.Point3D;


public class Tetrahedron extends SolidBase{

    public Tetrahedron() {

        vertexBuffer.add(new Point3D(4, 4, 1));
        vertexBuffer.add(new Point3D(6, 5, 2));
        vertexBuffer.add(new Point3D(6, 6, 1));
        vertexBuffer.add(new Point3D(4, 4, 4));

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
