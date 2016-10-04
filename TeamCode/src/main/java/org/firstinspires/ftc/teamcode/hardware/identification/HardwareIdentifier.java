package org.firstinspires.ftc.teamcode.hardware.identification;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HardwareIdentifier {
    private HardwareMap hardwareMap;

    public HardwareIdentifier(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    public DcMotor findDcMotor(HardwarePurpose purpose, HardwareSide side, HardwarePosition position) {
        String motorName = getName(purpose, side, position, HardwareType.DC_MOTOR);

        DcMotor dcMotor;

        try {
            dcMotor = hardwareMap.dcMotor.get(motorName);
        } catch(Exception e) {
            throw new IllegalArgumentException("DcMotor " + motorName + " could not be found. Verify that the hardware map follows naming conventions, and that the device exists.");
        }

        return dcMotor;
    }

    private String getName(HardwarePurpose hardwarePurpose, HardwareSide hardwareSide, HardwarePosition hardwarePosition, HardwareType hardwareType) {
        String purpose = "";
        String position = "";
        String side = "";
        String type = "";

        switch(hardwarePurpose) {
            case DRIVING: purpose = "driveTrain";
                break;
            case SHOOTING: purpose = "shootMech";
                break;
            case GATHERING: purpose = "gatherMech";
                break;
            case SENSORY: purpose = "sensorMech";
                break;
            case SERVO_ARM: purpose = "servoArm";
                break;
        }

        switch(hardwarePosition) {
            case FRONT_LEFT: position = "frontLeft";
                break;
            case FRONT_RIGHT: position = "frontRight";
                break;
            case BACK_LEFT: position = "backLeft";
                break;
            case BACK_RIGHT: position = "backRight";
                break;
            case FRONT: position = "front";
                break;
            case LEFT: position = "left";
                break;
            case BACK: position = "back";
                break;
            case RIGHT: position = "right";
                break;
            case TOP: position = "top";
                break;
            case BOTTOM: position = "bottom";
                break;
            case TOP_LEFT: position = "topLeft";
                break;
            case TOP_RIGHT: position = "topRight";
                break;
            case BOTTOM_LEFT: position = "bottomLeft";
                break;
            case BOTTOM_RIGHT: position = "bottomRight";
                break;
            case CENTER: position = "center";
                break;
        }

        switch(hardwareSide) {
            case FRONT: side = "front";
                break;
            case LEFT: side = "left";
                break;
            case BACK: side = "back";
                break;
            case RIGHT: side = "right";
                break;
            case TOP: side = "top";
                break;
            case BOTTOM: side = "bottom";
                break;
            case INSIDE: side = "inside";
                break;
        }

        switch(hardwareType) {
            case DC_MOTOR: type = "dcMotor";
                break;
            case SERVO: type = "servo";
                break;
            case CONTINUOUS_ROTATION_SERVO: type = "crServo";
                break;
            case ACCELERATION_SENSOR: type = "accelerationSensor";
                break;
            case COLOR_SENSOR: type = "colorSensor";
                break;
            case COMPASS_SENSOR: type = "compassSensor";
                break;
            case DISTANCE_SENSOR: type = "distanceSensor";
                break;
            case GYRO_SENSOR: type = "gyroSensor";
                break;
            case I2C_DEVICE: type = "i2cDevice";
                break;
            case IR_SENSOR: type = "irSensor";
                break;
            case LIGHT_SENSOR: type = "lightSensor";
                break;
            case OPTICAL_DISTANCE_SENSORY: type = "opticalDistanceSensor";
                break;
            case TOUCH_SENSOR: type = "touchSensor";
                break;
            case ULTRASONIC_SENSOR: type = "ultrasonicSensor";
                break;
            case VOLTAGE_SENSOR: type = "voltageSensor";
                break;
        }

        return purpose + "-" + type + "@" + side + "-" + position;
    }
}
