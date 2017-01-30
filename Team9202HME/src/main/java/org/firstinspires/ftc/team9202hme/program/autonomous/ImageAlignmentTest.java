package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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
        Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.CHARGER_SIDE_UP, 1, true);

        driveTrain.init(opMode.hardwareMap);
        navigator.init();

        opMode.waitForStart();

        final ImageTarget target = ImageTarget.GEARS;

        final double RANGE = 30;
        final double ANGLE_RANGE = 5;

        final double MOVE_SPEED = 0.3;
        final double TURN_SPEED = 0.2;

        double movePower = 0, turnPower = 0, angle = 0;

        while(opMode.opModeIsActive()) {
            Vector3 rotation = navigator.getRelativeTargetRotation(target);
            Vector3 translation = navigator.getRelativeTargetTranslation(target);

            opMode.telemetry.addData("Rotation", rotation);
            opMode.telemetry.addData("Translation", translation);

            opMode.telemetry.update();
            
            if(rotation.y > ANGLE_RANGE) {
                movePower = MOVE_SPEED;
                angle = 270;
            } else if(rotation.y < -ANGLE_RANGE) {
                movePower = MOVE_SPEED;
                angle = 90;
            }

            if(translation.x > RANGE) {
                turnPower = TURN_SPEED;
            } else if(translation.x < -RANGE){
                turnPower = -TURN_SPEED;
            }

            if((translation.x < RANGE && translation.x > -RANGE) && (rotation.y < ANGLE_RANGE && rotation.y > -ANGLE_RANGE)) {
                movePower = 0;
                angle = 0;
                turnPower = 0;
            }

            driveTrain.moveAndTurn(movePower, angle, turnPower);
        }
    }
}
