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

import io.inugami.release.management.api.common.dto.PageDTO;
import io.inugami.release.management.api.domain.version.IVersionService;
import io.inugami.release.management.interfaces.api.common.dto.Order;
import io.inugami.release.management.interfaces.api.domain.version.VersionRestClient;
import io.inugami.release.management.interfaces.api.domain.version.dto.VersionDTO;
import io.inugami.release.management.interfaces.api.domain.version.dto.VersionLightDTO;
import io.inugami.release.management.interfaces.core.domain.version.mapper.VersionDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class VersionRestController implements VersionRestClient {
    // =================================================================================================================
    // ATTRIBUTES
    // =================================================================================================================
    private final IVersionService  versionService;
    private final VersionDTOMapper versionDTOMapper;


    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @Override
    public VersionDTO create(final VersionDTO versionDTO) {
        var result = versionService.create(versionDTOMapper.convertToApiDto(versionDTO));
        return versionDTOMapper.convertToRestDto(result);
    }

    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Override
    public List<VersionLightDTO> getAllVersions(final int page, final int pageSize, final Order order) {
        var result = versionService.getAllVersions(PageDTO.builder()
                                                          .page(page)
                                                          .pageSize(pageSize)
                                                          .order(order == Order.ASC ? PageDTO.Order.ASC : PageDTO.Order.DESC)
                                                          .build());
        return result.stream()
                     .map(versionDTOMapper::convertToVersionLightDTO)
                     .toList();
    }

    @Override
    public VersionDTO getVersion(final long id) {
        return versionDTOMapper.convertToRestDto(versionService.getVersion(id));
    }

    @Override
    public VersionDTO getVersion(final String groupId, final String artifactId, final String version, final String packaging) {
        return versionDTOMapper.convertToRestDto(versionService.getVersion(groupId, artifactId, version, packaging));
    }
}
