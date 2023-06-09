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

public interface IVersionDao {
    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    VersionDTO save(VersionDTO dto);

    // =================================================================================================================
    // READ
    // =================================================================================================================
    List<VersionDTO> getAll(final PageDTO page);

    VersionDTO getVersion(String groupId, String artifactId, String version, String packaging);


    VersionDTO getVersion(long id);

    VersionDTO getVersionLight(long id);

    VersionDTO getVersionLight(String groupId, String artifactId, String version, String currentType);

    // =================================================================================================================
    // UPDATE
    // =================================================================================================================
    VersionDTO update(final VersionDTO versionDTO);

    // =================================================================================================================
    // DELETE
    // =================================================================================================================
    void delete(Long id);


}

