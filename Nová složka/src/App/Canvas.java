package App;

import Render.LineRenderer;
import Render.PolygonRenderer;
import Objects.Polygon;
import Objects.Point;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 *
 * @author PGRF FIM UHK
 * @version 2017
 */
public class Canvas {

    private JPanel panel;
    private BufferedImage img;
    private int x1,y1,x2,y2;
    private LineRenderer lr;
    private Polygon p = new Polygon();
    private PolygonRenderer pr;

    private JPanel menu;
    private JRadioButton usBtn;
    private JRadioButton kruzBtn;
    private JRadioButton polyBtn;
    private ButtonGroup grpBtn;

    private JButton delBtn;


    public Canvas(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        lr = new LineRenderer(img);
        pr = new PolygonRenderer(img);


       panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        menu = new JPanel();
        usBtn = new JRadioButton("Úsečka");
        usBtn.setSelected(true);
        polyBtn = new JRadioButton("Polygon");
        kruzBtn = new JRadioButton("Kružnice");

        grpBtn = new ButtonGroup();
        grpBtn.add(usBtn);
        grpBtn.add(polyBtn);
        grpBtn.add(kruzBtn);

        menu.add(usBtn);
        menu.add(polyBtn);
        menu.add(kruzBtn);

        delBtn = new JButton("Smazat");

        delBtn.addActionListener(actionEvent -> {
            clear();
            if (polyBtn.isSelected())
                p.delPoints();
            present();
        });
        menu.add(delBtn);

        frame.add(menu, BorderLayout.NORTH);
        frame.add(panel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    x1 = e.getX();
                    y1 = e.getY();

                    if (polyBtn.isSelected()) {
                        p.addPoint(new Point(x1,y1));
                        if (p.getSize() >= 2){
                            for (int i = 0; i < p.getSize()-1; i++){

                                lr.drawLine(p.getPoints(p.getSize()-1), p.getPoints(0),0x2f2f2f);

                                if (i < p.getSize())
                                    lr.drawLine(p.getPoints(i), p.getPoints(i+1),0xffffff);

                                lr.drawLine(p.getPoints(i+1), p.getPoints(0),0xffffff);
                            }
                            present();
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();

                if (usBtn.isSelected()) {
                    p.addPoint(new Point(x2, y2));
                    lr.drawLine(x1, y1, x2, y2, 0xffffff);
                }

                panel.repaint();
            }

        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (usBtn.isSelected()) {
                    clear();
                    x2 = e.getX();
                    y2 = e.getY();
                    lr.drawLine(x1, y1, x2, y2, 0xffffff);
                    present();
                }
            }
        });
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
    }

    public void start() {
        clear();
        img.getGraphics().drawString("Use mouse buttons", 5, img.getHeight() - 5);
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 600).start());
    }

}