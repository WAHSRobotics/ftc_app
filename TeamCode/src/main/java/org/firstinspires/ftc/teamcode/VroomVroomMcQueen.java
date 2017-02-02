package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by cjlr0 on 2/1/2017.
 */
@TeleOp
public class VroomVroomMcQueen extends OpMode {
    public DcMotor Kachowleft;
    public DcMotor Kachowright;

    @Override
    public void init() {
        Kachowleft = hardwareMap.dcMotor.get("Kleft");
        Kachowright = hardwareMap.dcMotor.get("Kright");
        Kachowleft.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double L = -gamepad1.left_stick_y;
        double R = -gamepad1.right_stick_y;

        Kachowleft.setPower(L);
        Kachowright.setPower(R);
    }
}
