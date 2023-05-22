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
package io.inugami.release.management.infrastructure.datasource.neo4j.entity;

import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
@Builder(toBuilder = true)
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Node("Version")
public class VersionEntity {
    @Id
    @GeneratedValue
    private Long id;

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

    @Relationship(type = "DEPENDENCY")
    private Set<VersionEntity> dependencies;

    @Relationship(type = "HAS_TEMPLATE")
    private Set<VersionEntity> testDependencies;

    @Relationship(type = "PROJECT_DEPENDENCY")
    private Set<VersionEntity> projectDependencies;
}
