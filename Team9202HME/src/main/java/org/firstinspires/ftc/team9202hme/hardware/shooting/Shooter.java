package org.firstinspires.ftc.team9202hme.hardware.shooting;


import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9202hme.hardware.HardwareComponent;

public abstract class Shooter extends HardwareComponent {
    public abstract void shootControlled(Gamepad gamepad);

    public abstract void shoot() throws InterruptedException;
}
