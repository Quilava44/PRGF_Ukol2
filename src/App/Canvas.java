package App;

import Model.*;
import Render.SolidRenderer;
import transforms.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 *
 * @author PGRF FIM UHK
 * @version 2017
 */
public class Canvas {

    private JFrame frame;
    private JPanel panel;
    private BufferedImage img;

    private int x1, y1, x2, y2;
    private Camera cam;
    private Mat4 m,v,p;

    private int curve = 1;
    private double az = Math.PI;
    private double zen = 0;


    private boolean perspective = true;

    private SolidRenderer sr;


    public Canvas (int width, int height) {

        frame = new JFrame();
        frame.setTitle("UHK FIM PGRF : Jan Nešpor" + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width, height));
        frame.add(panel, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        cam = new Camera(new Vec3D(30, 12, 6), az, zen, 30, true);

        m = new Mat4Scale(3,3,3);
        v = cam.getViewMatrix();
        changePersp();

        sr = new SolidRenderer(m,v,p);

       render();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        cam = cam.forward(0.2);
                        v = cam.getViewMatrix();
                        sr.setView(v);
                        break;
                    case KeyEvent.VK_S:
                        cam = cam.backward(0.2);
                        v = cam.getViewMatrix();
                        sr.setView(v);
                        break;
                    case KeyEvent.VK_A:
                        cam = cam.left(0.2);
                        v = cam.getViewMatrix();
                        sr.setView(v);
                        break;
                    case KeyEvent.VK_D:
                        cam = cam.right(0.2);
                        v = cam.getViewMatrix();
                        sr.setView(v);
                        break;
                    case KeyEvent.VK_UP:
                        cam = cam.up(0.2);
                        v = cam.getViewMatrix();
                        sr.setView(v);
                        break;
                    case KeyEvent.VK_DOWN:
                        cam = cam.down(0.2);
                        v = cam.getViewMatrix();
                        sr.setView(v);
                        break;
                    case KeyEvent.VK_P:
                        if (perspective) {
                            perspective = false;
                            changePersp();
                            sr.setProjection(p);
                        } else {
                            perspective = true;
                            changePersp();
                            sr.setProjection(p);
                        }
                        break;
                    case KeyEvent.VK_C:
                        switch (curve) {
                            case 1: curve = 2;
                                break;
                            case 2: curve = 3;
                                break;
                            case 3: curve = 1;
                                break;
                        }
                        break;
                    case KeyEvent.VK_NUMPAD2:
                        m = m.mul(new Mat4Transl(0,0,-0.2));
                        sr.setModel(m);
                        break;
                    case KeyEvent.VK_NUMPAD4:
                        m = m.mul(new Mat4Transl(0,-0.2,0));
                        sr.setModel(m);
                        break;
                    case KeyEvent.VK_NUMPAD6:
                        m = m.mul(new Mat4Transl(0,0.2,0));
                        sr.setModel(m);
                        break;
                    case KeyEvent.VK_NUMPAD8:
                        m = m.mul(new Mat4Transl(0,0,0.2));
                        sr.setModel(m);
                        break;
                    case KeyEvent.VK_NUMPAD1:
                        m = m.mul(new Mat4Transl(0.2,0,0));
                        sr.setModel(m);
                        break;
                    case KeyEvent.VK_NUMPAD3:
                        m = m.mul(new Mat4Transl(-0.2,0,0));
                        sr.setModel(m);
                        break;
                }
                render();
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }

        });

        frame.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                if (mouseWheelEvent.getWheelRotation() < 0) {
                    m = m.mul(new Mat4Scale(1.2, 1.2, 1.2));
                    sr.setModel(m);
                }
                else {
                    m = m.mul(new Mat4Scale(0.8, 0.8, 0.8));
                    sr.setModel(m);
                }

                render();
            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();

                m = m.mul(new Mat4RotZ((x2 -x1) / (double)img.getWidth() * 2)).mul(new Mat4RotY((y2 -y1) / (double)img.getHeight() * 2));
                sr.setModel(m);
                render();

                x1 = x2;
                y1 = y2;
            }
        });

        SwingUtilities.invokeLater(() -> {
            SwingUtilities.invokeLater(() -> {
                SwingUtilities.invokeLater(() -> {
                    SwingUtilities.invokeLater(() -> {
                        present();
                    });
                });
            });
        });

    }

    public void changePersp() {
        if (perspective)
            p = new Mat4PerspRH(Math.PI / 3, (double) img.getHeight() / (double) img.getWidth(), 0.1, 10);
        else
            p = new Mat4OrthoRH(img.getWidth() / 20, img.getHeight() / 20, 0.1, 10);
    }

    public void render() {

        clear();

        sr.draw(new Axis(), img, 1);
        sr.draw(new Tetrahedron(), img, 2);
        sr.draw(new Cube(), img, 3);

        switch (curve) {
            case 1:
                sr.draw(new Curve(1), img, 4);
                break;
            case 2:
                sr.draw(new Curve(2), img, 4);
                break;
            case 3:
                sr.draw(new Curve(3), img, 4);
                break;
        }

        present();
    }


    public void clear() {
        Graphics g = img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    private void present() {
        if (panel.getGraphics() != null) panel.getGraphics().drawImage(img, 0, 0, null);
    }


    public void start() {
        img.getGraphics().drawString("Ovladani: Pohyb kamery - WSAD, Prepinani krivek - C, Zmena projekce - P, Zoom - KoleckoMys, Rozhlizeni - Mouse1 + Drag ", 5, img.getHeight() - 5);
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}