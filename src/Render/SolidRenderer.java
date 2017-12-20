package Render;

import Model.SolidBase;
import Objects.Line;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

public class SolidRenderer {

    private Mat4 matModel;
    private Mat4 matView;
    private Mat4 matProj;
    private final double wmin = 0.1;

    private LineRenderer lr;


    public void setModel(Mat4 matrix) {
        this.matModel = matrix;
    }

    public void setView(Mat4 matrix) {
        this.matView = matrix;
    }

    public void setProj(Mat4 matrix) {
        this.matProj = matrix;
    }

    public void draw(SolidBase go, BufferedImage img) {

        // Model, View, Projekce - ve 4D vypo��t�me matici pro transformace
        lr = new LineRenderer(img);

        Mat4 finalMat = matModel.mul(matView.mul(matProj));

        Vec3D a, b;

        int w = img.getWidth();
        int h = img.getHeight();

        for (int i = 0; i < go.indexBuffer.size(); i += 2) {
            int iA = go.indexBuffer.get(i);
            int iB = go.indexBuffer.get(i + 1);

            Point3D pA = go.vertexBuffer.get(iA);
            Point3D pB = go.vertexBuffer.get(iB);

            // aplikujeme transformace
            pA = pA.mul(finalMat);
            pB = pB.mul(finalMat);

            if (pA.getW() < pB.getW()) {
                Point3D pom = pA;
                pA = pB;
                pB = pom;
            }

            if (pB.getW() < wmin) {
                double t = (pA.getW() - wmin) / (pA.getW() - pB.getW());
                pB = pA.mul(1 - t).add(pB.mul(t));

            }

            // zm�n�me 4D na 3D pomoc� dehomogenizace (zbav�me se w)
            Optional<Vec3D> optA = pA.dehomog();
            Optional<Vec3D> optB = pB.dehomog();

            if (!optA.isPresent() || !optB.isPresent())
                continue;

            a = optA.get();
            b = optB.get();

            // ViewPort transform (tato transformace p�epo��t� do sou�adnic obrazovky)
			/*
			 * Posuneme, proto�e nyn� jsou sou�adnice od -1 do 1 tak�e pro x
			 * vypo��t�me: (1*x+1)*((w-1)/2) w-width buffered image a pro y
			 * vypo��t�me: (-1*y+1)*((h-1)/2) h-height buffered image
			 */

            double xATrans = ((a.getX()) + 1) * ((w - 1) / 2);
            double yATrans = (-1 * (a.getY()) + 1) * ((h - 1) / 2);

            double xBTrans = ((b.getX()) + 1) * ((w - 1) / 2);
            double yBTrans = (-1 * (b.getY()) + 1) * ((h - 1) / 2);

            // vykreslen�
            lr.drawLineAa(new Line((int)xATrans, (int)yATrans, (int)xBTrans, (int)yBTrans, 0xffffff));
        }
    }

    public void draw(List<SolidBase> go, BufferedImage img) {
        for (SolidBase object : go) {
            draw(object, img);
        }
    }
}
