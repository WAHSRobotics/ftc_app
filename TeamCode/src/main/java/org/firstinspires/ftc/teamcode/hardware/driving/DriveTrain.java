package org.firstinspires.ftc.teamcode.hardware.driving;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.identification.HardwareComponent;

public abstract class DriveTrain extends HardwareComponent {
    public abstract void driveControlled(Gamepad controller);

    public abstract void move(double millimeters);

    public abstract void move(double millimeters, double degrees);

    public abstract void turn(double degrees);
}
