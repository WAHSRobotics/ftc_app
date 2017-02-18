package org.firstinspires.ftc.team9202hme.program.autonomous;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

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
        Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.CHARGER_SIDE_UP, 1, false);

        driveTrain.init(opMode.hardwareMap);
        navigator.init();

        opMode.waitForStart();

        Sound sound = new Sound();
        sound.load(opMode.hardwareMap, R.raw.you_took_the_peepo);
        sound.setVolume(1.0f, 1.0f);

        final ImageTarget TARGET = ImageTarget.LEGOS;

        final double MOVE_RANGE = 20;
        final double DISTANCE_RANGE = 40;
        final double ANGLE_RANGE = 3;

        final double MOVE_SPEED = 0.3;
        final double TURN_SPEED = 0.2;

        final double MIN = 0.05;

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

            //State 0: Align and center on image
            //State 1: Get close enough to the image for the color sensor to do its stuff
            //State 2: Stop the program (later this will be for color sensor)
            if(canSeeTarget) {
                double angleScale = ((7.0 / 900.0) * rotation.y) + (rotation.y >= 0 ? MIN : -MIN);
                angleScale = Range.clip(angleScale, -0.3, 0.3);

                double moveScale = ((Math.sqrt(Math.abs(translation.x))/(25)));

                double hypotenuse = sqrt(pow(translation.x, 2) + pow(translation.z, 2));
                double alpha = toDegrees(atan2(translation.x, translation.z));
                double phi = rotation.y - alpha;

                double distanceFromImage = -hypotenuse * sin(toRadians(phi));

                if (moveScale > 1.0){
                    moveScale = 1;
                } else if (moveScale < -1.0){
                    moveScale = -1.0;
                }

                switch(state) {
                    case 0:
                        opMode.telemetry.addData("Angle Scale", angleScale);
                        opMode.telemetry.addData("Distance from Image", distanceFromImage);

                        if(rotation.y > ANGLE_RANGE) {
                           turnPower = angleScale;
                        } else if(rotation.y < -ANGLE_RANGE) {
                            turnPower = angleScale;
                        } else {
                            turnPower = 0;
                        }

                        if(distanceFromImage > MOVE_RANGE) {
                            movePower = MOVE_SPEED;
                            angle = 270;
                        } else if(distanceFromImage < -MOVE_RANGE) {
                            movePower = MOVE_SPEED;
                            angle = 90;
                        } else {
                            movePower = 0;
                        }

                        driveTrain.moveAndTurn(movePower, angle, turnPower);
                        break;
                }
            } else {
                driveTrain.stop();
            }

            opMode.telemetry.update();
        }
    }
}
