package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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

        driveTrain.init(opMode.hardwareMap);
        navigator.init();

        opMode.waitForStart();

        Sound sound = new Sound();
        sound.load(opMode.hardwareMap, R.raw.you_took_the_peepo);
        sound.setVolume(1.0f, 1.0f);

        final ImageTarget TARGET = ImageTarget.TOOLS;

        final double LATERAL_DISTANCE_RANGE = 20;
        final double DISTANCE_RANGE = 40;
        final double ANGLE_RANGE = 3;

        final double MOVE_SPEED = 0.3;

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

            if(canSeeTarget) {
                double hypotenuse = sqrt(pow(translation.x, 2) + pow(translation.z, 2));
                @SuppressWarnings("SuspiciousNameCombination") //Math.atan2() doesn't like receiving a variable named "x" for parameter "y"
                double alpha = toDegrees(atan2(translation.x, translation.z));
                double phi = rotation.y - alpha;

                /**
                 * If a line segment parallel to the image was drawn from the robot (assuming the
                 * robot is just a point) until it intersected a line perpendicular to the image,
                 * lateralDistanceFromImage would be the length of that line segment.
                 */
                double lateralDistanceFromImage = -(hypotenuse * sin(toRadians(phi)));

                double turnPower = ((7.0 / 900.0) * rotation.y) + (driveTrain.getMinimumTurnPower() * signum(rotation.y));
                turnPower = Range.clip(turnPower, -0.3, 0.3);

                double movePower = /*((1.0 / 1000.0) * abs(lateralDistanceFromImage)) + driveTrain.getMinimumMovePower()*/MOVE_SPEED;
                movePower = Range.clip(movePower, -0.4, 0.4);

                //State 0: Align and center on image
                //State 1: Get close enough to the image for the color sensor to do its stuff
                //State 2: Stop the program (later this will be for color sensor)
                switch(state) {
                    case 0:
                        opMode.telemetry.addData("Turn Power", turnPower);
                        opMode.telemetry.addData("Distance from Image", lateralDistanceFromImage);

                        if(withinRange(rotation.y, ANGLE_RANGE) && withinRange(lateralDistanceFromImage, LATERAL_DISTANCE_RANGE)) {
                            driveTrain.stop();
                        } else {
                            driveTrain.moveAndTurn(movePower, 180 + (90 * signum(lateralDistanceFromImage)), turnPower);
                        }

                        break;
                    case 1:
                        break;
                }
            } else {
                driveTrain.stop();
            }

            opMode.telemetry.update();
        }
    }

    private boolean withinRange(double number, double range) {
        return number < range && number > -range;
    }
}
