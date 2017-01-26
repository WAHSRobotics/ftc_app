package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.math.vector.Vector3;
import org.firstinspires.ftc.team9202hme.navigation.ImageTarget;
import org.firstinspires.ftc.team9202hme.navigation.Navigator;
import org.firstinspires.ftc.team9202hme.navigation.CameraSide;
import org.firstinspires.ftc.team9202hme.navigation.PhoneOrientation;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;

import static java.lang.Math.*;

public class ImageAlignmentTest extends AutonomousProgram {
    public ImageAlignmentTest(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide);
    }

    @Override
    public void run() throws InterruptedException {
        HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, 1120);
        Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.CHARGER_SIDE_UP, 1, false);

        driveTrain.init(opMode.hardwareMap);
        navigator.init();

        final ImageTarget target = ImageTarget.GEARS;

        opMode.waitForStart();

        final double RANGE = 20;
        final double SPEED = 0.2;

        while(opMode.opModeIsActive()) {
            Vector3 rotation = navigator.getRelativeTargetRotation(target);
            Vector3 translation = navigator.getRelativeTargetTranslation(target);

            opMode.telemetry.addData("Rotation", rotation);
            opMode.telemetry.addData("Translation", translation);
            opMode.telemetry.update();

//            if(rotation.y > RANGE) {
//                driveTrain.turn(SPEED);
//            } else if(rotation.y < -RANGE) {
//                driveTrain.turn(-SPEED);
//            } else {
//                driveTrain.stop();
//            }

            if(translation.x > RANGE){
                driveTrain.move(SPEED, 90);
            } else if(translation.x < -RANGE){
                driveTrain.move(SPEED, 270);
            } else {
                driveTrain.stop();
            }
        }
    }
}
