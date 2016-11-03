package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;





public class MemeTeleop extends OpMode {




    private LSRobit robot = new LSRobit();

    @Override
    public void init() {

        robot.rightfront.setPower((y+x-z));
        robot.leftfront.setPower(sca(y-x+z));
        robot.rightback.setPower(scale(y-x-z));
        robot.leftback.setPower(scale(y+x+z));











    }




    public void loop(){



}





























}
