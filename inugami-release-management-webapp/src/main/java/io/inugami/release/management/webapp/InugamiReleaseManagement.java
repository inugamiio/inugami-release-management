package io.inugami.release.management.webapp;

import io.inugami.monitoring.springboot.config.InugamiMonitoringConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "io.inugami.release.management",
        InugamiMonitoringConfig.INUGAMI_MONITORING_CONFIG
})
@SpringBootApplication
public class InugamiReleaseManagement {
    public static void main(final String[] args) {
        SpringApplication.run(InugamiReleaseManagement.class, args);
    }

}
