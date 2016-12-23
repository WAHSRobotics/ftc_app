package org.firstinspires.ftc.team9202hme.navigation;


public class TargetNotFoundException extends Exception {
    private ImageTarget imageTarget;

    public TargetNotFoundException(String message, ImageTarget target) {
        super(message);
        this.imageTarget = target;
    }

    public ImageTarget getImageTarget() {
        return imageTarget;
    }
}
