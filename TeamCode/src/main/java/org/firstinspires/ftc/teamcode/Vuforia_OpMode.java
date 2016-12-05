package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.RobotLog;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;
@Autonomous
public class Vuforia_OpMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor driveR = hardwareMap.dcMotor.get("");
        driveR.setDirection(DcMotorSimple.Direction.REVERSE);
        driveR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        DcMotor driveL = hardwareMap.dcMotor.get("driveL");
        driveL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AYrzM+7/////AAAAGflN33oLXURIiZiOHPt5MZA2iv50tePz4bz21btpbPci5G9i+R0v4r0iNxOOPL5mkqRO/EjcBv4TYHnKqEahIIt35JZdscPxAp0uHcpSONmWqRcFNglob05nEiqNkTAQKG7Ux9AhjJqZp6R+lAiCKB1/Ht9pNZ+qK+xNE1iEtL9g708JbjmdsqT+KYCA7Rup0dqdeMGieexgSQUKfWKIk3w/Sap1W83He60GW0UGnSUzM81fBu05Oqkl1QiAWbb9TpWff9/YfOJZPSCfdfErIMBuYtYgsJl5xZEtv57u6EwrqsrlwvudD1GciBrIIMmnqMeIQu9EM5PD0dI9Oi+3jn8RPEfKauoAGDRIpUlfoI+2";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 4);
        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_Underscore2016-2017");
        beacons.get(0).setName("Wheels");
        beacons.get(0).setName("Tools");
        beacons.get(0).setName("Dank");
        beacons.get(0).setName("Memes");

        waitForStart();

        VuforiaTrackableDefaultListener wheels = (VuforiaTrackableDefaultListener) beacons.get(0).getListener();
        beacons.activate();


        driveL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        driveL.setPower(0.2);
        driveR.setPower(0.2);

        while (opModeIsActive() && wheels.getRawPose() == null) {
            idle();
        }

        driveL.setPower(0);
        driveR.setPower(0);
//analyze beacon here


        VectorF angles = anglesFromTarget(wheels);
        VectorF trans = navOffWall(wheels.getPose().getTranslation(), Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));
        if (trans.get(0) > 0) {
            driveL.setPower(0.02);
            driveR.setPower(-0.02);
        } else {

            driveL.setPower(-0.02);
            driveR.setPower(0.02);
        }

        do {
            if (wheels.getPose() != null) {
                trans = navOffWall(wheels.getPose().getTranslation(), Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));
            }
            idle();
        } while (opModeIsActive() && Math.abs(trans.get(0)) > 30);

        driveL.setPower(0);
        driveR.setPower(0);

        driveL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        driveR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        driveL.setTargetPosition((int) (driveL.getCurrentPosition() + (Math.hypot(trans.get(0), trans.get(2) + 150 / 409.252 * 560))));
        driveR.setTargetPosition((int) (driveR.getCurrentPosition() + (Math.hypot(trans.get(0), trans.get(2) + 150 / 409.252 * 560))));
        driveL.setPower(0.3);
        driveR.setPower(0.3);

        while (opModeIsActive() && driveL.isBusy() && driveR.isBusy()) {


            idle();
        }
        driveL.setPower(0);
        driveR.setPower(0);

        driveL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while(opModeIsActive() && (wheels.getPose() == null || Math.abs(wheels.getPose().getTranslation().get(0)) >10)){

        if(wheels !=null){

        if (wheels.getPose().getTranslation().get(0) >0){
            driveL.setPower(-0.3);
            driveR.setPower(0.3);

        }
        }   else{


            driveL.setPower(-0.3);
            driveR.setPower(0);
        }




        }




    }
    public VectorF navOffWall(VectorF trans, double robotAngle, VectorF offWall){ return new VectorF((float) (trans.get(0) - offWall.get(0) * Math.sin(Math.toRadians(robotAngle)) - offWall.get(2) * Math.cos(Math.toRadians(robotAngle))), trans.get(1), (float) (trans.get(2) + offWall.get(0) * Math.cos(Math.toRadians(robotAngle)) - offWall.get(2) * Math.sin(Math.toRadians(robotAngle))));
    }

    public VectorF anglesFromTarget(VuforiaTrackableDefaultListener image){ float [] data = image.getRawPose().getData(); float [] [] rotation = {{data[0], data[1]}, {data[4], data[5], data[6]}, {data[8], data[9], data[10]}}; double thetaX = Math.atan2(rotation[2][1], rotation[2][2]); double thetaY = Math.atan2(-rotation[2][0], Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2])); double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]); return new VectorF((float)thetaX, (float)thetaY, (float)thetaZ);
    }
}

//memes