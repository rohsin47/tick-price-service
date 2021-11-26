package org.soloactive.tick.util;

import java.time.Duration;
import java.time.ZonedDateTime;

public class DateUtils {

    public static boolean isValid(ZonedDateTime currentTime, ZonedDateTime createdTime) {
        long seconds = Duration.between(currentTime, createdTime).abs().getSeconds();
        return seconds < 60;
    }

    public static boolean isValid(ZonedDateTime createdTime) {
        ZonedDateTime currentTime = ZonedDateTime.now();
        return isValid(currentTime, createdTime);
    }
}
