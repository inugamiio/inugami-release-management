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

    VersionDTO getVersion(final String groupId, final String artifactId, final String version, final String type);
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
