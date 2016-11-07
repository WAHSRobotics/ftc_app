package org.firstinspires.ftc.teamcode.hardware.gathering;


import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.hardware.HardwareComponent;

public abstract class Gatherer extends HardwareComponent {
    public abstract void gatherControlled(Gamepad gamepad);

    public abstract void gather() throws InterruptedException;
}
