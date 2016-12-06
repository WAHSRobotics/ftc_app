package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/** Created By The Very Dank Meme Lord Shaun "Dank Indian" Sarker
 * I like Memes
 * Do You Like Memes?
 * NO!!!
 */

public class LSRobit {

    public DcMotor leftfront;
    public DcMotor rightfront;
    public DcMotor leftback;
    public DcMotor rightback;
    public Servo arm;

    public static final String VUFORIA_KEY = "AYrzM+7/////AAAAGflN33oLXURIiZiOHPt5MZA2iv50tePz4bz21btpbPci5G9i+R0v4r0iNxOOPL5mkqRO/EjcBv4TYHnKqEahIIt35JZdscPxAp0uHcpSONmWqRcFNglob05nEiqNkTAQKG7Ux9AhjJqZp6R+lAiCKB1/Ht9pNZ+qK+xNE1iEtL9g708JbjmdsqT+KYCA7Rup0dqdeMGieexgSQUKfWKIk3w/Sap1W83He60GW0UGnSUzM81fBu05Oqkl1QiAWbb9TpWff9/YfOJZPSCfdfErIMBuYtYgsJl5xZEtv57u6EwrqsrlwvudD1GciBrIIMmnqMeIQu9EM5PD0dI9Oi+3jn8RPEfKauoAGDRIpUlfoI+2";

    private HardwareMap hwMap;

    public void init(HardwareMap aHwMap){
        hwMap = aHwMap;
        arm = hwMap.servo.get("arm");
        leftfront = hwMap.dcMotor.get("lf");
        rightfront = hwMap.dcMotor.get("rf");
        leftback = hwMap.dcMotor.get("lb");
        rightback = hwMap.dcMotor.get("rb");

        leftfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftback.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightback.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private float scalePower(float power) {
        float scaledPower = 0.0f;

        power = Range.clip(power, -1, 1);
        float[] possiblePowerValues = {
                0.00f, 0.05f, 0.09f, 0.10f, 0.12f,
                0.15f, 0.18f, 0.24f, 0.30f, 0.36f,
                0.43f, 0.50f, 0.60f, 0.72f, 0.85f,
                1.00f, 1.00f
        };

        int powerIndex = (int)(power * 16.0);

        if (powerIndex < 0) {
            powerIndex = -powerIndex;
        } else if (powerIndex > 16) {
            powerIndex = 16;
        }

        if (power < 0) {
            scaledPower = -possiblePowerValues[powerIndex];
        } else {
            scaledPower = possiblePowerValues[powerIndex];
        }

        return scaledPower;
    }
}
