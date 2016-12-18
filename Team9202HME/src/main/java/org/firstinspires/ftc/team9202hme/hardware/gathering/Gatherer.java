package org.firstinspires.ftc.team9202hme.hardware.gathering;


import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9202hme.hardware.HardwareComponent;

public abstract class Gatherer extends HardwareComponent {
    public abstract void gatherControlled(Gamepad gamepad);

    public abstract void gather() throws InterruptedException;
}
