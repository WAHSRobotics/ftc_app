package org.firstinspires.ftc.teamcode.hardware.driving;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.HardwareComponent;

public abstract class DriveTrain extends HardwareComponent {
    public abstract void driveControlled(Gamepad controller);

    public abstract void stop();

    public abstract void move(int millimeters) throws InterruptedException;

    public abstract void move(int millimeters, int degrees) throws InterruptedException;

    public abstract void turn(int degrees) throws InterruptedException;
}
