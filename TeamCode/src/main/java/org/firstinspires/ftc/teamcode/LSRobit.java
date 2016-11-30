package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

    public class LSRobit {
        public DcMotor leftFront;
        public DcMotor rightFront;
        public DcMotor leftBack;
        public DcMotor rightBack;
        public Servo buttonPusher;

    private HardwareMap hwMap;

    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;

        leftFront = hwMap.dcMotor.get("lf");
        rightFront = hwMap.dcMotor.get("rf");
        leftBack = hwMap.dcMotor.get("lb");
        rightBack = hwMap.dcMotor.get("rb");
        buttonPusher = hwMap.servo.get("buttonPusher");

        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
