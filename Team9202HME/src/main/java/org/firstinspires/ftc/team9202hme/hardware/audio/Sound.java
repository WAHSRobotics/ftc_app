package org.firstinspires.ftc.team9202hme.hardware.audio;


import org.firstinspires.ftc.team9202hme.R;

public class Sound {
    public static final Sound YOU_TOOK_THE_PEEPO = new Sound(R.raw.you_took_the_peepo);
//    public static final Sound NASA_PEEPO = new Sound(0);
//    public static final Sound THERES_PEAS_IN_THERE = new Sound(0);
//    public static final Sound GET_AWAY_FROM_MY_CROISSANT = new Sound(0);
//    public static final Sound THASSA_PARKING = new Sound(0);
//    public static final Sound THERES_A_CASSERPIRRER_IN_YER_KIBBLE = new Sound(0);
//    public static final Sound NICE_CARRIRPIRRER = new Sound(0);
//    public static final Sound MOK_DZZZ_FUR_PSHWHSHSHSHWHSFSHFHSH = new Sound(0);
//    public static final Sound YOU_DONT_BITE_A_BALLOON = new Sound(0);
//    public static final Sound THASSA_SPIDER_ERB = new Sound(0);
//    public static final Sound THASSA_ROCK = new Sound(0);
//    public static final Sound MY_NANA_IN_DA_POOL_IN_THE_STREET = new Sound(0);
//
//    public static final Sound NANALAN_THEME_SONG = new Sound(0);

    private int resourceId;

    private Sound(int resourceId) {
        this.resourceId = resourceId;
    }

    int getResourceId() {
        return resourceId;
    }
}
