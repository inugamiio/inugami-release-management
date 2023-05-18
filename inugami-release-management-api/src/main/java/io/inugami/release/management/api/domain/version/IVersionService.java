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
package io.inugami.release.management.api.domain.version;

import io.inugami.release.management.api.common.dto.PageDTO;
import io.inugami.release.management.api.domain.version.dto.VersionDTO;

import java.util.List;

public interface IVersionService {
    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    VersionDTO create(final VersionDTO versionDTO);

    // =================================================================================================================
    // READ
    // =================================================================================================================
    List<VersionDTO> getAllVersions(final PageDTO page);

    VersionDTO getVersion(final String groupId, final String artifactId, final String version, final String packaging);
    VersionDTO getVersion(final long id);

    // =================================================================================================================
    // UPDATE
    // =================================================================================================================
    VersionDTO update(final VersionDTO versionDTO);

    // =================================================================================================================
    // DELETE
    // =================================================================================================================
    void delete(final String groupId, final String artifactId, final String version, final String type);
    void delete(final long id);
}
