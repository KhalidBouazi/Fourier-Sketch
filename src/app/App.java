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

        double[] xSeries = {5,4.6,1.3,1.2,1.3,1.2,1.1,0.5,1.3,1.8,3.2,5.7,7,6.6,7.2};
        double[] ySeries = {0.8,1.7,1,2,2.2,2.4,3.8,4,5.9,7.3,8.4,8.2,6.9,4.4,1.3};

        ArmProp[] armProps1 = getArmPropsFromTimeSeries(xSeries, ySeries, cumArmLength);
        ArmProp[] armProps2 = getArmPropsFromTimeSeries(xSeries, ySeries, cumArmLength);

        int numberOfArms = xSeries.length;
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

            arms1[i] = new Arm(point1, armProps1[i].abs, armProps1[i].angle, 2*Math.PI/(numberOfArms)*i);
            arms2[i] = new Arm(point2, armProps2[i].abs, armProps2[i].angle, 2*Math.PI/(numberOfArms)*i);
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

    public static ArmProp[] getArmPropsFromTimeSeries(double[] xSeries, double[] ySeries, int cumArmLength) {

        int samples = xSeries.length;

        ArmProp[] armProps = new ArmProp[samples];

        for (int i = 0; i < samples; i++) {
            armProps[i] = getArmPropFromTimeSeries(xSeries, ySeries, samples, i);
        }

        double absSum = 0;
        for (int i = 0; i < samples; i++) {
            absSum += armProps[i].abs;
        }

        double ratio = absSum/cumArmLength;

        for (int i = 0; i < samples; i++) {
            armProps[i].abs /= ratio;
        }

        return armProps;
    }

    public static ArmProp getArmPropFromTimeSeries(double[] xSeries, double[] ySeries, int samples, int armIdx) {

        double realCoefficient = 0;
        double imagCoefficient = 0;

        for (int i = 0; i < samples; i++) {

            double exponent = -armIdx*2*Math.PI/samples*i;
            double real = Math.exp(0) * Math.cos(exponent);
            double imag = Math.exp(0) * Math.sin(exponent);

            realCoefficient += 10*xSeries[i]*real - 10*(-1)*ySeries[i]*imag;
            imagCoefficient += 10*xSeries[i]*imag + 10*(-1)*ySeries[i]*real;
        }

        realCoefficient /= samples;
        imagCoefficient /= samples;

        double abs = Math.sqrt(Math.pow(realCoefficient, 2) + Math.pow(imagCoefficient, 2));
        double angle = Math.atan2(imagCoefficient, realCoefficient);

        ArmProp prop = new ArmProp(abs, angle);

        return prop; 
    }

    
}

class ArmProp {

    public double abs;
    public double angle;

    public ArmProp(double abs, double angle) {
        this.abs = abs;
        this.angle = angle;
    }
}