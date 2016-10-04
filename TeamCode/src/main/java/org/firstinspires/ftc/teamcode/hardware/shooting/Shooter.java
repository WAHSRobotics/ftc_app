package org.firstinspires.ftc.teamcode.hardware.shooting;


import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.identification.HardwareComponent;

public abstract class Shooter extends HardwareComponent {
    public abstract void shootControlled(Gamepad gamepad);

    public abstract void shoot();

    public abstract double rangeInMillimeters();
}
