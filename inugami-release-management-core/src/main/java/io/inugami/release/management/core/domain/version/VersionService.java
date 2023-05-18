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
package io.inugami.release.management.core.domain.version;

import io.inugami.release.management.api.common.dto.PageDTO;
import io.inugami.release.management.api.domain.version.IVersionService;
import io.inugami.release.management.api.domain.version.IVersionDao;
import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import io.inugami.release.management.api.domain.version.exception.VersionError;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.inugami.api.exceptions.Asserts.*;

@RequiredArgsConstructor
@Service
public class VersionService implements IVersionService {

    public static final String      DEFAULT_TYPE = "jar";
    // =================================================================================================================
    // ATTRIBUTES
    // =================================================================================================================
    private final       IVersionDao versionDao;

    @Value("${inugami.release.management.domain.version.defaultPageSize:50}")
    private int defaultPageSize;


    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @Override
    public VersionDTO create(final VersionDTO versionDTO) {
        assertNotNull(VersionError.CREATE_DATA_REQUIRED, versionDTO);
        assertModel(() -> assertNotEmpty(VersionError.CREATE_GROUP_ID_REQUIRED, versionDTO.getGroupId()),
                    () -> assertNotEmpty(VersionError.CREATE_ARTIFACT_ID_REQUIRED, versionDTO.getArtifactId()),
                    () -> assertNotEmpty(VersionError.CREATE_VERSION_REQUIRED, versionDTO.getVersion()));

        return versionDao.save(versionDTO);
    }


    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Override
    public List<VersionDTO> getAllVersions(final PageDTO page) {
        PageDTO currentPage = page != null ? page : PageDTO.builder()
                                                           .pageSize(defaultPageSize)
                                                           .build();

        final List<VersionDTO> result = versionDao.getAll(page);
        assertNotEmpty(VersionError.READ_NOT_FOUND_VERSIONS, result);
        return result;
    }

    @Override
    public VersionDTO getVersion(final String groupId, final String artifactId, final String version, final String type) {
        assertNotNull(VersionError.READ_GROUP_ID_REQUIRED, groupId);
        assertNotNull(VersionError.READ_ARTIFACT_ID_REQUIRED, artifactId);
        assertNotNull(VersionError.READ_VERSION_REQUIRED, version);

        final String     currentType = type == null ? DEFAULT_TYPE : type;
        final VersionDTO result      = versionDao.getVersion(groupId, artifactId, version, currentType);
        assertNotNull(VersionError.READ_VERSION_NOT_FOUND.addDetail("{0}:{1}:{2}:{4}", groupId, artifactId, version, currentType), result);
        return result;
    }

    @Override
    public VersionDTO getVersion(final long id) {
        assertHigherOrEquals(VersionError.READ_INVALID_ID, 0, id);
        final VersionDTO result = versionDao.getVersion(id);
        assertNotNull(VersionError.READ_VERSION_NOT_FOUND_WITH_ID.addDetail("{0}", id), result);
        return result;
    }


    // =================================================================================================================
    // UPDATE
    // =================================================================================================================
    @Override
    public VersionDTO update(final VersionDTO versionDTO) {
        return null;
    }

    // =================================================================================================================
    // DELETE
    // =================================================================================================================
    @Override
    public void delete(final String groupId, final String artifactId, final String version, final String type) {

    }

    @Override
    public void delete(final long id) {

    }
}
