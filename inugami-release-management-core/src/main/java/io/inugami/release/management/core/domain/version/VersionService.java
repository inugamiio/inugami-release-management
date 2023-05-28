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
import io.inugami.release.management.api.domain.version.IVersionDao;
import io.inugami.release.management.api.domain.version.IVersionService;
import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import io.inugami.release.management.api.domain.version.exception.VersionError;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.inugami.api.exceptions.Asserts.*;

@Setter(AccessLevel.PACKAGE)
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
                    () -> assertNotEmpty(VersionError.CREATE_VERSION_REQUIRED, versionDTO.getVersion()),
                    () -> assertNullOrEmpty(VersionError.CREATE_VERSION_DEPENDENCIES_SHOULD_BE_EMPTY, versionDTO.getDependencies()),
                    () -> assertNullOrEmpty(VersionError.CREATE_VERSION_TEST_DEPENDENCIES_SHOULD_BE_EMPTY, versionDTO.getTestDependencies()),
                    () -> assertNullOrEmpty(VersionError.CREATE_VERSION_PROJECT_DEPENDENCIES_SHOULD_BE_EMPTY, versionDTO.getProjectDependencies()),
                    () -> assertNull(VersionError.CREATE_VERSION_ID_SHOULD_BE_NULL, versionDTO.getId())
        );

        VersionDTO versionToSave = versionDTO;
        if (versionDTO.getPackaging() == null) {
            versionToSave = versionDTO.toBuilder().packaging(DEFAULT_TYPE).build();
        }
        return versionDao.save(versionToSave.toBuilder()
                                            .buildHash()
                                            .build());
    }


    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Override
    public List<VersionDTO> getAllVersions(final PageDTO page) {
        final PageDTO currentPage = page != null ? page : PageDTO.builder()
                                                                 .pageSize(defaultPageSize)
                                                                 .build();

        final List<VersionDTO> result = versionDao.getAll(currentPage);
        assertNotEmpty(VersionError.READ_NOT_FOUND_VERSIONS, result);
        return result;
    }

    @Override
    public VersionDTO getVersion(final String groupId, final String artifactId, final String version, final String type) {
        assertModel(
                () -> assertNotNull(VersionError.READ_GROUP_ID_REQUIRED, groupId),
                () -> assertNotNull(VersionError.READ_ARTIFACT_ID_REQUIRED, artifactId),
                () -> assertNotNull(VersionError.READ_VERSION_REQUIRED, version)
        );

        final String     currentType = type == null ? DEFAULT_TYPE : type;
        final VersionDTO result      = versionDao.getVersion(groupId, artifactId, version, currentType);
        assertNotNull(VersionError.READ_VERSION_NOT_FOUND.addDetail("{0}:{1}:{2}:{3}", groupId, artifactId, version, currentType), result);
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
        assertNotNull(VersionError.UPDATE_VERSION_DATA_REQUIRED, versionDTO);
        assertNotNull(VersionError.UPDATE_VERSION_ID_SHOULD_BE_NULL, versionDTO.getId());

        final VersionDTO result = versionDao.getVersionLight(versionDTO.getId());
        assertNotNull(VersionError.UPDATE_VERSION_NOT_FOUND, result);

        return versionDao.update(versionDTO);
    }

    // =================================================================================================================
    // DELETE
    // =================================================================================================================
    @Override
    public void delete(final String groupId, final String artifactId, final String version, final String type) {
        assertModel(
                () -> assertNotNull(VersionError.DELETE_GROUP_ID_REQUIRED, groupId),
                () -> assertNotNull(VersionError.DELETE_ARTIFACT_ID_REQUIRED, artifactId),
                () -> assertNotNull(VersionError.DELETE_VERSION_REQUIRED, version)
        );

        final String     currentType = type == null ? DEFAULT_TYPE : type;
        final VersionDTO result      = versionDao.getVersionLight(groupId, artifactId, version, currentType);
        assertNotNull(VersionError.DELETE_VERSION_NOT_FOUND.addDetail("{0}:{1}:{2}:{3}", groupId, artifactId, version, currentType), result);
        versionDao.delete(result.getId());
    }

    @Override
    public void delete(final long id) {
        assertHigherOrEquals(VersionError.DELETE_INVALID_ID, 0, id);
        final VersionDTO result = versionDao.getVersionLight(id);
        assertNotNull(VersionError.DELETE_VERSION_NOT_FOUND_WITH_ID.addDetail("{0}", id), result);
        versionDao.delete(result.getId());
    }
}
