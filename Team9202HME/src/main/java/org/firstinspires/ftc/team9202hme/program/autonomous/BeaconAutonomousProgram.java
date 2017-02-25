package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.team9202hme.hardware.driving.HolonomicDriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.shooting.RollerShooter;
import org.firstinspires.ftc.team9202hme.math.vector.Vector3;
import org.firstinspires.ftc.team9202hme.navigation.ImageTarget;
import org.firstinspires.ftc.team9202hme.navigation.Navigator;
import org.firstinspires.ftc.team9202hme.navigation.CameraSide;
import org.firstinspires.ftc.team9202hme.navigation.PhoneOrientation;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;

import static java.lang.Math.*;


public class BeaconAutonomousProgram extends AutonomousProgram {
    public BeaconAutonomousProgram(LinearOpMode opMode, FieldSide fieldSide) {
        super(opMode, fieldSide);
    }

    private ImageTarget target;

    private Vector3 translation = new Vector3();
    private Vector3 rotation = new Vector3();
    private Navigator navigator;

    private double lateralDistanceFromImage = 0;

    private int state = 0;

    private boolean onSecondBeacon = false;

    @Override
    public void run() throws InterruptedException {
        HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, 1120);
        RollerShooter shooter = new RollerShooter();
        ColorSensor colorSensor;
        TouchSensor touchSensor;

        navigator = new Navigator(CameraSide.BACK, PhoneOrientation.CHARGER_SIDE_UP, 1, false);
        navigator.init();

        driveTrain.init(opMode.hardwareMap);
        shooter.init(opMode.hardwareMap);
        colorSensor = opMode.hardwareMap.colorSensor.get("color");
        colorSensor.enableLed(false);
        touchSensor = opMode.hardwareMap.touchSensor.get("touch");

        opMode.waitForStart();

        final double LATERAL_DISTANCE_MARGIN = 10;
        final double ROTATION_MARGIN = 2;

        final double MINIMUM_DISTANCE_FROM_IMAGE = 150;

        final double PRECISION_SPEED = 0.25;
        final double MOVE_SPEED = 0.5;

        final int DIRECTION_TO_IMAGE = 60;

        final int DISTANCE_FROM_IMAGE = 1905;

        switch(fieldSide) {
            case RED:
                target = ImageTarget.GEARS;
                driveTrain.move(MOVE_SPEED, 180 + DIRECTION_TO_IMAGE, DISTANCE_FROM_IMAGE);
                break;
            case BLUE:
                target = ImageTarget.WHEELS;
                driveTrain.move(MOVE_SPEED, 180 - DIRECTION_TO_IMAGE, DISTANCE_FROM_IMAGE);
                break;
            default:
                target = ImageTarget.GEARS; //It's Mr. Gears
        }
        
        final double CAMERA_OFFSET = 60;

        while(opMode.opModeIsActive()) {
            boolean canSeeTarget = navigator.canSeeTarget(target);

            updateImageLocation();

            //State 0: Align so that robot is parallel to the wall, and center robot on image
            //State 1: Run the shooter to (hopefully) score two pre-loaded particles into the central vortex
            //State 2: Get close enough to the image for the color sensor to get accurate readings
            //State 3: Activate the correct color on the beacon based on readings from the color sensor
            //State 4: Move over to second beacon and repeat states 0, 2, and 3
            switch(state) {
                case 0:
                    if(canSeeTarget) {
                        if(withinMargin(rotation.y, ROTATION_MARGIN) && withinMargin(lateralDistanceFromImage, LATERAL_DISTANCE_MARGIN)) {
                            driveTrain.stop();
                            state++;
                        } else {
                            double turnPower = ((7.0 / 900.0) * rotation.y) + (driveTrain.getMinimumTurnPower() * signum(rotation.y));
                            turnPower = Range.clip(turnPower, -0.3, 0.3);

                            driveTrain.moveAndTurn(PRECISION_SPEED, 180 + (90 * signum(lateralDistanceFromImage)), turnPower);
                        }
                    } else {
                        driveTrain.stop();
                    }

                    break;
                case 1:
                    driveTrain.move(PRECISION_SPEED, 180, translation.z - MINIMUM_DISTANCE_FROM_IMAGE);

                    updateImageLocation();

                    Thread.sleep(10);

                    while(!withinMargin(translation.x + CAMERA_OFFSET, 12.5)) {
                        updateImageLocation();

                        if(navigator.canSeeTarget(target)) {
                            driveTrain.move(PRECISION_SPEED, 180 + (90 * signum(translation.x + CAMERA_OFFSET)));
                        } else {
                            driveTrain.stop();
                        }

                        opMode.idle();
                    }

                    driveTrain.stop();

                    if(!onSecondBeacon) {
                        shooter.shoot();
                    }

                    ElapsedTime timer = new ElapsedTime();
                    timer.reset();

                    while(!touchSensor.isPressed() && timer.seconds() < 2.0) {
                        driveTrain.move(PRECISION_SPEED, 180);

                        opMode.idle();
                    }

                    driveTrain.stop();

                    state++;

                    break;
                case 2:
                    driveTrain.move(PRECISION_SPEED, 0, 50);

                    updateImageLocation();

                    final double DISTANCE_TO_BUTTON = 70 + (translation.x + CAMERA_OFFSET);

                    switch(fieldSide) {
                        case RED:
                            if(colorSensor.red() >= 2) {
                                driveTrain.move(PRECISION_SPEED, 270, DISTANCE_TO_BUTTON);
                            } else if(colorSensor.blue() >= 2) {
                                driveTrain.move(PRECISION_SPEED, 90, DISTANCE_TO_BUTTON);
                            }
                            break;
                        case BLUE:
                            if(colorSensor.blue() >= 2) {
                                driveTrain.move(PRECISION_SPEED, 270, DISTANCE_TO_BUTTON);
                            } else if(colorSensor.red() >= 2) {
                                driveTrain.move(PRECISION_SPEED, 90, DISTANCE_TO_BUTTON);
                            }
                            break;
                    }

                    driveTrain.move(MOVE_SPEED, 180, 200);
                    driveTrain.move(MOVE_SPEED, 0, 400);

                    state++;

                    break;
                case 3:
                    if(onSecondBeacon) {
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

                        onSecondBeacon = true;
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
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private void updateImageLocation() {
        rotation = navigator.getRelativeTargetRotation(target);

        translation = navigator.getRelativeTargetTranslation(target);

        translation.z = abs(translation.z);

        double hypotenuse = sqrt(pow(translation.x, 2) + pow(translation.z, 2));
        double alpha = toDegrees(atan2(translation.x, translation.z));
        double phi = rotation.y - alpha;

        lateralDistanceFromImage = -hypotenuse * sin(toRadians(phi));

        opMode.telemetry.addData("Current Target", target);
        opMode.telemetry.addData("Target Visible", navigator.getRelativeTargetRotation(target));

        opMode.telemetry.addData("Translation", translation);
        opMode.telemetry.addData("Rotation", rotation);
        opMode.telemetry.addData("Lateral Distance", lateralDistanceFromImage);
        opMode.telemetry.addData("Current State", state);
        opMode.telemetry.addData("Current Beacon", onSecondBeacon ? 2 : 1);
        opMode.telemetry.update();
    }

    /**
     * Checks if a given value is within a margin of error
     *
     * @param number The number to check
     * @param margin The margin of error
     * @return Whether or not the number is in between -margin and +margin
     */
    private boolean withinMargin(double number, double margin) {
        return number < margin && number > -margin;
    }
}
