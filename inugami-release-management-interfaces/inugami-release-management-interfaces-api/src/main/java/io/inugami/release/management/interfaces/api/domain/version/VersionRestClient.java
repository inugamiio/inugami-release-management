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
package io.inugami.release.management.interfaces.api.domain.version;

import io.inugami.release.management.interfaces.api.common.dto.Order;
import io.inugami.release.management.interfaces.api.domain.version.dto.VersionDTO;
import io.inugami.release.management.interfaces.api.domain.version.dto.VersionLightDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "v1/version")
public interface VersionRestClient {

    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VersionDTO create(@RequestBody final VersionDTO versionDTO);

    // =================================================================================================================
    // READ
    // =================================================================================================================
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<VersionLightDTO> getAllVersions(final int page, final int pageSize, final Order order);

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    VersionDTO getVersion(@PathVariable final long id);

    @GetMapping(path = "{groupId}/{artifactId}/{version}/{packaging}", produces = MediaType.APPLICATION_JSON_VALUE)
    VersionDTO getVersion(@PathVariable final String groupId, @PathVariable final String artifactId, @PathVariable final String version, @PathVariable final String packaging);


    // =================================================================================================================
    // UPDATE
    // =================================================================================================================


    // =================================================================================================================
    // DELETE
    // =================================================================================================================
}
