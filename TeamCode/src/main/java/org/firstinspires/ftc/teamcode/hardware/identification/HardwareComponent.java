package org.firstinspires.ftc.teamcode.hardware.identification;


import org.firstinspires.ftc.teamcode.logging.Loggable;

public abstract class HardwareComponent implements Loggable {
    public abstract void init(HardwareIdentifier identifier);
}
