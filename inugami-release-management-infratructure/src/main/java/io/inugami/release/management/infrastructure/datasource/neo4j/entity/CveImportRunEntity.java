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

import io.inugami.release.management.api.domain.cve.dto.CveImportRunStatus;
import io.inugami.release.management.api.domain.cve.dto.CveImporterStatusDTO;
import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
@Builder(toBuilder = true)
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Node("CveImportRun")
public class CveImportRunEntity {
    @ToString.Include
    @Id
    @GeneratedValue
    private Long id;

    @ToString.Include
    @EqualsAndHashCode.Include
    private String             uid;
    @ToString.Include
    private LocalDateTime      startDate;
    @ToString.Include
    private LocalDateTime      doneDate;
    @ToString.Include
    private CveImportRunStatus status;

    private String message;

    @Relationship(type = "HAS_IMPORT_STATUS")
    private List<CveImporterStatusDTO> importerStatus;
}
