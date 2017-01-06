package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.team9202hme.R;
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

        final double SPEED = 0.4;
        final double TURN_SPEED = 0.5;
        final double RANGE = 40; //Acceptable range of error, in millimeters, when centering and approaching image
        final double DISTANCE = 250; //Desired distance away from image, in millimeters
        final double ANGLE_RANGE = 3; //Acceptable range of error, in degrees, when aligning with image
        final ImageTarget target = ImageTarget.GEARS;

        double movePower = 0, moveAngle = 0, turnPower = 0;

        int state = 0;

        driveTrain.move(SPEED, 90, 1000);
        /**
         * 0 == Center robot on image and simultaneously align with wall
         */

//        while(opMode.opModeIsActive()) {
//            if(navigator.canSeeTarget(target)) {
//                opMode.telemetry.addData("Image Status", "Visible");
//
//                Vector3 translation = navigator.getRelativeTargetTranslation(target); //Image location in 3d space, relative to phone camera
//
//                translation.z = Math.abs(translation.z); //Because of right-hand rule, Z axis is inverted, so I'm reverting it for ease of use
//
//                Vector3 rotation = navigator.getRelativeTargetRotation(target); //Image rotation in 3d space, relative to phone camera
//
//                opMode.telemetry.addData("Translation", translation);
//                opMode.telemetry.addData("Rotation", rotation);
//
//                opMode.telemetry.addData("Angle", Math.toDegrees(Math.atan2(translation.z, translation.x)) - 90); //Angle the image is at relative to the robot (NOT ROTATION!)
//                opMode.telemetry.update();
//
//                switch(state) {
//                    case 0:
//                        if(Math.abs(translation.x) > RANGE) {
//                            movePower = SPEED;
//                            moveAngle = 90 - rotation.y;
//                        } else {
//                            movePower = 0;
//                            moveAngle = 0;
//                        }
//
//                        if(rotation.y > ANGLE_RANGE) {
//                            turnPower = TURN_SPEED;
//                        } else if(rotation.y < -ANGLE_RANGE) {
//                            turnPower = -TURN_SPEED;
//                        } else {
//                            turnPower = 0;
//                        }
//
//                        driveTrain.moveAndTurn(movePower, moveAngle + 90, turnPower);
//                        break;
//                }
//            } else {
//                driveTrain.stop();
//
//                opMode.telemetry.addData("Image Status", "Not Visible");
//                opMode.telemetry.update();
//
//                Thread.sleep(10);
//            }
//        }
    }
}
