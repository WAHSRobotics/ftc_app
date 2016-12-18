package org.firstinspires.ftc.team9202hme.math;


import com.qualcomm.robotcore.util.Range;

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

    public PowerScale() {
        init(0.05, 1.0, 5.0);
    }

    public PowerScale(double min, double max) {
        init(min, max, 5.0);
    }

    public PowerScale(double min, double max, double scaleInterval) {
        init(min, max, scaleInterval);
    }

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
