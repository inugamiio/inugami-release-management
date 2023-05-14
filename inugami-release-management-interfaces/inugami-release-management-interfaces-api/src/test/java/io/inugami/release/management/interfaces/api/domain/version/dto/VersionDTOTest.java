package io.inugami.release.management.interfaces.api.domain.version.dto;

import io.inugami.commons.test.dto.AssertDtoContext;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static io.inugami.commons.test.UnitTestHelper.assertDto;
import static io.inugami.release.management.interfaces.api.common.Constants.XXX;
import static org.assertj.core.api.Assertions.assertThat;

class VersionDTOTest {


    @Test
    void versionDTO() {
        assertDto(new AssertDtoContext<VersionDTO>()
                          .toBuilder()
                          .objectClass(VersionDTO.class)
                          .fullArgConstructorRefPath("interface/domain/version/dto/versionDTOTest/fullArgConstructorRefPath.json")
                          .getterRefPath("interface/domain/version/dto/versionDTOTest/getterRefPath.json")
                          .toStringRefPath("interface/domain/version/dto/versionDTOTest/toStringRefPath.txt")
                          .cloneFunction(instance -> instance.toBuilder().build())
                          .noArgConstructor(() -> new VersionDTO())
                          .fullArgConstructor(VersionDTOTest::buildDataSet)
                          .noEqualsFunction(VersionDTOTest::notEquals)
                          .checkSetters(true)
                          .build());
    }


    static void notEquals(final VersionDTO value) {
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
        assertThat(value).isNotEqualTo(value.toBuilder().type(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().type(XXX).build().hashCode());
    }

    public static VersionDTO buildDataSet() {
        return VersionDTO.builder()
                         .id(1L)
                         .groupId("io.inugami.release.management")
                         .artifactId("inugami-release-management-common")
                         .version("1.0.0-SNAPSHOT")
                         .type("jar")
                         .buildHash()
                         .dependencies(Set.of(VersionDTO.builder()
                                                        .id(2L)
                                                        .groupId("org.slf4j")
                                                        .artifactId("slf4j-api")
                                                        .version("1.7.36")
                                                        .type("jar")
                                                        .build()))
                         .testDependencies(Set.of(VersionDTO.builder()
                                                            .id(3L)
                                                            .groupId("io.inugami")
                                                            .artifactId("inugami_commons_test")
                                                            .version("3.2.0")
                                                            .type("jar")
                                                            .build()))
                         .projectDependencies(Set.of(VersionDTO.builder()
                                                               .id(1L)
                                                               .groupId("io.inugami.release.management")
                                                               .artifactId("inugami-release-management-api")
                                                               .version("1.0.0-SNAPSHOT")
                                                               .type("jar")
                                                               .build()))
                         .build();
    }
}