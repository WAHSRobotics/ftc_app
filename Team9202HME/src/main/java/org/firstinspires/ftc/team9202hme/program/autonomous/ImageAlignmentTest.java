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

        final Vector3[] rotation = new Vector3[1];
        final Vector3[] translation = new Vector3[1];

        rotation[0] = new Vector3();
        translation[0] = new Vector3();

        driveTrain.init(opMode.hardwareMap);

        opMode.waitForStart();

        final double RANGE = 30;
        final double ANGLE_RANGE = 5;

        final double SPEED = 0.2;

        double movePower = 0, turnPower = 0, angle = 0;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final ImageTarget target = ImageTarget.GEARS;   //remember to change this dumb ass

                final Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.CHARGER_SIDE_UP, 1, true);
                navigator.init();

                while(opMode.opModeIsActive()) {
                    rotation[0] = navigator.getRelativeTargetRotation(target);
                    translation[0] = navigator.getRelativeTargetTranslation(target);

                    opMode.telemetry.addData("Rotation", rotation[0]);
                    opMode.telemetry.addData("Translation", translation[0]);

                    opMode.telemetry.update();
                }
            }
        });

        thread.start();

        while(opMode.opModeIsActive()) {
            if(rotation[0].y > ANGLE_RANGE) {
                movePower = SPEED;
                angle = 270;
            } else if(rotation[0].y < -ANGLE_RANGE) {
                movePower = SPEED;
                angle = 90;
            }

            if(translation[0].x > RANGE) {
                turnPower = SPEED;
            } else if(translation[0].x < -RANGE){
                turnPower = -SPEED;
            }

            if((translation[0].x < RANGE && translation[0].x > -RANGE) && (rotation[0].y < ANGLE_RANGE && rotation[0].y > -ANGLE_RANGE)) {
                movePower = 0;
                angle = 0;
                turnPower = 0;
            }

            driveTrain.moveAndTurn(movePower, angle, turnPower);
        }

        thread.join();
    }
}
