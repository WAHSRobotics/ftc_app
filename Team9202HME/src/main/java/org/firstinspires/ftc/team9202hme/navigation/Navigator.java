package org.firstinspires.ftc.team9202hme.navigation;


import com.vuforia.HINT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.team9202hme.R;
import org.firstinspires.ftc.team9202hme.math.vector.Vector3;

public class Navigator {
    private VuforiaLocalizer localizer;
    private VuforiaLocalizer.Parameters vuforiaSettings;
    private VuforiaTrackables targets;

    public Navigator(PhoneCamera phoneCamera, int maxSimultaneousImageTargets, boolean showCameraFeedbackOnPhone) {
        if(showCameraFeedbackOnPhone) {
            vuforiaSettings = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        } else {
            vuforiaSettings = new VuforiaLocalizer.Parameters();
        }

        switch(phoneCamera) {
            case SCREEN: vuforiaSettings.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
                break;
            case BACK: vuforiaSettings.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
                break;
        }

        vuforiaSettings.vuforiaLicenseKey = "AYrzM+7/////AAAAGflN33oLXURIiZiOHPt5MZA2iv50tePz4bz21" +
                "btpbPci5G9i+R0v4r0iNxOOPL5mkqRO/EjcBv4TYHnKqEahIIt35JZdsc" +
                "PxAp0uHcpSONmWqRcFNglob05nEiqNkTAQKG7Ux9AhjJqZp6R+lAiCKB1" +
                "/Ht9pNZ+qK+xNE1iEtL9g708JbjmdsqT+KYCA7Rup0dqdeMGieexgSQUK" +
                "fWKIk3w/Sap1W83He60GW0UGnSUzM81fBu05Oqkl1QiAWbb9TpWff9/Yf" +
                "OJZPSCfdfErIMBuYtYgsJl5xZEtv57u6EwrqsrlwvudD1GciBrIIMmnqM" +
                "eIQu9EM5PD0dI9Oi+3jn8RPEfKauoAGDRIpUlfoI+2";
        vuforiaSettings.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        Vuforia.setHint(HINT.HINT_MAX_SIMULTANEOUS_IMAGE_TARGETS, maxSimultaneousImageTargets);
    }

    private VuforiaTrackableDefaultListener getTargetListener(ImageTarget imageTarget) {
        VuforiaTrackable target = targets.get(imageTarget.ordinal());

        return (VuforiaTrackableDefaultListener) target.getListener();
    }

    public void init() {
        localizer = ClassFactory.createVuforiaLocalizer(vuforiaSettings);

        targets = localizer.loadTrackablesFromAsset("FTC_2016-17");
        targets.get(0).setName("Wheels");
        targets.get(1).setName("Tools");
        targets.get(2).setName("Legos");
        targets.get(3).setName("Gears");

        targets.activate();
    }

    public boolean canSeeTarget(ImageTarget target) {
        return getTargetListener(target).getPose() != null;
    }

    public Vector3 getRelativeTargetLocation(ImageTarget target) {
        VuforiaTrackableDefaultListener listener = getTargetListener(target);
        OpenGLMatrix pose = listener.getPose();

        Vector3 result = new Vector3();

        if(pose != null) {
            VectorF translation = pose.getTranslation();

            result.x = translation.get(0);
            result.y = translation.get(1);
            result.z = translation.get(2);

            return result;
        } else return new Vector3();
    }
}
