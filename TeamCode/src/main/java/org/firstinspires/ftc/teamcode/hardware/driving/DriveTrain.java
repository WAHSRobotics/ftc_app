package org.firstinspires.ftc.teamcode.hardware.driving;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.HardwareComponent;

public abstract class DriveTrain extends HardwareComponent {
    public abstract void driveControlled(Gamepad controller);

    public abstract void stop();

    public abstract void moveIndefinitely() throws InterruptedException;

    public abstract void moveIndefinitely(int degrees) throws InterruptedException;

    public abstract void move(int millimeters) throws InterruptedException;

    public abstract void move(int millimeters, int degrees) throws InterruptedException;

    public abstract void turn(int degrees, double power) throws InterruptedException;

    public abstract void leftPowerInc(double amount) throws InterruptedException;

    public abstract void rightPowerInc(double amount) throws InterruptedException;
}
