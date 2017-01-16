package org.firstinspires.ftc.team9202hme.program;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.team9202hme.hardware.driving.DriveTrain;
import org.firstinspires.ftc.team9202hme.hardware.gathering.Gatherer;
import org.firstinspires.ftc.team9202hme.hardware.shooting.Shooter;

/**
 * Interface for creating programs to be used during
 * the autonomous period of the competition
 *
 * @author Nathaniel Glover
 */
public abstract class AutonomousProgram {
    /**
     * The two possible sides of the field
     *
     * @author Nathaniel Glover
     */
    public enum FieldSide {
        RED, BLUE
    }

    /**
     * The LinearOpMode that will be running this program. Used for accessing
     * things like HardwareMap, Gamepad, and Telemetry
     */
    protected LinearOpMode opMode;
    /**
     * The side of the field that the robot will be on when this program is run
     */
    protected FieldSide fieldSide;

    /**
     * Initializes protected members so that
     * they may be used by subclasses
     *
     * @param opMode The LinearOpMode that will be running this program
     * @param fieldSide The side of the field that the robot will be on
     *                  when this program is run
     */
    public AutonomousProgram(LinearOpMode opMode, FieldSide fieldSide) {
        this.opMode = opMode;
        this.fieldSide = fieldSide;
    }

    /**
     * Runs the program. <b>NOTE:</b> Do not catch the InterruptedException.
     * This method is intended for use in {@link LinearOpMode#runOpMode()},
     * which will properly handle the InterruptedException should it occur.
     *
     * @throws InterruptedException
     */
    public abstract void run() throws InterruptedException;
}
