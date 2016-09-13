package org.firstinspires.ftc.teamcode;

public class OutputTesting {
    public static final double POWER_MIN = 0.05;
    public static final double POWER_MAX = 1.0;
    public static final double POWER_INTERVAL = 0.05;

    public static void main(String[] args) {
        for(double i = 0.0; i < 1.1; i += 0.01) {
            System.out.println(scalePower(i));
        }
    }

    public static double scalePower(double power) {
        double scaledPower;

        if(POWER_MIN <= power && power <= POWER_MAX) {
            scaledPower = ((int) (power / POWER_INTERVAL)) * POWER_INTERVAL;

            scaledPower = Math.round(scaledPower * 10000.0) / 10000.0;;
        } else {
            scaledPower = 0.0;
        }

        return scaledPower;
    }
}
