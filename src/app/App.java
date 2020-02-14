package app;

public class App {

    public static void main(String[] args) throws Exception {
        
        int cumArmLength = 200;
        int padding = 15;
        int yOffset = 25;
        int width = 4 * cumArmLength + 4 * padding;
        int height = 4 * cumArmLength + 4 * padding;
        Point startPoint1 = new Point(padding + cumArmLength, (int) (0.5*height) + cumArmLength);
        Point startPoint2 = new Point((int) (0.5*width) + cumArmLength, padding + cumArmLength);

        int numberOfArms = 3;
        int[] armLengths1 = partitionRandomly(cumArmLength, numberOfArms);
        int[] armLengths2 = partitionRandomly(cumArmLength, numberOfArms);

        Arm[] arms1 = new Arm[numberOfArms];
        Arm[] arms2 = new Arm[numberOfArms];

        for (int i = 0; i < numberOfArms; i++) {

            Point point1;
            Point point2;

            if (i == 0) {
                point1 = startPoint1;
                point2 = startPoint2;
            } else {
                point1 = arms1[i-1].getEndPoint();
                point2 = arms2[i-1].getEndPoint();
            }

            arms1[i] = new Arm(point1, armLengths1[i], Math.PI, 0.01 * (numberOfArms - i));
            arms2[i] = new Arm(point2, armLengths2[i], Math.PI, 0.01 * (numberOfArms - i));
        }

        FourierSketch fs = new FourierSketch(arms1, arms2, width, height, padding, yOffset);
        fs.showSketchWindow();
        
    }

    public static int[] partitionRandomly(int number, int parts) {

        int[] partition = new int[parts];
        double sum = 0;

        for (int i = 0; i < parts; i++) {
            partition[i] = (int) (Math.random() * number);
            sum += partition[i];
        }

        double ratio = sum/number;

        for (int i = 0; i < parts; i++) {
            partition[i] = (int) ((double) (partition[i])/ratio);
        }

        return partition;
    }
}