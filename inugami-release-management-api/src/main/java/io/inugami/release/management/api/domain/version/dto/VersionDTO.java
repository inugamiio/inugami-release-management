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

import lombok.*;

import java.util.Set;

import static io.inugami.release.management.api.common.Constants.DOUBLE_DOT;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Setter
@Getter
public class VersionDTO {

    private Long   id;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String groupId;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String artifactId;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String version;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String packaging;

    private String  hash;
    private Boolean currentProject;

    private Set<VersionDTO> dependencies;
    private Set<VersionDTO> testDependencies;
    private Set<VersionDTO> projectDependencies;

    public static class VersionDTOBuilder {
        public VersionDTOBuilder buildHash() {
            this.hash = String.join(DOUBLE_DOT, groupId, artifactId, version, packaging);
            return this;
        }
    }
}
