/* --------------------------------------------------------------------
 *  Inugami
 * --------------------------------------------------------------------
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.inugami.release.management.api.domain.version.dto;

import io.inugami.commons.test.dto.AssertDtoContext;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static io.inugami.commons.test.UnitTestHelper.assertDto;
import static io.inugami.release.management.api.common.Constants.XXX;
import static org.assertj.core.api.Assertions.assertThat;
class VersionDTOTest {


    @Test
    void versionDTO() {
        assertDto(new AssertDtoContext<VersionDTO>()
                          .toBuilder()
                          .objectClass(VersionDTO.class)
                          .fullArgConstructorRefPath("api/domain/version/dto/versionDTOTest/fullArgConstructorRefPath.json")
                          .getterRefPath("api/domain/version/dto/versionDTOTest/getterRefPath.json")
                          .toStringRefPath("api/domain/version/dto/versionDTOTest/toStringRefPath.txt")
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
        assertThat(value).isNotEqualTo(value.toBuilder().packaging(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().packaging(XXX).build().hashCode());
    }

    public static VersionDTO buildDataSet() {
        return VersionDTO.builder()
                         .id(1L)
                         .groupId("io.inugami.release.management")
                         .artifactId("inugami-release-management-common")
                         .version("1.0.0-SNAPSHOT")
                         .packaging("jar")
                         .buildHash()
                         .dependencies(Set.of(VersionDTO.builder()
                                                        .id(2L)
                                                        .groupId("org.slf4j")
                                                        .artifactId("slf4j-api")
                                                        .version("1.7.36")
                                                        .packaging("jar")
                                                        .build()))
                         .testDependencies(Set.of(VersionDTO.builder()
                                                            .id(3L)
                                                            .groupId("io.inugami")
                                                            .artifactId("inugami_commons_test")
                                                            .version("3.2.0")
                                                            .packaging("jar")
                                                            .build()))
                         .projectDependencies(Set.of(VersionDTO.builder()
                                                               .id(1L)
                                                               .groupId("io.inugami.release.management")
                                                               .artifactId("inugami-release-management-api")
                                                               .version("1.0.0-SNAPSHOT")
                                                               .packaging("jar")
                                                               .build()))
                         .build();
    }
}