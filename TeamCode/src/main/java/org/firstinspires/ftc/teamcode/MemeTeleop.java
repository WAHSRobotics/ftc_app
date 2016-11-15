package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;
import java.math.BigInteger;

public class MemeTeleop extends OpMode {

    private LSRobit robot = new LSRobit();

        @Override
        public void init() {
        double y = gamepad1.right_stick_y;
        double x = gamepad1.right_stick_x;
        double z = 0.0;
        boolean leftbumper = true;
        boolean rightbumper = true;
        double leftBump = gamepad1.left_trigger;
        double rightBump = gamepad1.right_trigger;

        if (leftBump > 0.0) {
            z = leftBump;
        }

        else if (rightBump > 0.0) {
            z = -rightBump;
        }

        while (leftbumper = true) {
            leftbumper = false;
        }



          while (rightbumper = false){
               rightbumper = true;
                  }

        robot.rightfront.setPower(scale(y+x-z));
        robot.leftfront.setPower(scale(y-x+z));
        robot.rightback.setPower(scale(y-x-z));
        robot.leftback.setPower(scale(y+x+z));
    }
//make america great again
    /*
     Method for making it easier to make the robot run slowly
     */

    public double scale(double inPower) {
        return inPower; //todo: make a real scale method
    }

    public void loop() {

    }

}
