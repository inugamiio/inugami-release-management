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

import static io.inugami.commons.test.UnitTestHelper.assertText;
import static io.inugami.release.management.infrastructure.domain.version.VersionDataset.versionDTOLight;
import static io.inugami.release.management.infrastructure.domain.version.VersionDataset.versionEntityLight;
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
                  "hash" : ":",
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
    void getAll_withDefualtOrder() {
        when(versionRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(List.of(versionEntityLight())));
        final List<VersionDTO> result = buildDao().getAll(PageDTO.builder()
                                                                 .pageSize(10)
                                                                 .page(0)
                                                                 .build());

        assertText(result, NOMIAL_VERSIONS);
    }
    // =================================================================================================================
    // UPDATE
    // =================================================================================================================

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
    VersionDao buildDao() {
        final VersionEntityMapper mapper = new DatasourceNeo4jConfiguration().versionEntityMapper();
        return new VersionDao(versionRepository, mapper);
    }
}