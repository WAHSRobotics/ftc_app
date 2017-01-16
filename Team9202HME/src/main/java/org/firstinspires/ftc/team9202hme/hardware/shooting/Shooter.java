package org.firstinspires.ftc.team9202hme.hardware.shooting;


import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.team9202hme.hardware.HardwareComponent;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.TeleOpProgram;

/**
 * Hardware interface for controlling the shooting mechanism
 * of a robot
 *
 * @author Nathaniel Glover
 */
public abstract class Shooter extends HardwareComponent {
    /**
     * Controls the shooting mechanism based on inputs
     * from a gamepad. This is meant to be
     * used in {@link TeleOpProgram#loop()}
     *
     * @param controller The gamepad that will be used to control the robot during
     *                   TeleOp. It should ideally come from the OpMode in the
     *                   program that will be controlling the robot, as either
     *                   gamepad1 or gamepad2
     *
     * @see TeleOpProgram
     */
    public abstract void shootControlled(Gamepad controller);

    /**
     * Activates the shooting mechanism so that it
     * will fire an object
     * <p>
     * <b>NOTE:</b> Do not catch the InterruptedException.
     * This method is intended for use in {@link AutonomousProgram#run()},
     * which will properly handle the InterruptedException should it occur.
     *
     * @throws InterruptedException
     *
     * @see AutonomousProgram
     */
    public abstract void shoot() throws InterruptedException;
}
