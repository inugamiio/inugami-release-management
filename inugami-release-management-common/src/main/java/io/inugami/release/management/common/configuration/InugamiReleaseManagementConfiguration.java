package io.inugami.release.management.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.inugami.api.marshalling.JsonMarshaller;
import io.inugami.release.management.common.utils.ClockUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Clock;

@Configuration
public class InugamiReleaseManagementConfiguration {

    @Bean
    public Clock clock() {
        return ClockUtil.getClock();
    }

    @Primary
    @Bean
    public ObjectMapper defaultObjectMapper() {
        return JsonMarshaller.getInstance().getDefaultObjectMapper();
    }
}
