package org.firstinspires.ftc.teamcode.hardware;


public class HardwareSpecifications {
    private final double mmWheelCircumference;
    private final double encoderTicksPerRotation;
    private final double mmLength;
    private final double mmWidth;
    private final double mmHeight;

    public HardwareSpecifications(double mmWheelDiameter, double encoderTicksPerRotation, double mmLength, double mmWidth, double mmHeight) {
        this.mmWheelCircumference = mmWheelDiameter;
        this.encoderTicksPerRotation = encoderTicksPerRotation;
        this.mmLength = mmLength;
        this.mmWidth = mmWidth;
        this.mmHeight = mmHeight;
    }

    public double getMmWheelCircumference() {
        return mmWheelCircumference;
    }

    public double getEncoderTicksPerRotation() {
        return encoderTicksPerRotation;
    }

    public double getMmLength() {
        return mmLength;
    }

    public double getMmWidth() {
        return mmWidth;
    }

    public double getMmHeight() {
        return mmHeight;
    }
}
