package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
<<<<<<< HEAD
/** Created By The Very Dank Meme Lord Shaun "Dank Indian" Sarker
 * I like Memes
 * Do You Like Memes?
 * NO!!!
 */
=======


>>>>>>> origin/ls_2016_SarkerShaun

    public class LSRobit {
    public DcMotor leftfront;
    public DcMotor rightfront;
    public DcMotor leftback;
    public DcMotor rightback;
//    public Servo Arm1;
//    public Servo Arm2;
//    public Servo Arm3;
//    public Servo Arm4;
//    public Servo Arm5;
//    public Servo Arm6;



    private HardwareMap hwMap;

    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;
//        Arm1 = hwMap.servo.get("a1");
//        Arm2 = hwMap.servo.get("a2");
//        Arm3 =hwMap.servo.get("a3");
//        Arm4 = hwMap.servo.get("a4");
//        Arm5 = hwMap.servo.get("a5");
//        Arm6 = hwMap.servo.get("a6");
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
