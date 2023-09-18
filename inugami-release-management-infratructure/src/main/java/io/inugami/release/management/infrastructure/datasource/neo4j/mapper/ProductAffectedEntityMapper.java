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
package io.inugami.release.management.infrastructure.datasource.neo4j.mapper;

import io.inugami.release.management.api.domain.cve.dto.ProductAffectedDTO;
import io.inugami.release.management.infrastructure.datasource.neo4j.entity.ProductAffectedEntity;
import org.mapstruct.Mapper;

@Mapper(uses = {VersionEntityMapper.class, VersionRulesEntityMapper.class})
public interface ProductAffectedEntityMapper {
    ProductAffectedEntity convertToEntity(ProductAffectedDTO dto);


}
