package org.firstinspires.ftc.teamcode.opmode.teleop;

        import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.GyroSensor;
        import com.qualcomm.robotcore.hardware.HardwareMap;
        import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
        import com.qualcomm.robotcore.util.ElapsedTime;
        import com.qualcomm.robotcore.util.Range;
public class GGRobit {
    //what a meme
    public DcMotor leftfront;
    public DcMotor rightfront;
    public DcMotor leftback;
    public DcMotor rightback;



    //memes
    public void init(HardwareMap hwMap){
        leftfront = hwMap.dcMotor.get("red");
        rightfront = hwMap.dcMotor.get("green");
        leftback = hwMap.dcMotor.get("blue");
        rightback = hwMap.dcMotor.get("black");

        leftfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftback.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightback.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    private double scalePower(double power) {
        double scaledPower = 0.0f;

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
