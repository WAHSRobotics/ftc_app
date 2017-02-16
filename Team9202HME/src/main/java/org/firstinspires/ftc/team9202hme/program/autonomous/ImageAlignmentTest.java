package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.team9202hme.R;
import org.firstinspires.ftc.team9202hme.audio.Sound;
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

        Sound sound = new Sound();
        sound.load(opMode.hardwareMap, R.raw.you_took_the_peepo);
        sound.setVolume(1.0f, 1.0f);

        final ImageTarget TARGET = ImageTarget.GEARS;

        double angleScale;
        double moveScale;

        final double MOVE_RANGE = 20;
        final double DISTANCE_RANGE = 40;
        final double ANGLE_RANGE = 3;

        final double MOVE_SPEED = 0.15;
        final double TURN_SPEED = 0.1;

        final double MIN = 0.2;


        double movePower = 0, turnPower = 0, angle = 0;
        int state = 0;

        while(opMode.opModeIsActive()) {
            boolean canSeeTarget = navigator.canSeeTarget(TARGET);

            Vector3 rotation = navigator.getRelativeTargetRotation(TARGET);
            Vector3 translation = navigator.getRelativeTargetTranslation(TARGET);
            translation.z = abs(translation.z);

            if(canSeeTarget) {
                opMode.telemetry.addData("Rotation", rotation);
                opMode.telemetry.addData("Translation", translation);
            } else {
                opMode.telemetry.addLine("Image is not visible");
            }

            opMode.telemetry.addData("case", state);
            opMode.telemetry.update();

            //State 0: Align and center on image
            //State 1: Get close enough to the image for the color sensor to do its stuff
            //State 2: Stop the program (later this will be for color sensor)

            angleScale = (MIN + (7/900) * rotation.y);
            moveScale = ((Math.sqrt(Math.abs(translation.x))/(25)));

            if (moveScale > 1.0){
                moveScale = 1;
            } else if (moveScale < -1.0){
                moveScale = -1.0;
            }


            if(canSeeTarget) {
                switch(state) {
                    case 0:
                        if(rotation.y > ANGLE_RANGE) {
                            movePower = angleScale;
                            angle = 90;
                        } else if(rotation.y < -ANGLE_RANGE) {
                            movePower = angleScale;
                            angle = 270;
                        } else {
                            movePower = 0;
                        }

                        if(translation.x > MOVE_RANGE) {
                            turnPower = movePower;
                        } else if(translation.x < -MOVE_RANGE) {
                            turnPower = -movePower;
                        } else {
                            turnPower = 0;
                        }

                        if((translation.x < MOVE_RANGE && translation.x > -MOVE_RANGE) && (rotation.y < ANGLE_RANGE && rotation.y > -ANGLE_RANGE)) {
                            driveTrain.stop();
//                            state++;
                        }

                        driveTrain.moveAndTurn(movePower, angle, turnPower);
                        break;
//                    case 1:
//                        if(translation.z > DISTANCE_RANGE + MOVE_RANGE) {
//                            movePower = MOVE_SPEED;
//                            angle = 180;
//                        } else if(translation.z < DISTANCE_RANGE - MOVE_RANGE) {
//                            movePower = MOVE_SPEED;
//                            angle = 0;
//                        }
//
//                        if(rotation.y > ANGLE_RANGE) {
//                            turnPower = 0.1;
//                        } else if(rotation.y < -ANGLE_RANGE) {
//                            turnPower = -0.1;
//                        }
//
//                        if((translation.z < 130) && /*(translation.z < DISTANCE_RANGE - MOVE_RANGE)
//                                && */(rotation.y < ANGLE_RANGE && rotation.y > -ANGLE_RANGE)) {
//                            driveTrain.stop();
//                            state++;
//                        }
//
//                        driveTrain.moveAndTurn(movePower, angle, turnPower);
//                        break;
//                    case 2:
//
//                        if (translation.x > -20){
//                            driveTrain.move(movePower, 90);
//                        } else  if (translation.x < 20){
//                            driveTrain.move(movePower, 270);
//                        } else {
//                            sound.play();
//                            driveTrain.stop();
//                            opMode.requestOpModeStop();
//                        }
//                        break;
                }
            }
        }
    }
}
