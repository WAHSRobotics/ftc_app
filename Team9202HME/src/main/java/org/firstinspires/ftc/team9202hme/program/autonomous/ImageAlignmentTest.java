package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

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
        Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.CHARGER_SIDE_UP, 1, false);
        ColorSensor colorSensor;
        TouchSensor touchSensor;

        driveTrain.init(opMode.hardwareMap);
        navigator.init();
        colorSensor = opMode.hardwareMap.colorSensor.get("color");
        colorSensor.enableLed(false);
        touchSensor = opMode.hardwareMap.touchSensor.get("touch");

        opMode.waitForStart();

        Sound sound = new Sound();
        sound.load(opMode.hardwareMap, R.raw.you_took_the_peepo);
        sound.setVolume(1.0f, 1.0f);

        ImageTarget target;

        final double LATERAL_DISTANCE_RANGE = 20;
        final double DISTANCE_RANGE = 75;
        final double ANGLE_RANGE = 3;

        final double MOVE_SPEED = 0.3;
        final double TURN_SPEED = 0.2;

        final int STUPUD_GYRO = 47;

        final int DIRECTION_TO_IMAGE = 60;

        int state = 0;

        switch(fieldSide) {
            case RED:
                target = ImageTarget.GEARS;
                driveTrain.move(MOVE_SPEED, 180 + DIRECTION_TO_IMAGE, 1905);
                break;
            case BLUE:
                target = ImageTarget.WHEELS;
                driveTrain.move(MOVE_SPEED, 180 - DIRECTION_TO_IMAGE, 1905);
                break;
            default:
                target = ImageTarget.GEARS; //It's Mr. Gears
        }

        Thread.sleep(500);

        boolean shouldStop = false;

        while(opMode.opModeIsActive()) {
            boolean canSeeTarget = navigator.canSeeTarget(target);

            Vector3 rotation = navigator.getRelativeTargetRotation(target);
            Vector3 translation = navigator.getRelativeTargetTranslation(target);
            translation.z = abs(translation.z);

            if(canSeeTarget) {
                opMode.telemetry.addData("Rotation", rotation);
                opMode.telemetry.addData("Translation", translation);
            } else {
                opMode.telemetry.addLine("Image is not visible");
            }

            opMode.telemetry.addData("Case", state);

            double hypotenuse = sqrt(pow(translation.x, 2) + pow(translation.z, 2));
            @SuppressWarnings("SuspiciousNameCombination") //Math.atan2() doesn't like receiving a variable named "x" for parameter "y"
                    double alpha = toDegrees(atan2(translation.x, translation.z));
            double phi = rotation.y - alpha;

            /**
             * If a line segment parallel to the image was drawn from the robot (assuming the
             * robot is just a point) until it intersected a line perpendicular to the image,
             * lateralDistanceFromImage would be the length of that line segment.
             */
            double lateralDistanceFromImage = -hypotenuse * sin(toRadians(phi));

            double turnPower = ((7.0 / 900.0) * rotation.y) + (driveTrain.getMinimumTurnPower() * signum(rotation.y));
            turnPower = Range.clip(turnPower, -0.3, 0.3);

            double movePower = /*((1.0 / 8000.0) * abs(lateralDistanceFromImage)) + driveTrain.getMinimumMovePower()*/MOVE_SPEED;
            movePower = Range.clip(movePower, -0.4, 0.4);

            //State 0: Align and center on image
            //State 1: Get close enough to the image for the color sensor to do its stuff
            //State 2: Color sensor things
            //State 3: Move over to second beacon and repeat states 0 - 2
            switch(state) {
                case 0:
                    if(canSeeTarget) {
                        if(withinRange(rotation.y, ANGLE_RANGE) && withinRange(lateralDistanceFromImage, LATERAL_DISTANCE_RANGE)) {
                            driveTrain.stop();
                            state++;
                        } else {
                            driveTrain.moveAndTurn(movePower, 180 + (90 * signum(lateralDistanceFromImage)), turnPower);
                        }
                    } else {
                        driveTrain.stop();
                    }

                    break;
                case 1:
                    driveTrain.move(0.3, 180, translation.z - DISTANCE_RANGE);
                    driveTrain.move(0.2, 270, translation.x + 85);

                    while(!touchSensor.isPressed()) {
                        driveTrain.move(0.2, 180);
                    }

                    Thread.sleep(500);

                    state++;
                    break;
                case 2:
                    driveTrain.move(0.2, 0, 50);

                    switch(fieldSide) {
                        case RED:
                            if(colorSensor.red() >= 2) {
                                driveTrain.move(0.2, 270, 80);
                            } else if(colorSensor.blue() >= 2) {
                                driveTrain.move(0.2, 90, 80);
                            }
                            break;
                        case BLUE:
                            if(colorSensor.blue() >= 2) {
                                driveTrain.move(0.2, 270, 80);
                            } else if(colorSensor.red() >= 2) {
                                driveTrain.move(0.2, 90, 80);
                            }
                            break;
                    }

                    driveTrain.move(0.5, 180, 200);
                    driveTrain.move(0.5, 0, 400);

                    state++;
                case 3:
                    if(shouldStop) {
                        driveTrain.stop();
                        opMode.requestOpModeStop();
                    } else {
                        state = 0;

                        switch(fieldSide) {
                            case RED: target = ImageTarget.TOOLS;
                                break;
                            case BLUE: target = ImageTarget.LEGOS;
                                break;
                        }

                        shouldStop = true;
                    }

                    switch(fieldSide) {
                        case RED:
                            driveTrain.move(MOVE_SPEED, 270, 1200);
                            break;
                        case BLUE:
                            driveTrain.move(MOVE_SPEED, 90, 1200);
                            break;
                    }
                    break;
            }

            opMode.telemetry.update();
        }
    }

    private boolean withinRange(double number, double range) {
        return number < range && number > -range;
    }
}
