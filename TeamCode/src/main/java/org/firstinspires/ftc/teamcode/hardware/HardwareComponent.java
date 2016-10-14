package org.firstinspires.ftc.teamcode.hardware;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.logging.Loggable;

public abstract class HardwareComponent implements Loggable {
    public abstract void init(HardwareMap hardwareMap);
}