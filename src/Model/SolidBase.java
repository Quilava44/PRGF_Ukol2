package Model;

import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

public abstract class SolidBase {

    public List<Point3D> vertexBuffer = new ArrayList<>();
    public List<Integer> indexBuffer = new ArrayList<>();


    public List<Point3D> getVertices() {
        return vertexBuffer;
    }

    public List<Integer> getIndices() {
        return indexBuffer;
    }


}
