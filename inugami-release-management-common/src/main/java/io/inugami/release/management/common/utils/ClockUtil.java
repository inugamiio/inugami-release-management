package io.inugami.release.management.common.utils;

import lombok.experimental.UtilityClass;

import java.time.Clock;

@UtilityClass
public class ClockUtil {
    public static Clock getClock(){
        return Clock.systemDefaultZone();
    }
}
