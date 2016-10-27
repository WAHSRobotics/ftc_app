package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class LSRobit {
    public DcMotor leftfront;
    public DcMotor rightfront;
    public DcMotor leftback;
    public DcMotor rightback;



    private HardwareMap hwMap;

    public void init(HardwareMap ahwMap){
     hwMap = ahwMap;

        leftfront = hwMap.dcMotor.get("lf");
        rightfront = hwMap.dcMotor.get("rf");
        leftback = hwMap.dcMotor.get("lb");
        rightback = hwMap.dcMotor.get("rb");
        leftfront.setDirection(DcMotor.Direction.FORWARD);
        rightfront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftback.setDirection(DcMotorSimple.Direction.FORWARD);
        rightback.setDirection(DcMotorSimple.Direction.REVERSE);


        leftfront.setPower(0);
        rightfront.setPower(0);
        leftback.setPower(0);
        rightback.setPower(0);

        leftfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftback.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightback.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);








    }














}
