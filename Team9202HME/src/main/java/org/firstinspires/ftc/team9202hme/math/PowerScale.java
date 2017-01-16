package org.firstinspires.ftc.team9202hme.math;


import com.qualcomm.robotcore.util.Range;

/**
 * A class for scaling motor power so that driving
 * is smoother
 * <p>
 * It currently uses the function
 * y = (u - l) (x^(su)^x) + l, where y is the output
 * power, x is the input power, u is the max power,
 * l is the minimum power, and s is the scale interval.
 * The scale interval determines how much the motor power will
 * be affected by the scale. It's default value is 5.0
 *
 * @author Nathaniel Glover
 */
public class PowerScale {
    private double minimumPower;
    private double maximumPower;
    private double scaleInterval;

    private void init(double absoluteMin, double absoluteMax, double scaleInterval) {
        if(absoluteMin < 0.0 || absoluteMin > 1.0) {
            throw new IllegalArgumentException("Argument 'min' should be between 0.0 and 1.0");
        }

        if(absoluteMax < 0.0 || absoluteMax > 1.0) {
            throw new IllegalArgumentException("Argument 'max' should be between 0.0 and 1.0");
        }

        if(absoluteMax < absoluteMin || absoluteMin > absoluteMax) {
            throw new IllegalArgumentException("Argument 'max' should be greater than argument 'min'.");
        }

        this.minimumPower = absoluteMin;
        this.maximumPower = absoluteMax;
        this.scaleInterval = scaleInterval;
    }

    /**
     * Constructs a PowerScale with default values
     */
    public PowerScale() {
        init(0.0, 1.0, 5.0);
    }

    /**
     * Constructs a PowerScale with desired minimum and maximum powers
     *
     * @param min The minimum power, ranging from 0.0 to 1.0
     * @param max The maximum power, ranging from 0.0 to 1.0, and
     *            should be greater than minimum power
     */
    public PowerScale(double min, double max) {
        init(min, max, 5.0);
    }

    /**
     * Constructs a PowerScale with desired minimum and maximum powers,
     * and desired scale interval
     *
     * @param min The minimum power, ranging from 0.0 to 1.0
     * @param max The maximum power, ranging from 0.0 to 1.0, and
     *            should be greater than minimum power
     * @param scaleInterval Determines how much the {@link PowerScale#scalePower(double)}
     *                      will affect the input power. It be greater than 1, and anything
     *                      over 10 is not recommended for smooth driving
     */
    public PowerScale(double min, double max, double scaleInterval) {
        init(min, max, scaleInterval);
    }

    /**
     * Scales the input power based on the function
     * y = (u - l) (x^(su)^x) + l, where y is the output
     * power, x is the input power, u is the max power,
     * l is the minimum power, and s is the scale interval.
     * The scale interval determines how much the motor power will
     * be affected by the scale. It's default value is 5.0
     *
     * @param power The power to be scaled
     * @return The power after it has been scaled
     */
    public double scalePower(double power) {
        boolean negative = false;

        //Change power to be positive, so the following function doesn't be stupid
        if(power < 0) {
            negative = true;
            power = Math.abs(power);
        }

        //Does some math... like y = (u - l) (x^(su)^x) + l or something like that, where y is scaledPower,
        //x is power, u is this.maximumPower, l is this.minimumPower, and s is this.scaleInterval
        //It makes a fancy function that is very nice
        double scaledPower = (this.maximumPower - this.minimumPower) * Math.pow(power,
                Math.pow(this.scaleInterval * this.maximumPower, power)) + this.minimumPower;

        //Make sure that my math isn't terrible, and that it didn't somehow exceed maximum power
        scaledPower = Range.clip(scaledPower, 0.0, 1.0);

        //Just makes sure that zero (or anything below this.minimumPower) isn't set to this.minimumPower by fancy function
        if(this.minimumPower >= power) {
            scaledPower = 0.0;
        }

        //Turn power back into negative to keep the specified direction
        return negative ? -scaledPower : scaledPower;
    }
}
