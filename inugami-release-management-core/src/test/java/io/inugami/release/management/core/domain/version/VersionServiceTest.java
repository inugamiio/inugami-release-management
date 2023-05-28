package io.inugami.release.management.core.domain.version;


import io.inugami.release.management.api.common.dto.PageDTO;
import io.inugami.release.management.api.domain.version.IVersionDao;
import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import io.inugami.release.management.api.domain.version.exception.VersionError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static io.inugami.commons.test.UnitTestHelper.assertText;
import static io.inugami.commons.test.UnitTestHelper.assertThrows;
import static io.inugami.release.management.core.domain.version.VersionDataset.versionDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VersionServiceTest {

    public static final String      VERSION_NOMINAL_RESPONSE  = """
            {
              "artifactId" : "inugami-release-management-common",
              "groupId" : "io.inugami.release.management",
              "hash" : ":",
              "id" : 1,
              "packaging" : "jar",
              "version" : "1.0.0-SNAPSHOT"
            }
            """;
    public static final String      VERSIONS_NOMINAL_RESPONSE = """
            [ {
              "artifactId" : "inugami-release-management-common",
              "dependencies" : [ {
                "artifactId" : "slf4j-api",
                "groupId" : "org.slf4j",
                "id" : 2,
                "packaging" : "jar",
                "version" : "1.7.36"
              } ],
              "groupId" : "io.inugami.release.management",
              "hash" : ":",
              "id" : 1,
              "packaging" : "jar",
              "projectDependencies" : [ {
                "artifactId" : "inugami-release-management-api",
                "groupId" : "io.inugami.release.management",
                "id" : 1,
                "packaging" : "jar",
                "version" : "1.0.0-SNAPSHOT"
              } ],
              "testDependencies" : [ {
                "artifactId" : "inugami_commons_test",
                "groupId" : "io.inugami",
                "id" : 3,
                "packaging" : "jar",
                "version" : "3.2.0"
              } ],
              "version" : "1.0.0-SNAPSHOT"
            } ]
            """;
    public static final String      VERSION_DETAIL_NOMINAL    = """
            {
              "artifactId" : "inugami-release-management-common",
              "dependencies" : [ {
                "artifactId" : "slf4j-api",
                "groupId" : "org.slf4j",
                "id" : 2,
                "packaging" : "jar",
                "version" : "1.7.36"
              } ],
              "groupId" : "io.inugami.release.management",
              "hash" : ":",
              "id" : 1,
              "packaging" : "jar",
              "projectDependencies" : [ {
                "artifactId" : "inugami-release-management-api",
                "groupId" : "io.inugami.release.management",
                "id" : 1,
                "packaging" : "jar",
                "version" : "1.0.0-SNAPSHOT"
              } ],
              "testDependencies" : [ {
                "artifactId" : "inugami_commons_test",
                "groupId" : "io.inugami",
                "id" : 3,
                "packaging" : "jar",
                "version" : "3.2.0"
              } ],
              "version" : "1.0.0-SNAPSHOT"
            }
            """;
    public static final String      GROUP_ID                  = "io.inugami.release.management";
    public static final String      ARTIFACT_ID               = "inugami-release-management-common";
    public static final String      VERSION                   = "1.0.0-SNAPSHOT";
    public static final String      TYPE                      = "jar";
    // =================================================================================================================
    // ATTRIBUTES
    // =================================================================================================================
    @Mock
    private             IVersionDao versionDao;

    @Captor
    private ArgumentCaptor<PageDTO> pageCaptor;

    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @Test
    void create_withoutData() {
        assertThrows(VersionError.CREATE_DATA_REQUIRED,
                     () -> buildService().create(null),
                     "core/domain/version/versionServiceTest/create_withoutData.json");
    }

    @Test
    void create_withoutGroupId() {
        assertThrows(VersionError.CREATE_GROUP_ID_REQUIRED,
                     () -> buildService().create(versionDTO().toBuilder()
                                                             .groupId(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/create_withoutGroupId.json");
    }

    @Test
    void create_withoutArtifactId() {
        assertThrows(VersionError.CREATE_ARTIFACT_ID_REQUIRED,
                     () -> buildService().create(versionDTO().toBuilder()
                                                             .artifactId(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/create_withoutArtifactId.json");
    }

    @Test
    void create_withoutVersion() {
        assertThrows(VersionError.CREATE_VERSION_REQUIRED,
                     () -> buildService().create(versionDTO().toBuilder()
                                                             .version(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/create_withoutVersion.json");
    }


    @Test
    void create_withDependencies() {
        assertThrows(VersionError.CREATE_VERSION_DEPENDENCIES_SHOULD_BE_EMPTY,
                     () -> buildService().create(versionDTO().toBuilder()
                                                             .build()),
                     "core/domain/version/versionServiceTest/create_withDependencies.json");
    }

    @Test
    void create_withTestDependencies() {
        assertThrows(VersionError.CREATE_VERSION_TEST_DEPENDENCIES_SHOULD_BE_EMPTY,
                     () -> buildService().create(versionDTO().toBuilder()
                                                             .dependencies(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/create_withTestDependencies.json");
    }

    @Test
    void create_withProjectDependencies() {
        assertThrows(VersionError.CREATE_VERSION_PROJECT_DEPENDENCIES_SHOULD_BE_EMPTY,
                     () -> buildService().create(versionDTO().toBuilder()
                                                             .dependencies(null)
                                                             .testDependencies(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/create_withProjectDependencies.json");
    }

    @Test
    void create_withId() {
        assertThrows(VersionError.CREATE_VERSION_ID_SHOULD_BE_NULL,
                     () -> buildService().create(versionDTO().toBuilder()
                                                             .dependencies(null)
                                                             .testDependencies(null)
                                                             .projectDependencies(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/create_withId.json");
    }

    @Test
    void create_withoutPackaging() {
        when(versionDao.save(any(VersionDTO.class))).thenAnswer(answer -> ((VersionDTO) answer.getArgument(0)).toBuilder().id(1L).build());

        final VersionDTO result = buildService().create(versionDTO().toBuilder()
                                                                    .dependencies(null)
                                                                    .testDependencies(null)
                                                                    .projectDependencies(null)
                                                                    .packaging(null)
                                                                    .id(null)
                                                                    .build());

        assertText(result, VERSION_NOMINAL_RESPONSE);
    }

    @Test
    void create_nominal() {
        when(versionDao.save(any(VersionDTO.class))).thenAnswer(answer -> ((VersionDTO) answer.getArgument(0)).toBuilder().id(1L).build());

        final VersionDTO result = buildService().create(versionDTO().toBuilder()
                                                                    .dependencies(null)
                                                                    .testDependencies(null)
                                                                    .projectDependencies(null)
                                                                    .id(null)
                                                                    .build());

        assertText(result, VERSION_NOMINAL_RESPONSE);
    }

    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Test
    void getAllVersions_nominal() {
        when(versionDao.getAll(any(PageDTO.class))).thenReturn(List.of(VersionDataset.versionDTO()));
        final List<VersionDTO> result = buildService().getAllVersions(PageDTO.builder()
                                                                             .pageSize(10)
                                                                             .build());

        assertText(result, VERSIONS_NOMINAL_RESPONSE);
        verify(versionDao).getAll(pageCaptor.capture());
        assertThat(pageCaptor.getValue().getPageSize()).isEqualTo(10);
    }

    @Test
    void getAllVersions_withoutPage() {
        when(versionDao.getAll(any(PageDTO.class))).thenReturn(List.of(VersionDataset.versionDTO()));
        final List<VersionDTO> result = buildService().getAllVersions(null);

        assertText(result, VERSIONS_NOMINAL_RESPONSE);
        verify(versionDao).getAll(pageCaptor.capture());
        assertThat(pageCaptor.getValue().getPageSize()).isEqualTo(50);
    }

    @Test
    void getAllVersions_withoutResult() {
        assertThrows(VersionError.READ_NOT_FOUND_VERSIONS,
                     () -> buildService().getAllVersions(null),
                     "core/domain/version/versionServiceTest/getAllVersions_withoutResult.json");
    }


    @Test
    void getVersion_nominal() {
        when(versionDao.getVersion(GROUP_ID, ARTIFACT_ID, VERSION, TYPE))
                .thenReturn(VersionDataset.versionDTO());
        final VersionDTO result = buildService().getVersion(GROUP_ID, ARTIFACT_ID, VERSION, TYPE);
        assertText(result, VERSION_DETAIL_NOMINAL);
    }

    @Test
    void getVersion_withoutType() {
        when(versionDao.getVersion(GROUP_ID, ARTIFACT_ID, VERSION, TYPE))
                .thenReturn(VersionDataset.versionDTO());
        final VersionDTO result = buildService().getVersion(GROUP_ID, ARTIFACT_ID, VERSION, null);
        assertText(result, VERSION_DETAIL_NOMINAL);
    }

    @Test
    void getVersion_withoutGroupId() {
        assertThrows(VersionError.READ_GROUP_ID_REQUIRED,
                     () -> buildService().getVersion(null, ARTIFACT_ID, VERSION, null),
                     "core/domain/version/versionServiceTest/getVersion_withoutGroupId.json");
    }


    @Test
    void getVersion_withoutArtifactId() {
        assertThrows(VersionError.READ_ARTIFACT_ID_REQUIRED,
                     () -> buildService().getVersion(GROUP_ID, null, VERSION, null),
                     "core/domain/version/versionServiceTest/getVersion_withoutArtifactId.json");
    }

    @Test
    void getVersion_withoutVersion() {
        assertThrows(VersionError.READ_VERSION_REQUIRED,
                     () -> buildService().getVersion(GROUP_ID, ARTIFACT_ID, null, null),
                     "core/domain/version/versionServiceTest/getVersion_withoutVersion.json");
    }

    @Test
    void getVersion_withoutResult() {
        assertThrows(VersionError.READ_VERSION_NOT_FOUND,
                     () -> buildService().getVersion(GROUP_ID, ARTIFACT_ID, VERSION, null),
                     "core/domain/version/versionServiceTest/getVersion_withoutResult.json");
    }

    @Test
    void getVersion_withId_nominal() {
        when(versionDao.getVersion(1L))
                .thenReturn(VersionDataset.versionDTO());
        final VersionDTO result = buildService().getVersion(1L);
        assertText(result, VERSION_DETAIL_NOMINAL);
    }

    @Test
    void getVersion_withId_withIdLessThan0() {
        assertThrows(VersionError.READ_INVALID_ID,
                     () -> buildService().getVersion(-1),
                     "core/domain/version/versionServiceTest/getVersion_withId_withIdLessThan0.json");
    }

    @Test
    void getVersion_withId_withoutResult() {
        assertThrows(VersionError.READ_VERSION_NOT_FOUND_WITH_ID,
                     () -> buildService().getVersion(2),
                     "core/domain/version/versionServiceTest/getVersion_withId_withoutResult.json");
    }

    // =================================================================================================================
    // UPDATE
    // =================================================================================================================
    @Test
    void update_nominal() {
        when(versionDao.getVersion(anyLong())).thenReturn(VersionDTO.builder().id(1L).build());
        when(versionDao.update(any())).thenAnswer(answer -> answer.getArgument(0));
        final VersionDTO versionDto = versionDTO().toBuilder()
                                                  .dependencies(null)
                                                  .testDependencies(null)
                                                  .projectDependencies(null)
                                                  .build();

        final VersionDTO result = buildService().update(versionDto);
        assertText(result, """
                {
                  "artifactId" : "inugami-release-management-common",
                  "groupId" : "io.inugami.release.management",
                  "hash" : ":",
                  "id" : 1,
                  "packaging" : "jar",
                  "version" : "1.0.0-SNAPSHOT"
                }
                """);
    }

    @Test
    void update_withoutData() {
        assertThrows(VersionError.UPDATE_VERSION_DATA_REQUIRED,
                     () -> buildService().update(null),
                     "core/domain/version/versionServiceTest/update_withoutData.json");
    }

    @Test
    void update_withoutGroupId() {
        assertThrows(VersionError.UPDATE_GROUP_ID_REQUIRED,
                     () -> buildService().update(versionDTO().toBuilder()
                                                             .groupId(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/update_withoutGroupId.json");
    }

    @Test
    void update_withoutArtifactId() {
        assertThrows(VersionError.UPDATE_ARTIFACT_ID_REQUIRED,
                     () -> buildService().update(versionDTO().toBuilder()
                                                             .artifactId(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/update_withoutArtifactId.json");
    }

    @Test
    void update_withoutVersion() {
        assertThrows(VersionError.UPDATE_VERSION_REQUIRED,
                     () -> buildService().update(versionDTO().toBuilder()
                                                             .version(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/update_withoutVersion.json");
    }

    @Test
    void update_withDependencies() {
        assertThrows(VersionError.UPDATE_VERSION_DEPENDENCIES_SHOULD_BE_EMPTY,
                     () -> buildService().update(versionDTO().toBuilder()
                                                             .testDependencies(null)
                                                             .projectDependencies(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/update_withDependencies.json");
    }

    @Test
    void update_withTestDependencies() {
        assertThrows(VersionError.UPDATE_VERSION_TEST_DEPENDENCIES_SHOULD_BE_EMPTY,
                     () -> buildService().update(versionDTO().toBuilder()
                                                             .dependencies(null)
                                                             .projectDependencies(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/update_withTestDependencies.json");
    }

    @Test
    void update_withProjectDependencies() {
        assertThrows(VersionError.UPDATE_VERSION_PROJECT_DEPENDENCIES_SHOULD_BE_EMPTY,
                     () -> buildService().update(versionDTO().toBuilder()
                                                             .dependencies(null)
                                                             .testDependencies(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/update_withProjectDependencies.json");
    }

    @Test
    void update_withoutId() {
        assertThrows(VersionError.UPDATE_VERSION_ID_SHOULD_BE_NULL,
                     () -> buildService().update(versionDTO().toBuilder()
                                                             .dependencies(null)
                                                             .testDependencies(null)
                                                             .projectDependencies(null)
                                                             .id(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/update_withoutId.json");
    }

    @Test
    void update_withoutVersionFound() {
        assertThrows(VersionError.UPDATE_VERSION_NOT_FOUND,
                     () -> buildService().update(versionDTO().toBuilder()
                                                             .dependencies(null)
                                                             .testDependencies(null)
                                                             .projectDependencies(null)
                                                             .build()),
                     "core/domain/version/versionServiceTest/update_withoutVersionFound.json");
    }


    // =================================================================================================================
    // DELETE
    // =================================================================================================================
    @Test
    void delete_withId_nominal() {
        when(versionDao.getVersion(1L)).thenReturn(VersionDTO.builder()
                                                             .id(1L)
                                                             .build());
        buildService().delete(1L);
        verify(versionDao).delete(1L);
    }

    @Test
    void delete_withId_withBadId() {
        assertThrows(VersionError.DELETE_INVALID_ID,
                     () -> buildService().delete(-1),
                     "core/domain/version/versionServiceTest/delete_withId_withBadId.json");
    }

    @Test
    void delete_withId_withVersionNotFound() {
        assertThrows(VersionError.DELETE_VERSION_NOT_FOUND_WITH_ID,
                     () -> buildService().delete(1),
                     "core/domain/version/versionServiceTest/delete_withId_withVersionNotFound.json");
    }

    // =================================================================================================================
    // TOOLS
    // =================================================================================================================
    VersionService buildService() {
        final VersionService result = new VersionService(versionDao);

        result.setDefaultPageSize(50);
        return result;
    }
}