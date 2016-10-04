package org.firstinspires.ftc.teamcode.util;


public class MathUtil {
    public static double setSignificantPlaces(double val, int sigPlaces) {
        double multiplier = Math.pow(10, sigPlaces);
        return Math.round(val * multiplier) / multiplier;
    }
}
