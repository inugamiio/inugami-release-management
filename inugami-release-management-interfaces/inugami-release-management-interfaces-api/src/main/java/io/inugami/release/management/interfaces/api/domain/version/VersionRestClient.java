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
    List<VersionLightDTO> getAllVersions(@RequestParam(required = false, defaultValue = "0") final Integer page,
                                         @RequestParam(required = false, defaultValue = "50") final Integer pageSize,
                                         @RequestParam(required = false, defaultValue = "ASC") final Order order);

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    VersionDTO getVersion(@PathVariable final long id);

    @GetMapping(path = "{groupId}/{artifactId}/{version}/{packaging}", produces = MediaType.APPLICATION_JSON_VALUE)
    VersionDTO getVersion(@PathVariable final String groupId, @PathVariable final String artifactId, @PathVariable final String version, @PathVariable final String packaging);


    // =================================================================================================================
    // UPDATE
    // =================================================================================================================
    @PatchMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    VersionDTO update(@PathVariable final long id, @RequestBody final VersionDTO versionDTO);

    // =================================================================================================================
    // DELETE
    // =================================================================================================================
    @DeleteMapping(path = "{groupId}/{artifactId}/{version}/{packaging}")
    void delete(@PathVariable final String groupId, @PathVariable final String artifactId, @PathVariable final String version, @PathVariable final String type);

    @DeleteMapping(path = "{id}")
    void delete(@PathVariable final long id);

}
