package App;

import Model.*;
import Objects.Line;
import Render.LineRenderer;
import Objects.Point;
import Render.SolidRenderer;
import transforms.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 *
 * @author PGRF FIM UHK
 * @version 2017
 */
public class Canvas {


    private JFrame window;
    private BufferedImage img;
    private SolidRenderer sr;
    private List<SolidBase> go = new ArrayList<>();
    private Camera cam;
    private double zoom = 1.2;
    private double rotaceX = 0, rotaceY = 0, rotaceZ = 0;
    private double posunX = 0, posunY = 0, posunZ = 0;
    private double doprava = 0, dopredu = 0;
    private int krivka = 0;
    private double azimuth = 0;
    private double zenith = 0;
    private boolean perspective = true;
    private boolean cube = true;
    private boolean tetraherdron = true;
    private boolean axis = true;
    private final double KROK_POSUNU_A_ROTACE = 0.05;
    private final double KROK_ROTACE_KAMERY = Math.toRadians(0.1);
    private int x1, y1;
    private Mat4 m,v,p;

    public Canvas() {
        initGUI();
        window.setLocationRelativeTo(null);
    }

    private void updateCanvas() {
        Graphics g = img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        window.repaint();
    }

    private void initGUI() {
        window = new JFrame("3D Geometry - Michal �ern�");
        img = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
        updateCanvas();

        window.setVisible(true);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel kresliciPlocha = new JLabel(new ImageIcon(img));
        window.add(kresliciPlocha);
        window.pack();

        cam = new Camera(new Vec3D(-10, 0, 0), azimuth, zenith, 1, true);

        redraw();

        window.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }
        });

        window.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double az = (e.getX() - x1) * KROK_ROTACE_KAMERY;
                double ze = (e.getY() - y1) * KROK_ROTACE_KAMERY;

                x1 = e.getX();
                y1 = e.getY();

                azimuth += az;
                zenith += ze;

                redraw();
            }
        });

        window.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    zoom += KROK_POSUNU_A_ROTACE;
                } else {
                    zoom -= KROK_POSUNU_A_ROTACE;
                }
                redraw();
            }
        });

        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        cam = cam.forward(KROK_POSUNU_A_ROTACE);
                        dopredu++;
                        break;
                    case KeyEvent.VK_S:
                        cam = cam.backward(KROK_POSUNU_A_ROTACE);
                        dopredu--;
                        break;
                    case KeyEvent.VK_A:
                        cam = cam.left(KROK_POSUNU_A_ROTACE);
                        doprava--;
                        break;
                    case KeyEvent.VK_D:
                        cam = cam.right(KROK_POSUNU_A_ROTACE);
                        doprava++;
                        break;
                    case KeyEvent.VK_P:
                        perspective = !perspective;
                        break;
                    case KeyEvent.VK_LEFT:
                        posunY += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_RIGHT:
                        posunY -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_UP:
                        posunZ += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_DOWN:
                        posunZ -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_PAGE_UP:
                        posunX -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_PAGE_DOWN:
                        posunX += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_ADD:
                        zoom += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_SUBTRACT:
                        zoom -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD1:
                        rotaceX -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD4:
                        rotaceX += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD7:
                        rotaceY += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD8:
                        rotaceY -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD6:
                        rotaceZ -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD2:
                        rotaceZ += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_SEMICOLON:
                        krivka = 0;
                        break;
                    case KeyEvent.VK_1:
                        krivka = 1;
                        break;
                    case KeyEvent.VK_2:
                        krivka = 2;
                        break;
                    case KeyEvent.VK_3:
                        krivka = 3;
                        break;
                    case KeyEvent.VK_4:
                        if (cube)
                            cube = false;
                        else
                            cube = true;
                        break;
                    case KeyEvent.VK_5:
                        if (tetraherdron)
                            tetraherdron = false;
                        else
                            tetraherdron = true;
                        break;
                    case KeyEvent.VK_6:
                        if (axis) {
                            axis = false;
                            go.clear();
                        } else
                            axis = true;
                        break;
                    case KeyEvent.VK_R:
                        cam = cam.backward(dopredu * KROK_POSUNU_A_ROTACE);
                        cam = cam.left(doprava * KROK_POSUNU_A_ROTACE);
                        rotaceX = rotaceY = rotaceZ = posunX = posunY = posunZ = azimuth = zenith = dopredu = doprava = krivka = 0;
                        perspective = cube = tetraherdron = axis = true;
                        zoom = 1.2;
                        break;
                }
                redraw();
            }
        });
    }

    private void setNotes() {
        Graphics g = img.getGraphics();

        int radek = 1;
        int vyskaPisma = 12;

        g.setFont(new Font("Geneva", Font.BOLD, vyskaPisma));
        g.setColor(Color.BLACK);

        g.drawString("Pohyb kamery: [W] [S] [A] [D]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Zm�na perspektivy: [P]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Zoom: [Kole�ko my�i] [+] [-]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Rotace kolem osy:", 10, 2 + vyskaPisma * radek++);
        g.drawString("X: [Num4] [Num1]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Y: [Num7] [Num8]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Z: [Num2] [Num6]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Posun po ose:", 10, 2 + vyskaPisma * radek++);
        g.drawString("X: [PageUp] [PageDown]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Y: [Left] [Right]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Z: [Up] [Down]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Schovat k�ivku: [;]", 10, 2 + vyskaPisma * radek++);
        g.drawString("K�ivky: [1] [2] [3]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Krychle: [4]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Jehlan: [5]", 10, 2 + vyskaPisma * radek++);
        g.drawString("Osy: [6]", 10, 2 + vyskaPisma * radek++);
        g.drawString("X-modr�, Y-�erven�, Z-zelen�", 10, 2 + vyskaPisma * radek++);
        g.drawString("Reset sc�ny: [R]", 10, 2 + vyskaPisma * radek++);
    }

    public void redraw() {
        updateCanvas();

        cam = cam.withAzimuth(azimuth);
        cam = cam.withZenith(zenith);

        m = ((new Mat4RotX(rotaceX).mul(new Mat4RotY(rotaceY).mul(new Mat4RotZ(rotaceZ))))
                .mul(new Mat4Scale(zoom, zoom, zoom))).mul(new Mat4Transl(posunX, posunY, posunZ));
        v = cam.getViewMatrix();

        if (perspective)
            p = new Mat4PerspRH(1, 1, 1, 1);
        else
            p = new Mat4OrthoRH(13 * img.getHeight() / img.getWidth(), 13 * img.getHeight() / img.getWidth(),0.01, 0);

        sr = new SolidRenderer(m,v,p);

        // vykreslen�
        if (axis)
            sr.draw(new Axis(), img, 1);
        if (tetraherdron)
            sr.draw(new Tetrahedron(), img, 2);
        if (cube)
            sr.draw(new Cube(), img, 3);
        if (krivka != 0)
            sr.draw(new Curve(krivka), img, 4);

        setNotes();
    }
    /*private JPanel panel;
    private BufferedImage img;


    private SolidRenderer wfr = new SolidRenderer();
    private List<SolidBase> go = new ArrayList<>();
    private Camera cam;
    private double zoom = 1.2;
    private double rotaceX = 0, rotaceY = 0, rotaceZ = 0;
    private double posunX = 0, posunY = 0, posunZ = 0;
    private double doprava = 0, dopredu = 0;
    private int krivka = 0;
    private double azimuth = 0;
    private double zenith = 0;
    private boolean perspective = true;
    private boolean cube = true;
    private boolean tetraherdron = true;
    private boolean axis = true;
    private final double KROK_POSUNU_A_ROTACE = 0.05;
    private final double KROK_ROTACE_KAMERY = Math.toRadians(0.1);
    private int x1, y1;


    public Canvas(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);



        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        cam = new Camera(new Vec3D(-10, 0, 0), azimuth, zenith, 1, true);
        redraw();

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
            }

        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                double az = (e.getX() - x1) * KROK_ROTACE_KAMERY;
                double ze = (e.getY() - y1) * KROK_ROTACE_KAMERY;

                x1 = e.getX();
                y1 = e.getY();

                azimuth += az;
                zenith += ze;

                redraw();
            }
        });

        panel.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    zoom += KROK_POSUNU_A_ROTACE;
                } else {
                    zoom -= KROK_POSUNU_A_ROTACE;
                }
                redraw();
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        cam = cam.forward(KROK_POSUNU_A_ROTACE);
                        dopredu++;
                        break;
                    case KeyEvent.VK_S:
                        cam = cam.backward(KROK_POSUNU_A_ROTACE);
                        dopredu--;
                        break;
                    case KeyEvent.VK_A:
                        cam = cam.left(KROK_POSUNU_A_ROTACE);
                        doprava--;
                        break;
                    case KeyEvent.VK_D:
                        cam = cam.right(KROK_POSUNU_A_ROTACE);
                        doprava++;
                        break;
                    case KeyEvent.VK_P:
                        perspective = !perspective;
                        break;
                    case KeyEvent.VK_LEFT:
                        posunY += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_RIGHT:
                        posunY -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_UP:
                        posunZ += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_DOWN:
                        posunZ -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_PAGE_UP:
                        posunX -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_PAGE_DOWN:
                        posunX += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_ADD:
                        zoom += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_SUBTRACT:
                        zoom -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD1:
                        rotaceX -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD4:
                        rotaceX += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD7:
                        rotaceY += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD8:
                        rotaceY -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD6:
                        rotaceZ -= KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_NUMPAD2:
                        rotaceZ += KROK_POSUNU_A_ROTACE;
                        break;
                    case KeyEvent.VK_SEMICOLON:
                        krivka = 0;
                        break;
                    case KeyEvent.VK_1:
                        krivka = 1;
                        break;
                    case KeyEvent.VK_2:
                        krivka = 2;
                        break;
                    case KeyEvent.VK_3:
                        krivka = 3;
                        break;
                    case KeyEvent.VK_4:
                        if (cube)
                            cube = false;
                        else
                            cube = true;
                        break;
                    case KeyEvent.VK_5:
                        if (tetraherdron)
                            tetraherdron = false;
                        else
                            tetraherdron = true;
                        break;
                    case KeyEvent.VK_6:
                        if (axis) {
                            axis = false;
                            go.clear();
                        } else
                            axis = true;
                        break;
                    case KeyEvent.VK_R:
                        cam = cam.backward(dopredu * KROK_POSUNU_A_ROTACE);
                        cam = cam.left(doprava * KROK_POSUNU_A_ROTACE);
                        rotaceX = rotaceY = rotaceZ = posunX = posunY = posunZ = azimuth = zenith = dopredu = doprava = krivka = 0;
                        perspective = cube = tetraherdron = axis = true;
                        zoom = 1.2;
                        break;
                }
                redraw();
        }
    });
    }

    public void redraw() {
        present();

        cam = cam.withAzimuth(azimuth);
        cam = cam.withZenith(zenith);

        // nastaven� matic
        // view
        wfr.setView(cam.getViewMatrix());
        // projection
        if (perspective)
            wfr.setProj(new Mat4PerspRH(1, 1, 1, 1));
        else
            wfr.setProj(new Mat4OrthoRH(13 * img.getHeight() / img.getWidth(), 13 * img.getHeight() / img.getWidth(),
                    0.01, 0));
        // model - zoom, posun, rotace
        wfr.setModel(((new Mat4RotX(rotaceX).mul(new Mat4RotY(rotaceY).mul(new Mat4RotZ(rotaceZ))))
                .mul(new Mat4Scale(zoom, zoom, zoom))).mul(new Mat4Transl(posunX, posunY, posunZ)));

        // vykreslen�
        if (cube)
            wfr.draw(new Cube(), img);
        if (tetraherdron)
            wfr.draw(new Tetrahedron(), img);
        if (axis) {
            wfr.draw(new Axis(), img);
        }
        if (krivka != 0)
            wfr.draw(new Curve(krivka), img);
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }


    public void present() {
        if (panel.getGraphics() != null){
            panel.getGraphics().drawImage(img, 0, 0, null);
        }
    }*/

    public void start() {
        //clear();
        updateCanvas();
        img.getGraphics().drawString("Use mouse buttons", 5, img.getHeight() - 5);
        window.repaint();
        //panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(/*800, 600*/).start());
    }

}