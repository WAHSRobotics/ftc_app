package org.firstinspires.ftc.team9202hme.opmode.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.team9202hme.navigation.CameraSide;
import org.firstinspires.ftc.team9202hme.navigation.ImageTarget;
import org.firstinspires.ftc.team9202hme.navigation.Navigator;
import org.firstinspires.ftc.team9202hme.navigation.PhoneOrientation;

@TeleOp(name = "Navigation Test", group = "Tests")
//@Disabled
public class NavigationTest extends OpMode {
    private Navigator navigator = new Navigator(CameraSide.BACK, PhoneOrientation.CHARGER_SIDE_UP, 4, true);

    @Override
    public void init() {
        navigator.init();
    }

    @Override
    public void loop() {
        for(ImageTarget target : ImageTarget.values()) {
            telemetry.addData("Navigator Data for Image Target", target.name());
            telemetry.addData("Visible", navigator.canSeeTarget(target));
            telemetry.addData("Translation", navigator.getRelativeTargetTranslation(target));
            telemetry.addData("Rotation", navigator.getRelativeTargetRotation(target));
        }

        telemetry.update();
    }
}
