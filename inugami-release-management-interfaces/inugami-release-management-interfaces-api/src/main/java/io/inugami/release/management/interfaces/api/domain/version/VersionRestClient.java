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

    @GetMapping(path = "{groupId}/{artifactId}/{version}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    VersionDTO getVersion(@PathVariable final String groupId, @PathVariable final String artifactId, @PathVariable final String version, @PathVariable final String type);


    // =================================================================================================================
    // UPDATE
    // =================================================================================================================


    // =================================================================================================================
    // DELETE
    // =================================================================================================================
}
