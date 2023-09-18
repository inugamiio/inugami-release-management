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

import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import io.inugami.release.management.infrastructure.datasource.neo4j.entity.VersionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Mapper(uses = {VersionRulesEntityMapper.class})
public interface VersionEntityMapper {

    @Mapping(target = "name", source = "hash")
    VersionEntity convertToEntity(VersionDTO dto);

    @Mapping(target = "hash", source = "name")
    @Mapping(target = "dependencies", expression = "java(convertSubEntity(entity.getDependencies()))")
    @Mapping(target = "testDependencies", expression = "java(convertSubEntity(entity.getTestDependencies()))")
    @Mapping(target = "projectDependencies", expression = "java(convertSubEntity(entity.getProjectDependencies()))")
    VersionDTO convertToDto(VersionEntity entity);

    default Set<VersionDTO> convertSubEntity(final Collection<VersionEntity> entities) {

        final Set<VersionDTO> result = new LinkedHashSet<>();
        if (entities != null) {
            for (final VersionEntity versionEntity : entities) {
                result.add(VersionDTO.builder()
                                     .id(versionEntity.getId())
                                     .groupId(versionEntity.getGroupId())
                                     .artifactId(versionEntity.getArtifactId())
                                     .version(versionEntity.getVersion())
                                     .packaging(versionEntity.getPackaging())
                                     .build());
            }
        }
        return result;
    }

    @Mapping(target = "hash", source = "name")
    @Mapping(target = "dependencies", ignore = true)
    @Mapping(target = "testDependencies", ignore = true)
    @Mapping(target = "projectDependencies", ignore = true)
    VersionDTO convertToDtoLight(VersionEntity entity);
}

