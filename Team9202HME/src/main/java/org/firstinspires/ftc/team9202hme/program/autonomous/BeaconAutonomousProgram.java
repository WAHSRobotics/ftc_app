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
        /*
        NOTES:
            - All measurements of distance throughout this program are given in millimeters
            - All measurements of angle are given in degrees
            - Since the phone mount and beacon pusher is on the back of the robot,
              when instructing the robot to move at an angle it is usually 180 plus
              that angle, since the 180 refers to the back of the robot
         */

        /*
        Declare hardware components that the program will be working with. HTML
        Documentation for HolonomicDriveTrain and RollerShooter can be found in the
        "doc" folder of this module, Team9202HME
         */
        HolonomicDriveTrain driveTrain = new HolonomicDriveTrain(76.2, 1120);
        RollerShooter shooter = new RollerShooter();
        ColorSensor colorSensor;
        TouchSensor touchSensor;

        /*
        Declare and initialize navigator, used for locating images in 3D space. Because
        Vuforia (the library FTC supplies us with for image recognition) gives different
        readings based on the orientation of the phone, you have to specify how the phone
        will be oriented on the robot
         */
        navigator = new Navigator(CameraSide.BACK, PhoneOrientation.CHARGER_SIDE_UP, 1, false);
        navigator.init();

        //Initialize hardware components using hardware map
        driveTrain.init(opMode.hardwareMap);
        shooter.init(opMode.hardwareMap);
        colorSensor = opMode.hardwareMap.colorSensor.get("color");
        colorSensor.enableLed(false);
        touchSensor = opMode.hardwareMap.touchSensor.get("touch");

        //Suspend this thread until the play button is pressed
        opMode.waitForStart();

        /*
        These are margins of error for centering the robot on the image,
        necessary because the robot's physical imperfections prevent it
        from being perfectly centered on the image.
         */
        final double LATERAL_DISTANCE_MARGIN = 10; //The margin of error for lateral distance
        final double ROTATION_MARGIN = 2; //The margin of error for being parallel with the wall

        /*
        The closest the phone camera can get to the image before it starts to
        have difficulty recognizing it
         */
        final double MINIMUM_DISTANCE_FROM_IMAGE = 150;

        /*
        The speed at which, for the most part, the robot will move
        throughout the duration of autonomous
         */
        final double MOVE_SPEED = 0.3;

        //Angle at which to drive to get within range of image
        final int DIRECTION_TO_IMAGE = 60;

        //Distance to drive at the angle specified above
        final int DISTANCE_FROM_IMAGE = 1905;

        /*
        Sets the image target based on what side of the field the robot
        is on, specified by the OpModes calling upon this function, and
        then moves towards it
         */
        switch(fieldSide) {
            case RED:
                target = ImageTarget.LEGOS;
                driveTrain.move(0.4, 180 + DIRECTION_TO_IMAGE, DISTANCE_FROM_IMAGE);
                break;
            case BLUE:
                target = ImageTarget.LEGOS;
                driveTrain.move(0.4, 180 - DIRECTION_TO_IMAGE, DISTANCE_FROM_IMAGE);
                break;
            default:
                target = ImageTarget.GEARS; //If the JVM breaks and fieldSide isn't RED or BLUE, I guess we'll go for gears
        }

        //Give Vuforia a few moments to calibrate itself; it doesn't take very long
        Thread.sleep(500);
        
        final double CAMERA_OFFSET = 50;

        while(opMode.opModeIsActive()) {
            //Whether or not the image is visible
            boolean canSeeTarget = navigator.canSeeTarget(target);

            updateImageLocation();

            //State 0: Align so that robot is parallel to the wall, and center robot on image
            //State 1: Get close enough to the image for the color sensor to get accurate readings
            //State 2: Run the shooter to (hopefully) score two pre-loaded particles into the central vortex
            //State 3: Activate the correct color on the beacon based on readings from the color sensor
            //State 4: Move over to second beacon and repeat states 0 - 3
            switch(state) {
                case 0:
                    //If the image is visible, align with it. Otherwise, turn for a few moments and then check again.
                    if(canSeeTarget) {
                        /*
                        If the robot is centered within its margin of error, then stop the robot and progress to state 1.
                        Otherwise, attempt to align with the image.
                         */
                        if(withinMargin(rotation.y, ROTATION_MARGIN) && withinMargin(lateralDistanceFromImage, LATERAL_DISTANCE_MARGIN)) {
                            driveTrain.stop();
                            state++;
                        } else {
                            //This scales the speed at which the robot turns to correct for rotational error, which helps prevent over-correcting
                            double turnPower = ((7.0 / 900.0) * rotation.y) + (driveTrain.getMinimumTurnPower() * signum(rotation.y));
                            //If the robot spins too fast it loses sight of the image, so the power is clamped between -0.3 and 0.3
                            turnPower = Range.clip(turnPower, -0.3, 0.3);

                            /*
                            Moves the robot either to the right or to the left, based on the sign of the lateral distance
                            from the image (tells us if the image is on the left or the right) and while doing so, spins to
                            correct for rotational error
                             */
                            driveTrain.moveAndTurn(MOVE_SPEED, 180 + (90 * signum(lateralDistanceFromImage)), turnPower);
                        }
                    } else {
                        driveTrain.stop();
                    }

                    break;
                case 1:
                    driveTrain.move(MOVE_SPEED, 180, translation.z - MINIMUM_DISTANCE_FROM_IMAGE);

                    updateImageLocation();

                    Thread.sleep(10);

                    while(!withinMargin(translation.x + CAMERA_OFFSET, 15)) {
                        updateImageLocation();

                        if(navigator.canSeeTarget(target)) {
                            driveTrain.move(MOVE_SPEED, 180 + (90 * signum(translation.x + CAMERA_OFFSET)));
                        } else {
                            driveTrain.stop();
                        }

                        opMode.idle();
                    }

                    driveTrain.stop();

                    ElapsedTime timer = new ElapsedTime();
                    timer.reset();

                    while(!touchSensor.isPressed() && timer.seconds() < 3.0) {
                        driveTrain.move(MOVE_SPEED, 180);

                        opMode.idle();
                    }

                    driveTrain.stop();

                    Thread.sleep(500);

                    state++;

                    break;
                case 2:
                    if(!onSecondBeacon) {
                        shooter.shoot();
                    }

                    state++;

                    break;
                case 3:
                    driveTrain.move(MOVE_SPEED, 0, 50);

                    updateImageLocation();

                    final double DISTANCE_TO_BUTTON = 65 + (translation.x + CAMERA_OFFSET);

                    switch(fieldSide) {
                        case RED:
                            if(colorSensor.red() >= 2) {
                                driveTrain.move(MOVE_SPEED, 270, DISTANCE_TO_BUTTON);
                            } else if(colorSensor.blue() >= 2) {
                                driveTrain.move(MOVE_SPEED, 90, DISTANCE_TO_BUTTON);
                            }
                            break;
                        case BLUE:
                            if(colorSensor.blue() >= 2) {
                                driveTrain.move(MOVE_SPEED, 270, DISTANCE_TO_BUTTON);
                            } else if(colorSensor.red() >= 2) {
                                driveTrain.move(MOVE_SPEED, 90, DISTANCE_TO_BUTTON);
                            }
                            break;
                    }

                    driveTrain.move(0.5, 180, 200);
                    driveTrain.move(0.5, 0, 400);

                    state++;

                    break;
                case 4:
                    if(onSecondBeacon) {
                        driveTrain.stop();
                        opMode.requestOpModeStop();
                    } else {
                        state = 0;

                        switch(fieldSide) {
                            case RED: target = ImageTarget.TOOLS;
                                break;
                            case BLUE: target = ImageTarget.TOOLS;
                                break;
                        }

                        onSecondBeacon = true;
                    }

                    switch(fieldSide) {
                        case RED:
                            driveTrain.move(0.4, 270, 1200);
                            break;
                        case BLUE:
                            driveTrain.move(0.4, 90, 1200);
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
