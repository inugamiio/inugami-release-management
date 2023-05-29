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
package io.inugami.release.management.api.domain.cve.dto;

import io.inugami.release.management.api.common.dto.VersionRulesDTO;
import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import lombok.*;

import java.util.List;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Setter
@Getter
public class ProductAffectedDTO {
    private Long                  id;
    @ToString.Include
    @EqualsAndHashCode.Include
    private List<VersionRulesDTO> rules;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String                product;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String                vendor;
    private List<VersionDTO>      affectVersions;
}
