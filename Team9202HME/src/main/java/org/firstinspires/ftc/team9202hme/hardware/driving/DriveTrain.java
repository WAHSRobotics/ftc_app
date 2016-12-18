package org.firstinspires.ftc.team9202hme.hardware.driving;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9202hme.hardware.HardwareComponent;

public abstract class DriveTrain extends HardwareComponent {
    public abstract void driveControlled(Gamepad controller);

    public abstract void stop();

    public abstract void move(double power, double millimeters) throws InterruptedException;

    public abstract void move(double power, double millimeters, double degrees) throws InterruptedException;

    public abstract void turn(double power, double degrees) throws InterruptedException;
}
