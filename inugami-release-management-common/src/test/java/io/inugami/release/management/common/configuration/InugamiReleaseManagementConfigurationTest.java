package io.inugami.release.management.common.configuration;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InugamiReleaseManagementConfigurationTest {

    @Test
    void clock_nominal() {
        assertThat(buildConfig().clock()).isNotNull();
    }

    @Test
    void defaultObjectMapper_nominal() {
        assertThat(buildConfig().defaultObjectMapper()).isNotNull();
    }

    InugamiReleaseManagementConfiguration buildConfig() {
        return new InugamiReleaseManagementConfiguration();
    }
}