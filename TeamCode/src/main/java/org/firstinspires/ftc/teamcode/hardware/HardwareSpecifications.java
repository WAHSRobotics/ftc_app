package org.firstinspires.ftc.teamcode.hardware;


public class HardwareSpecifications {
    private final double mmWheelDiameter;
    private final double encoderTicksPerRotation;
    private final double mmRobotDiameter;

    public HardwareSpecifications(double mmWheelDiameter, double encoderTicksPerRotation, double mmRobotDiameter) {
        this.mmWheelDiameter = mmWheelDiameter;
        this.encoderTicksPerRotation = encoderTicksPerRotation;
        this.mmRobotDiameter = mmRobotDiameter;
    }

    public double getMmWheelDiameter() {
        return mmWheelDiameter;
    }

    public double getEncoderTicksPerRotation() {
        return encoderTicksPerRotation;
    }

    public double getMmRobotDiameter() {
        return mmRobotDiameter;
    }
}
