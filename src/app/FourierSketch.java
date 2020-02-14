package app;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;



public class FourierSketch extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Arm[] arms1, arms2;
    private ArrayList<Point> fourierPoints1, fourierPoints2;
    private JFrame frame;
    private int width, height, padding, yOffset;

    public FourierSketch(Arm[] arms1, Arm[] arms2, int width, int height, int padding, int yOffset) {
        this.arms1 = arms1;
        this.arms2 = arms2;
        fourierPoints1 = new ArrayList<Point>();
        fourierPoints2 = new ArrayList<Point>();
        this.width = width;
        this.height = height;
        this.padding = padding;
        this.yOffset = yOffset;
    }

    public void showSketchWindow() {
        frame = new JFrame();
        frame.setSize(width, height + yOffset);
        frame.setTitle("Fourier Sketch");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.add(this);
    }

    @Override 
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect((int) (0.5*width), (int) (0.5*height), (int) (0.5*width) - 2 * padding, (int) (0.5*height) - 2 * padding);
        g2.drawRect(padding, padding, (int) (0.5*width) - 2 * padding, (int) (0.5*height) - 2 * padding);
        g2.drawRect((int) (0.5*width), padding, (int) (0.5*width) - 2 * padding, (int) (0.5*height) - 2 * padding);
        g2.drawRect(padding, (int) (0.5*height), (int) (0.5*width) - 2 * padding, (int) (0.5*height) - 2 * padding);
        
        for (int i = 0; i < arms1.length; i++) {
            arms1[i].drawArm(g2);
            arms2[i].drawArm(g2);

            if (i + 1 < arms1.length) {
                arms1[i+1].setParentArm(arms1[i]);
                arms2[i+1].setParentArm(arms2[i]);
            } else {
                Point fourierPoint1 = new Point(arms1[i].getEndPoint().x, arms2[i].getEndPoint().y);
                Point fourierPoint2 = new Point(arms2[i].getEndPoint().x, arms1[i].getEndPoint().y);
                fourierPoints1.add(fourierPoint1);
                fourierPoints2.add(fourierPoint2);
                g2.setStroke(new BasicStroke(1));
                g2.drawLine(arms1[i].getEndPoint().x, arms1[i].getEndPoint().y, fourierPoint1.x, fourierPoint1.y);
                g2.drawLine(arms2[i].getEndPoint().x, arms2[i].getEndPoint().y, fourierPoint1.x, fourierPoint1.y);
                g2.drawLine(arms1[i].getEndPoint().x, arms1[i].getEndPoint().y, fourierPoint2.x, fourierPoint2.y);
                g2.drawLine(arms2[i].getEndPoint().x, arms2[i].getEndPoint().y, fourierPoint2.x, fourierPoint2.y);
            }
        }

        for (int i = 0; i < fourierPoints1.size(); i++) {
            g.setColor(Color.RED);
            g2.drawOval(fourierPoints1.get(i).x, fourierPoints1.get(i).y, 1, 1);
            g2.drawOval(fourierPoints2.get(i).x, fourierPoints2.get(i).y, 1, 1);
        }

        repaint();
    }

}