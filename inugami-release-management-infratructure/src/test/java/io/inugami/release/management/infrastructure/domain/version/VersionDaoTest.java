package io.inugami.release.management.infrastructure.domain.version;

import io.inugami.release.management.api.common.dto.PageDTO;
import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import io.inugami.release.management.infrastructure.datasource.neo4j.DatasourceNeo4jConfiguration;
import io.inugami.release.management.infrastructure.datasource.neo4j.entity.VersionEntity;
import io.inugami.release.management.infrastructure.datasource.neo4j.mapper.VersionEntityMapper;
import io.inugami.release.management.infrastructure.datasource.neo4j.repository.VersionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static io.inugami.commons.test.UnitTestHelper.assertText;
import static io.inugami.release.management.infrastructure.domain.version.VersionDataset.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VersionDaoTest {
    public static final String            NOMIAL_VERSIONS = """
            [ {
              "artifactId" : "inugami-release-management-common",
              "dependencies" : [ ],
              "groupId" : "io.inugami.release.management",
              "id" : 1,
              "packaging" : "jar",
              "projectDependencies" : [ ],
              "testDependencies" : [ ],
              "version" : "1.0.0-SNAPSHOT"
            } ]
            """;
    public static final String            GROUP_ID        = "io.inugami.release.management";
    public static final String            ARTIFACT_ID     = "inugami-release-management-common";
    public static final String            VERSION         = "1.0.0-SNAPSHOT";
    public static final String            TYPE            = "jar";
    public static final String            NOMINAL_VERSION = """
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
    // =================================================================================================================
    // ATTRIBUTES
    // =================================================================================================================
    @Mock
    private             VersionRepository versionRepository;

    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @Test
    void save_nominal() {
        when(versionRepository.save(any(VersionEntity.class))).thenAnswer(answer -> answer.getArgument(0, VersionEntity.class).toBuilder().id(1L).build());
        final VersionDTO result = buildDao().save(versionDTOLight().toBuilder()
                                                                   .id(null)
                                                                   .build());
        assertText(result, """
                {
                  "artifactId" : "inugami-release-management-common",
                  "dependencies" : [ ],
                  "groupId" : "io.inugami.release.management",
                  "hash" : "io.inugami.release.management:inugami-release-management-common:1.0.0-SNAPSHOT:jar",
                  "id" : 1,
                  "packaging" : "jar",
                  "projectDependencies" : [ ],
                  "testDependencies" : [ ],
                  "version" : "1.0.0-SNAPSHOT"
                }
                """);
    }

    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Test
    void getAll_nominal() {
        when(versionRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(List.of(versionEntityLight())));
        final List<VersionDTO> result = buildDao().getAll(PageDTO.builder()
                                                                 .order(PageDTO.Order.ASC)
                                                                 .pageSize(10)
                                                                 .page(0)
                                                                 .build());

        assertText(result, NOMIAL_VERSIONS);
    }

    @Test
    void getAll_withDESC() {
        when(versionRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(List.of(versionEntityLight())));
        final List<VersionDTO> result = buildDao().getAll(PageDTO.builder()
                                                                 .order(PageDTO.Order.DESC)
                                                                 .pageSize(10)
                                                                 .page(0)
                                                                 .build());

        assertText(result, NOMIAL_VERSIONS);
    }

    @Test
    void getAll_withDefaultOrder() {
        when(versionRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(List.of(versionEntityLight())));
        final List<VersionDTO> result = buildDao().getAll(PageDTO.builder()
                                                                 .pageSize(10)
                                                                 .page(0)
                                                                 .build());

        assertText(result, NOMIAL_VERSIONS);
    }


    @Test
    void getVersion_nominal() {
        when(versionRepository.findByGroupIdAndArtifactIdAndVersionAndPackaging(GROUP_ID,
                                                                                ARTIFACT_ID,
                                                                                VERSION,
                                                                                TYPE)).thenReturn(Optional.of(versionEntity()));

        final VersionDTO result = buildDao().getVersion(GROUP_ID, ARTIFACT_ID, VERSION, TYPE);
        assertText(result, NOMINAL_VERSION);
    }

    @Test
    void getVersion_withId() {
        when(versionRepository.findById(1L)).thenReturn(Optional.of(versionEntity()));

        final VersionDTO result = buildDao().getVersion(1L);
        assertText(result, NOMINAL_VERSION);
    }

    @Test
    void getVersionLight_nominal() {
        when(versionRepository.getVersionLight(1L)).thenReturn(Optional.of(versionEntityLight()));
        assertText(buildDao().getVersionLight(1L), """
                {
                  "artifactId" : "inugami-release-management-common",
                  "groupId" : "io.inugami.release.management",
                  "id" : 1,
                  "packaging" : "jar",
                  "version" : "1.0.0-SNAPSHOT"
                }
                """);
    }

    @Test
    void getVersionLight_nominal_withGav() {
        when(versionRepository.getVersionLight(GROUP_ID,
                                               ARTIFACT_ID,
                                               VERSION,
                                               TYPE)).thenReturn(Optional.of(versionEntityLight()));
        assertText(buildDao().getVersionLight(GROUP_ID,
                                              ARTIFACT_ID,
                                              VERSION,
                                              TYPE), """
                           {
                             "artifactId" : "inugami-release-management-common",
                             "groupId" : "io.inugami.release.management",
                             "id" : 1,
                             "packaging" : "jar",
                             "version" : "1.0.0-SNAPSHOT"
                           }
                                      """);
    }

    // =================================================================================================================
    // UPDATE
    // =================================================================================================================
    @Test
    void update_nominal() {
        when(versionRepository.findById(1L)).thenReturn(Optional.of(versionEntity()));
        when(versionRepository.save(any(VersionEntity.class))).thenAnswer(answer -> answer.getArgument(0));

        final VersionDTO result = buildDao().update(VersionDTO.builder()
                                                              .id(1L)
                                                              .groupId("io.inugami.release.management.xxx")
                                                              .artifactId("inugami-release-management-common-xxxx")
                                                              .version("1.0.0")
                                                              .packaging("war")
                                                              .currentProject(true)
                                                              .build());
        assertText(result, """
                {
                    "artifactId" : "inugami-release-management-common-xxxx",
                    "currentProject" : true,
                    "dependencies" : [ {
                      "artifactId" : "slf4j-api",
                      "groupId" : "org.slf4j",
                      "id" : 2,
                      "packaging" : "jar",
                      "version" : "1.7.36"
                    } ],
                    "groupId" : "io.inugami.release.management.xxx",
                    "hash" : "io.inugami.release.management.xxx:inugami-release-management-common-xxxx:1.0.0:war",
                    "id" : 1,
                    "packaging" : "war",
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
                    "version" : "1.0.0"
                  }
                """);
    }

    // =================================================================================================================
    // DELETE
    // =================================================================================================================
    @Test
    void delete_nominal() {
        buildDao().delete(1L);
        verify(versionRepository).deleteById(1L);
    }


    // =================================================================================================================
    // TOOLS
    // =================================================================================================================
    @Test
    void convertToDto_withNull() {
        assertThat(buildDao().convertToDto(Optional.empty())).isNull();
    }

    VersionDao buildDao() {
        final VersionEntityMapper mapper = new DatasourceNeo4jConfiguration().versionEntityMapper();
        return new VersionDao(versionRepository, mapper);
    }
}