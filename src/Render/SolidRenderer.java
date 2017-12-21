package Render;

import Model.SolidBase;
import Objects.Line;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.image.BufferedImage;
import java.util.Optional;

public class SolidRenderer {

    private Mat4 matModel;
    private Mat4 matView;
    private Mat4 matProj;
    private Mat4 matrix;
    private double minimum = 0.1;
    private Point3D firstPoint, secondPoint;

    private LineRenderer lr;

    public SolidRenderer(Mat4 m, Mat4 v, Mat4 p) {
        this.matModel = m;
        this.matView = v;
        this.matProj = p;
    }

    public void draw(SolidBase sb, BufferedImage img, int id) {

        // Iniciace LineRendereru pro pozdejsi vyuziti u Rasterizace
        lr = new LineRenderer(img);

        //Vypocet Matrix matice
        matrix = matModel.mul(matView).mul(matProj);

        // Hlavni cyklus
        for (int i = 0; i < sb.indexBuffer.size(); i += 2) {

            // Transformujeme bod
            firstPoint = sb.vertexBuffer.get(sb.indexBuffer.get(i)).mul(matrix);
            secondPoint = sb.vertexBuffer.get(sb.indexBuffer.get(i + 1)).mul(matrix);

            //Prohozeni bodu pokud W1 < W2
            if (firstPoint.getW() < secondPoint.getW()) flipPoints();

            //Aplikace transformace pokud minimum > W2
            if (minimum > secondPoint.getW()) {
                double tmp = (firstPoint.getW() - minimum) / (firstPoint.getW() - secondPoint.getW());
                secondPoint = firstPoint.mul(1 - tmp).add(secondPoint.mul(tmp));
            }

            // Dehomog
            Optional<Vec3D> firstOptional = firstPoint.dehomog();
            Optional<Vec3D> secondOptional = secondPoint.dehomog();

            if (!firstOptional.isPresent() || !secondOptional.isPresent()) continue;

            Vec3D firstVector = firstOptional.get();
            Vec3D secondVector = secondOptional.get();

            //Transform do okna
            double x1 = ((img.getWidth() - 1) * (firstVector.getX() + 1)) / 2;
            double y1 = ((img.getHeight() - 1) * (1 - firstVector.getY()) / 2);
            double x2 = ((img.getWidth() - 1) * (secondVector.getX() + 1)) / 2;
            double y2 = ((img.getHeight() - 1) * (1 - secondVector.getY()) / 2);

            // Rasterizace pomoci AA line algoritmu z predchozich uloh
            lr.drawLineAa(new Line((int) x1, (int) y1, (int) x2, (int) y2, id));
        }
    }

    public void flipPoints() {
        Point3D tmp = firstPoint;
        firstPoint = secondPoint;
        secondPoint = tmp;
    }

}
