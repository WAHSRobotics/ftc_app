package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.util.MathUtil;
import org.firstinspires.ftc.teamcode.util.PowerScale;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import static org.firstinspires.ftc.teamcode.util.MathUtil.setSignificantPlaces;

public class OutputTesting {
    public static void main(String[] args) {
        PowerScale powerScale = new PowerScale();

//        for(double i = -1.1; i < 1.1; i += 0.01) {
//            System.out.println(powerScale.scalePower(i));
//        }

        double power = 1.0;

        double radius = sqrt(pow(power, 2) + pow(power, 2));

        for(int i = 0; i < 360; i++) {
            double angle = toRadians(i);

            double x = radius * cos(angle);
            double y = radius * sin(angle);

            x = setSignificantPlaces(x, 2);
            y = setSignificantPlaces(y, 2);
            angle = toDegrees(angle);
            angle = setSignificantPlaces(angle, 2);

            System.out.println("Angle: " + angle + "   Point: (" + Range.clip(x, -1.0, 1.0) + ", " + Range.clip(y, -1.0, 1.0) + ")");
        }
    }
}
