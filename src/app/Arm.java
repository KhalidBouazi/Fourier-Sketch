package app;

import java.awt.*;

public class Arm {

    private Point startPoint;
    private double length;
    private double angle;
    private double angularSpeed;
    private Arm parentArm;

    public Arm(Point startPoint, double length, double angle, double angularSpeed) {
        this.startPoint = startPoint;
        this.length = length;
        this.angle = angle;
        this.angularSpeed = angularSpeed;
        parentArm = null;
    }

    public Point getEndPoint() {
        int x = startPoint.x + (int) (Math.cos(angle) * length),
            y = startPoint.y + (int) (Math.sin(angle) * length);

        return new Point(x, y);
    }

    public double getLength() {
        return length;
    }

    public double getAngle() {
        return angle;
    }

    public Arm getParentArm() {
        return parentArm;
    }

    public void turn() {
        angle += angularSpeed;
    }

    public void setParentArm(Arm parentArm) {
        this.parentArm = parentArm;
    }

    public void drawArm(Graphics2D g) {
        turn();

        Arm parent = parentArm;
        if (parent != null) {
            startPoint = parent.getEndPoint();
        }

        Point endPoint = getEndPoint();

        g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }
}