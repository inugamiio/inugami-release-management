package io.inugami.release.management.common.utils;

import java.time.Clock;

public final class ClockUtil {
    public static Clock getClock(){
        return Clock.systemDefaultZone();
    }
}
