package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.robotcore.util.Range;

public class PowerScale {
    private double minimumPower;
    private double maximumPower;
    private double scaleInterval;

    public PowerScale(double min, double max) {
        new PowerScale(min, max, 5);
    }

    public PowerScale(double min, double max, double scaleInterval) {
        if(min < 0.0 || min > 1.0) {
            throw new IllegalArgumentException("Argument 'min' should be between 0.0 and 1.0");
        }

        if(max < 0.0 || max > 1.0) {
            throw new IllegalArgumentException("Argument 'max' should be between 0.0 and 1.0");
        }

        if(max < min || min > max) {
            throw new IllegalArgumentException("Argument 'max' should be greater than argument 'min'.");
        }

        this.minimumPower = min;
        this.maximumPower = max;
        this.scaleInterval = scaleInterval;
    }

    public double scalePower(double power) {
        boolean negative = false;

        //Change power to be positive, so the following function doesn't be stupid
        if(power < 0) {
            negative = true;
            power = Math.abs(power);
        }

        //Does some math... like y = x^s^x or something like that, where y is scaledPower,
        //x is power, and s is this.scaleInterval and whatnot
        //It makes a fancy function that is very nice
        double scaledPower = Math.pow(power, Math.pow(this.scaleInterval, power));

        //Round scaledPower to 2 significant features
        scaledPower = setSignificantFigures(scaledPower, 2);

        //Make sure that my math isn't terrible, and that it didn't somehow exceed maximum power
        scaledPower = Range.clip(scaledPower, 0.0, this.maximumPower);

        //Turn power back into negative to keep the same direction
        //and also make sure I'm not returning below the minimum power
        if(negative) {
            return scaledPower >= -this.minimumPower ? -scaledPower : 0.0;
        } else {
            return scaledPower >= this.minimumPower ? scaledPower : 0.0;
        }

    }

    private double setSignificantFigures(double value, int significantFigures) {
        double multiplier = Math.pow(10, significantFigures);
        return Math.round(value * multiplier) / multiplier;
    }

    public double getScaleInterval() {
        return scaleInterval;
    }

    public void setScaleInterval(double scaleInterval) {
        this.scaleInterval = scaleInterval;
    }

    public double getMinimumPower() {
        return minimumPower;
    }

    public void setMinimumPower(double minimumPower) {
        this.minimumPower = minimumPower;
    }

    public double getMaximumPower() {
        return maximumPower;
    }

    public void setMaximumPower(double maximumPower) {
        this.maximumPower = maximumPower;
    }
}
