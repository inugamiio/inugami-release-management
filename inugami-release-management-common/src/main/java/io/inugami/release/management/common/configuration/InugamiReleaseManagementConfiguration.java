package io.inugami.release.management.common.configuration;

import io.inugami.release.management.common.utils.ClockUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class InugamiReleaseManagementConfiguration {

    @Bean
    public Clock clock() {
        return ClockUtil.getClock();
    }
}
