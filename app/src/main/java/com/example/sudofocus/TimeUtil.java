package com.example.sudofocus;

import java.util.StringTokenizer;

public class TimeUtil {

    private int minutesInt,secondsInt;
    private String minutes,seconds;

    public TimeUtil(String minutes,String seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public boolean isValid() {
        try {
            minutesInt = Integer.valueOf(minutes);
            secondsInt = Integer.valueOf(seconds);
            if(secondsInt > 60) {
                throw new Exception();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public long toMillis() {
        return (minutesInt*60000) + (secondsInt*1000);
    }

}
