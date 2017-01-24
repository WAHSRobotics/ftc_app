package org.firstinspires.ftc.teamcode.opmode.teleop;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;

@TeleOp(name = "LSTeleOp")
public class LsTeleop extends OpMode {
    private LSRobit robot = new LSRobit();
    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.leftfront.setPower(0.0);
        robot.rightfront.setPower(0.0);
        robot.leftback.setPower(0.0);
        robot.rightback.setPower(0.0);
        robot.Spin1.setPower(0.0);
        robot.Spin2.setPower(0.0);
        robot.Spin3.setPower(0.0);
    }



    public double scale(double inPower) {
        float scaledPower = 0.0f;

        inPower = Range.clip(inPower, -1, 1);
        float[] possiblePowerValues = {
                0.00f, 0.05f, 0.09f, 0.10f, 0.12f,
                0.15f, 0.18f, 0.24f, 0.30f, 0.36f,
                0.43f, 0.50f, 0.60f, 0.72f, 0.85f,
                1.00f, 1.00f

        };

        int powerIndex = (int)(inPower * 16.0);

        if (powerIndex < 0) {
            powerIndex = -powerIndex;
        } else if (powerIndex > 16) {
            powerIndex = 16;
        }if (inPower < 0) {
            scaledPower = -possiblePowerValues[powerIndex];
        } else {
            scaledPower = possiblePowerValues[powerIndex];
        }
        return scaledPower;
    }
    public void loop() {
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double z = gamepad1.right_stick_x;

// if (gamepad1.right_bumper) {
//            robot.Spin1.setPower(1.0);
//        } else if (gamepad1.b){
//            robot.Spin1.setPower(-1);
//        }else
//        {
//        robot.Spin1.setPower(0.0);
//        }
//
//        if (gamepad1.a ){
//            robot.Spin2.setPower(-1.0);
//        }else if(gamepad1.left_bumper){
//                robot.Spin2.setPower(1);
//        }
//        else{
//            robot.Spin2.setPower(0.0);
//        }
//
//        if(gamepad1.left_trigger > 0){
//            robot.Spin3.setPower(gamepad1.left_trigger);
//        } else if (gamepad1.right_trigger > 0){
//
//            robot.Spin3.setPower(-gamepad1.right_trigger);
//
//        }

        robot.rightfront.setPower(scale(+ y - x + z));
        robot.leftfront.setPower(scale(- y - x + z));
        robot.rightback.setPower(scale(+ y + x + z));
        robot.leftback.setPower(scale(- y + x + z));
    }
}
