package org.firstinspires.ftc.teamcode.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.teamcode.opmode.teleop.LSRobit;


//this is a work in progress
@Autonomous(name="Semi-Auto", group="Memestatus")

public class AutonomousRobitMeme extends LinearOpMode {


    LSRobit robot   = new LSRobit();
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.4;

    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);


        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();


        waitForStart();
        robot.driveByTime(0.5,0.0,0.0,9000);

        robot.driveByTime(0.0,0.0,0.5,0);


        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 22.0)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();



        }


        robot.leftfront.setPower(TURN_SPEED);
        robot.rightback.setPower(-TURN_SPEED);

        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }

        robot.leftback.setPower(-FORWARD_SPEED);
        robot.rightback.setPower(-FORWARD_SPEED);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 1.0)) {
            telemetry.addData("Path", "Leg 3: %2.5f S Elapsed", runtime.seconds());
            telemetry.update();
            idle();
        }

        robot.leftfront.setPower(0);
        robot.rightfront.setPower(0);



        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
        idle();
    }
}

