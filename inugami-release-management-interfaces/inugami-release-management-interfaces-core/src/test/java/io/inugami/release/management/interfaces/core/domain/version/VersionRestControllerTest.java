package io.inugami.release.management.interfaces.core.domain.version;

import io.inugami.release.management.api.domain.version.IVersionService;
import io.inugami.release.management.interfaces.api.common.dto.Order;
import io.inugami.release.management.interfaces.api.domain.version.dto.VersionDTO;
import io.inugami.release.management.interfaces.core.domain.version.config.InterfaceCoreMapperConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static io.inugami.commons.test.UnitTestHelper.assertTextRelative;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VersionRestControllerTest {

    public static final String          GROUP_ID    = "io.inugami.release.management";
    public static final String          ARTIFACT_ID = "inugami-release-management-common";
    public static final String          VERSION     = "1.0.0-SNAPSHOT";
    public static final String          PACKAGING   = "jar";
    @Mock
    private             IVersionService versionService;

    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @Test
    void create_nominal() {
        when(versionService.create(any())).thenReturn(VersionDataset.versionDTOLight());
        final var result = buildController().create(VersionDTO.builder()
                                                              .groupId(GROUP_ID)
                                                              .artifactId(ARTIFACT_ID)
                                                              .version(VERSION)
                                                              .packaging(PACKAGING)
                                                              .buildHash()
                                                              .build());

        assertTextRelative(result, "interfaces/core/domain/version/versionRestController/create_nominal.json");
    }

    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Test
    void getAllVersions_nominal() {
        when(versionService.getAllVersions(any())).thenReturn(List.of(VersionDataset.versionDTOLight()));
        final var result = buildController().getAllVersions(1, 10, Order.ASC);
        assertTextRelative(result, "interfaces/core/domain/version/versionRestController/getAllVersions_nominal.json");
    }

    @Test
    void getVersion_nominal() {
        when(versionService.getVersion(1L)).thenReturn(VersionDataset.versionDTO());
        final var result = buildController().getVersion(1);
        assertTextRelative(result, "interfaces/core/domain/version/versionRestController/getVersion_nominal_by_id.json");
    }

    @Test
    void getVersion_nominal_withGav() {
        when(versionService.getVersion(GROUP_ID, ARTIFACT_ID, VERSION, PACKAGING)).thenReturn(VersionDataset.versionDTO());
        final var result = buildController().getVersion(GROUP_ID, ARTIFACT_ID, VERSION, PACKAGING);
        assertTextRelative(result, "interfaces/core/domain/version/versionRestController/getVersion_nominal_by_gav.json");
    }

    // =================================================================================================================
    // UPDATE
    // =================================================================================================================
    @Test
    void update_nominal() {
        when(versionService.update(any())).thenReturn(VersionDataset.versionDTOLight());
        final var result = buildController().update(1L, VersionDTO.builder()
                                                                  .id(1L)
                                                                  .groupId("io.inugami.release.management")
                                                                  .artifactId("inugami-release-management-common")
                                                                  .version("1.0.0-SNAPSHOT")
                                                                  .packaging("jar")
                                                                  .buildHash()
                                                                  .build());
        assertTextRelative(result, "interfaces/core/domain/version/versionRestController/update_nominal.json");
    }

    // =================================================================================================================
    // DELETE
    // =================================================================================================================
    @Test
    void delete_nominal() {
        buildController().delete(1L);
        verify(versionService).delete(1L);
    }

    @Test
    void delete_nominal_gav() {
        buildController().delete(GROUP_ID, ARTIFACT_ID, VERSION, PACKAGING);
        verify(versionService).delete(GROUP_ID, ARTIFACT_ID, VERSION, PACKAGING);
    }


    // =================================================================================================================
    // TOOLS
    // =================================================================================================================
    VersionRestController buildController() {
        final InterfaceCoreMapperConfiguration config = new InterfaceCoreMapperConfiguration();
        return new VersionRestController(versionService, config.versionDTOMapper());
    }
}