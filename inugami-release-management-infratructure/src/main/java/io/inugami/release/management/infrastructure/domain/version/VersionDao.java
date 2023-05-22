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
package io.inugami.release.management.infrastructure.domain.version;

import io.inugami.release.management.api.common.dto.PageDTO;
import io.inugami.release.management.api.domain.version.IVersionDao;
import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import io.inugami.release.management.infrastructure.datasource.neo4j.entity.VersionEntity;
import io.inugami.release.management.infrastructure.datasource.neo4j.mapper.VersionEntityMapper;
import io.inugami.release.management.infrastructure.datasource.neo4j.repository.VersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VersionDao implements IVersionDao {

    private final VersionRepository   versionRepository;
    private final VersionEntityMapper versionEntityMapper;

    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @Override
    public VersionDTO save(final VersionDTO dto) {
        final VersionEntity entity = versionEntityMapper.convertToEntity(dto);
        final VersionEntity result = versionRepository.save(entity);
        return versionEntityMapper.convertToDto(result);
    }


    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Override
    public List<VersionDTO> getAll(final PageDTO page) {
        final PageDTO.Order       order    = page.getOrder() == null ? PageDTO.Order.ASC : page.getOrder();
        final PageRequest         pageable = PageRequest.of(page.getPage(), page.getPageSize(), order == PageDTO.Order.ASC ? Sort.Direction.ASC : Sort.Direction.DESC);
        final Page<VersionEntity> result   = versionRepository.findAll(pageable);

        return result.get()
                     .map(versionEntityMapper::convertToDto)
                     .toList();
    }

    @Override
    public VersionDTO getVersion(final String groupId, final String artifactId, final String version, final String packaging) {
        final Optional<VersionEntity> result = versionRepository.findByGroupIdAndArtifactIdAndVersionAndPackaging(groupId, artifactId, version, packaging);
        return convertToDto(result);
    }

    @Override
    public VersionDTO getVersion(final long id) {
        final Optional<VersionEntity> result = versionRepository.findById(id);
        return convertToDto(result);
    }

    private VersionDTO convertToDto(final Optional<VersionEntity> entity) {
        return entity.isPresent() ? versionEntityMapper.convertToDto(entity.get()) : null;
    }

    // =================================================================================================================
    // UPDATE
    // =================================================================================================================


    // =================================================================================================================
    // DELETE
    // =================================================================================================================
}
