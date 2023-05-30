package io.inugami.release.management.common.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClockUtilTest {

    @Test
    void getClock(){
        assertThat(ClockUtil.getClock()).isNotNull();
    }
}