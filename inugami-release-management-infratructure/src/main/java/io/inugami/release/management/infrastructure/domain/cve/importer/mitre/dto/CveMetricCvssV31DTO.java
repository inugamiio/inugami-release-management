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
package io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto;

import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Setter
@Getter
public class CveMetricCvssV31DTO {
    private String attackComplexity;
    private String attackVector;
    private String availabilityImpact;
    @EqualsAndHashCode.Include
    @ToString.Include
    private Double baseScore;
    @EqualsAndHashCode.Include
    @ToString.Include
    private String baseSeverity;
    private String confidentialityImpact;
    private String integrityImpact;
    private String privilegesRequired;
    private String scope;
    private String userInteraction;
    private String vectorString;
    private String version;
}
