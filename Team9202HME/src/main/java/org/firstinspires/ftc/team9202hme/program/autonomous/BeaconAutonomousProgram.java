package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
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

    @Override
    @SuppressWarnings("SuspiciousNameCombination")
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
        Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.CHARGER_SIDE_UP, 1, false);
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
        The image that the navigator will be targeting, changes
        based on what side of the field we're on, as well as what
        beacon we're currently pressing
         */
        ImageTarget target;

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
        final double MINIMUM_DISTANCE_FROM_IMAGE = 75;

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
                target = ImageTarget.GEARS;
                driveTrain.move(MOVE_SPEED, 180 + DIRECTION_TO_IMAGE, DISTANCE_FROM_IMAGE);
                break;
            case BLUE:
                target = ImageTarget.WHEELS;
                driveTrain.move(MOVE_SPEED, 180 - DIRECTION_TO_IMAGE, DISTANCE_FROM_IMAGE);
                break;
            default:
                target = ImageTarget.GEARS; //If the JVM breaks and fieldSide isn't RED or BLUE, I guess we'll go for gears
        }

        //Give Vuforia a few moments to calibrate itself; it doesn't take very long
        Thread.sleep(500);

        /*
        Since the robot shouldn't do things like shoot or continue
        looking for more beacons after it has found the second, we
        need a variable to know when the robot has found the second
        beacon
         */
        boolean onSecondBeacon = false;

        /*
        The current state that the state machine, which is in the loop
        below, is in. It's rather crude, but it serves as a reliable
        method of changing from state to state
         */
        int state = 0;

        /*
        This is the main loop of the program, which will continuously update
        important information, such as the location of the current image target
        in 3D space
         */
        while(opMode.opModeIsActive()) {
            //Whether or not the image is visible
            boolean canSeeTarget = navigator.canSeeTarget(target);

            //The image's rotation in 3D space, using Euler angles (pitch, yaw, and roll, respectively x, y, and z)
            Vector3 rotation = navigator.getRelativeTargetRotation(target);

            //The image's location in 3D space, given by a 3D point at the center of the image
            Vector3 translation = navigator.getRelativeTargetTranslation(target);

            /*
            Because of the right hand rule, the z coordinate should technically
            always be negative in our situation, but it makes programming a bit
            harder so we just make it positive
             */
            translation.z = abs(translation.z);

            /*
            These are variables are just for computing the lateral distance from the
            image (see below) which we plan to use to scale our speed, so that the
            closer the robot is to the image, the slower it goes. This should prevent
            the robot from over-correcting while aligning.
             */
            double hypotenuse = sqrt(pow(translation.x, 2) + pow(translation.z, 2));
            double alpha = toDegrees(atan2(translation.x, translation.z));
            double phi = rotation.y - alpha;

            /*
            If a line segment parallel to the image was drawn from the robot (assuming the
            robot is just a point) until it intersected a line perpendicular to the image,
            lateralDistanceFromImage would be the length of that line segment.
             */
            double lateralDistanceFromImage = -hypotenuse * sin(toRadians(phi));

            //State 0: Align so that robot is parallel to the wall, and center robot on image
            //State 1: Run the shooter to (hopefully) score two pre-loaded particles into the central vortex
            //State 2: Get close enough to the image for the color sensor to get accurate readings
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
                        driveTrain.turn(0.2);
                        Thread.sleep(500);
                        driveTrain.stop();
                    }

                    break;
                case 1:
                    if(!onSecondBeacon) {
                        shooter.shoot();
                    }

                    state++;

                    break;
                case 2:
                    driveTrain.move(0.3, 180, translation.z - MINIMUM_DISTANCE_FROM_IMAGE);

                    while(!withinMargin(translation.x + 40, 10)) {
                        driveTrain.move(0.25, 180 + (90 * signum(translation.x + 40)));
                    }

                    while(!touchSensor.isPressed()) {
                        driveTrain.move(MOVE_SPEED, 180);
                    }

                    Thread.sleep(500);

                    state++;

                    break;
                case 3:
                    driveTrain.move(MOVE_SPEED, 0, 50);

                    switch(fieldSide) {
                        case RED:
                            if(colorSensor.red() >= 2) {
                                driveTrain.move(MOVE_SPEED, 270, 80);
                            } else if(colorSensor.blue() >= 2) {
                                driveTrain.move(MOVE_SPEED, 90, 80);
                            }
                            break;
                        case BLUE:
                            if(colorSensor.blue() >= 2) {
                                driveTrain.move(MOVE_SPEED, 270, 80);
                            } else if(colorSensor.red() >= 2) {
                                driveTrain.move(MOVE_SPEED, 90, 80);
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

            /*
            Update the telemetry with useful information about the image
            and the state of the program
             */
            opMode.telemetry.addData("Current Target", target);
            opMode.telemetry.addData("Translation", translation);
            opMode.telemetry.addData("Rotation", rotation);
            opMode.telemetry.addData("Lateral Distance", lateralDistanceFromImage);
            opMode.telemetry.addData("Current State", state);
            opMode.telemetry.addData("Current Beacon", onSecondBeacon ? 2 : 1);
            opMode.telemetry.update();
        }
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
