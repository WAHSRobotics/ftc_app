package org.firstinspires.ftc.team9202hme.opmode.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.HINT;
import com.vuforia.Vuforia;

import static java.lang.Math.*;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.team9202hme.R;
import org.firstinspires.ftc.team9202hme.hardware.HardwareConstants;
import org.firstinspires.ftc.team9202hme.hardware.driving.DriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;

@Autonomous(name = "ImageAlignmentTest (DELETE THIS LATER)")
public class ImageAlignmentTest extends LinearOpMode { //TODO: Delete this
    @Override
    public void runOpMode() throws InterruptedException {
        HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION);
        driveTrain.init(hardwareMap);

        // Make camera view show on screen of "robot controller" Maybe video guy means drivers station?
        // leave constructor blank if you're not in diagnostic mode so you don't use up battery
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        params.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        params.vuforiaLicenseKey = "AYrzM+7/////AAAAGflN33oLXURIiZiOHPt5MZA2iv50tePz4bz21" +
                "btpbPci5G9i+R0v4r0iNxOOPL5mkqRO/EjcBv4TYHnKqEahIIt35JZdsc" +
                "PxAp0uHcpSONmWqRcFNglob05nEiqNkTAQKG7Ux9AhjJqZp6R+lAiCKB1" +
                "/Ht9pNZ+qK+xNE1iEtL9g708JbjmdsqT+KYCA7Rup0dqdeMGieexgSQUK" +
                "fWKIk3w/Sap1W83He60GW0UGnSUzM81fBu05Oqkl1QiAWbb9TpWff9/Yf" +
                "OJZPSCfdfErIMBuYtYgsJl5xZEtv57u6EwrqsrlwvudD1GciBrIIMmnqM" +
                "eIQu9EM5PD0dI9Oi+3jn8RPEfKauoAGDRIpUlfoI+2";
        params.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.TEAPOT;

        // instantiate Vuforia
        VuforiaLocalizer vuforia = ClassFactory.createVuforiaLocalizer(params);
        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, 1);

        // Load Beacon images (provided by FTC)
        VuforiaTrackables beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");
        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Legos");
        beacons.get(3).setName("Gears");

        waitForStart();

        beacons.activate();

        double x = 0;

        while(opModeIsActive()) {
            VuforiaTrackable beacon = beacons.get(2);

            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) beacon.getListener()).getPose();

            if(pose != null) {
                VectorF translation = pose.getTranslation();

                telemetry.addData(beacon.getName() + "-Translation", translation);

                // return degrees your robot must turn to be facing the beacon
                // 1,2 is for vertically oriented phone (1 = y axis, 2 = x axis)
                // 0,2 is for landscape oriented phone
                double degreesToTurnYZ = Math.toDegrees(Math.atan2(translation.get(1), translation.get(2)));
                double degreesToTurnXY = Math.toDegrees(Math.atan2(translation.get(0), translation.get(1)));
                double degreesToTurnZX = Math.toDegrees(Math.atan2(translation.get(2), translation.get(0)));

                telemetry.addData(beacon.getName() + "-Degrees YZ", degreesToTurnYZ);
                telemetry.addData(beacon.getName() + "-Degrees XY", degreesToTurnXY);
                telemetry.addData(beacon.getName() + "-Degrees XZ", degreesToTurnZX);

                x = translation.get(0);
            }

            telemetry.update();

            final double SPEED = 0.3;
            final double RANGE = 15;

            if(abs(x) <= RANGE) {
                driveTrain.stop();
                Thread.sleep(1);
            } else if(x > RANGE) {
                driveTrain.moveIndefinitely(SPEED, 270);
                Thread.sleep(1);
            } else if(x < RANGE) {
                driveTrain.moveIndefinitely(SPEED, 90);
                Thread.sleep(1);
            }
        }
    }
}
