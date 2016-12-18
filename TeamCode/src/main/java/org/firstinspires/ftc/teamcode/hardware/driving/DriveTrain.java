package org.firstinspires.ftc.teamcode.hardware.driving;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.HardwareComponent;

public abstract class DriveTrain extends HardwareComponent {
    public abstract void driveControlled(Gamepad controller);

    public abstract void stop();

    public abstract void move(double power, double millimeters) throws InterruptedException;

    public abstract void move(double power, double millimeters, double degrees) throws InterruptedException;

    public abstract void turn(double power, double degrees) throws InterruptedException;
}
