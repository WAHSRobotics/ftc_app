package org.firstinspires.ftc.team9202hme.hardware;


import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class HardwareComponent {
    public abstract void init(HardwareMap hardwareMap);
    public abstract void logTelemetry(Telemetry telemetry);
}