package io.inugami.release.management.interfaces.api.domain.version.dto;

import io.inugami.commons.test.dto.AssertDtoContext;
import org.junit.jupiter.api.Test;

import static io.inugami.commons.test.UnitTestHelper.assertDto;
import static io.inugami.release.management.interfaces.api.common.Constants.XXX;
import static org.assertj.core.api.Assertions.assertThat;
class VersionLightDTOTest {

    @Test
    void versionLightDTO() {
        assertDto(new AssertDtoContext<VersionLightDTO>()
                          .toBuilder()
                          .objectClass(VersionLightDTO.class)
                          .fullArgConstructorRefPath("interface/domain/version/dto/versionLightDTOTest/fullArgConstructorRefPath.json")
                          .getterRefPath("interface/domain/version/dto/versionLightDTOTest/getterRefPath.json")
                          .toStringRefPath("interface/domain/version/dto/versionLightDTOTest/toStringRefPath.txt")
                          .cloneFunction(instance -> instance.toBuilder().build())
                          .noArgConstructor(() -> new VersionLightDTO())
                          .fullArgConstructor(VersionLightDTOTest::buildDataSet)
                          .noEqualsFunction(VersionLightDTOTest::notEquals)
                          .checkSetters(true)
                          .build());
    }


    static void notEquals(final VersionLightDTO value) {
        assertThat(value).isNotEqualTo(value.toBuilder());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().groupId(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().groupId(XXX).build().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().artifactId(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().artifactId(XXX).build().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().version(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().version(XXX).build().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().packaging(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().packaging(XXX).build().hashCode());
    }

    public static VersionLightDTO buildDataSet() {
        return VersionLightDTO.builder()
                         .id(1L)
                         .groupId("io.inugami.release.management")
                         .artifactId("inugami-release-management-common")
                         .version("1.0.0-SNAPSHOT")
                         .packaging("jar")
                         .build();
    }
}