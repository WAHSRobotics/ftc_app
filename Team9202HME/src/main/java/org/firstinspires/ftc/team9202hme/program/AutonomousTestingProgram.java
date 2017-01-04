package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.hardware.HardwareConstants;
import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.math.vector.Vector3;
import org.firstinspires.ftc.team9202hme.navigation.ImageTarget;
import org.firstinspires.ftc.team9202hme.navigation.Navigator;
import org.firstinspires.ftc.team9202hme.navigation.CameraSide;
import org.firstinspires.ftc.team9202hme.navigation.PhoneOrientation;

public class AutonomousTestingProgram extends AutonomousProgram {
    public AutonomousTestingProgram(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide,
                new HolonomicDriveTrain(HardwareConstants.MM_OMNI_WHEEL_DIAMETER, HardwareConstants.ANDYMARK_ENCODER_TICKS_PER_ROTATION),
                null,
                null);
    }

    @Override
    public void run() throws InterruptedException {
        Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.CHARGER_SIDE_UP, 1, false);

        driveTrain.init(opMode.hardwareMap);
        navigator.init();

        opMode.waitForStart();

        final double SPEED = 0.2;
        final double RANGE = 40; //Acceptable range of error, in millimeters, when centering and approaching image
        final double DISTANCE = 250; //Desired distance away from image, in millimeters
        final double ANGLE_RANGE = 3; //Acceptable range of error, in degrees, when aligning with image
        final ImageTarget target = ImageTarget.GEARS;

        while(opMode.opModeIsActive()) {
            if(navigator.canSeeTarget(target)) {
                opMode.telemetry.addData("Image Status", "Visible");

                Vector3 translation = navigator.getRelativeTargetTranslation(target); //Image location in 3d space, relative to phone camera

                translation.z = Math.abs(translation.z); //Because of right-hand rule, Z axis is inverted, so I'm reverting it for ease of use

                Vector3 rotation = navigator.getRelativeTargetRotation(target); //Image rotation in 3d space, relative to phone camera

                opMode.telemetry.addData("Translation", translation);
                opMode.telemetry.addData("Rotation", rotation);

                double angle = Math.toDegrees(Math.atan2(translation.z, translation.x)) - 90; //Angle the image is at relative to the robot (NOT ROTATION!)

                opMode.telemetry.addData("Angle", angle);
                opMode.telemetry.update();

                if(translation.z > DISTANCE + RANGE) { //Too far away
                    driveTrain.move(SPEED, 180);

                    Thread.sleep(1);
                } else if(translation.z < DISTANCE - RANGE) { //Too close
                    driveTrain.move(SPEED, 0);

                    Thread.sleep(1);
                } else { //Within range
                    driveTrain.stop();
                    
                    Thread.sleep(1);
                }
            } else {
                driveTrain.stop();

                opMode.telemetry.addData("Image Status", "Not Visible");
                opMode.telemetry.update();

                Thread.sleep(10);
            }
        }
    }
}
