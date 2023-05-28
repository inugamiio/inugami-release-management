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
package io.inugami.release.management.interfaces.core.domain.version;

import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class VersionDataset {


    public static VersionDTO versionDTOLight() {
        return VersionDTO.builder()
                         .id(1L)
                         .groupId("io.inugami.release.management")
                         .artifactId("inugami-release-management-common")
                         .version("1.0.0-SNAPSHOT")
                         .packaging("jar")
                         .buildHash()
                         .build();
    }

    public static VersionDTO versionDTO() {
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
