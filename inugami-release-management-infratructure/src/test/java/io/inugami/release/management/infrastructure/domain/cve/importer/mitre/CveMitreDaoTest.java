package io.inugami.release.management.infrastructure.domain.cve.importer.mitre;

import io.inugami.api.marshalling.JsonMarshaller;
import io.inugami.commons.connectors.IHttpBasicConnector;

import io.inugami.commons.test.UnitTestHelper;
import io.inugami.release.management.api.domain.cve.dto.CveDTO;
import io.inugami.release.management.common.services.DownloadService;
import io.inugami.release.management.common.services.ZipService;
import io.inugami.release.management.infrastructure.common.FileService;
import io.inugami.release.management.infrastructure.datasource.neo4j.mapper.CveEntityMapper;
import io.inugami.release.management.infrastructure.datasource.neo4j.repository.CveEntityRepository;
import io.inugami.release.management.infrastructure.domain.cve.CveMapperConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.List;

import static io.inugami.commons.test.UnitTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CveMitreDaoTest {

    @Mock
    private IHttpBasicConnector mitreImporterHttpConnector;
    @Mock
    private CveEntityRepository cveEntityRepository;


    // =================================================================================================================
    // CREATE
    // =================================================================================================================

    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Test
    void isCveZipFileExists_withData() {
        assertThat(buildDao().isCveZipFileExists()).isTrue();
    }

    @Test
    void getAllFiles_nominal() {
        final List<File> result = buildDao().getAllFiles();

        final List<String> paths = result.stream()
                                         .map(File::getPath)
                                         .map(path -> path.split("inugami-release-management-infratructure")[1])
                                         .map(path -> path.replaceAll("\\\\", "/"))
                                         .toList();

        assertText(String.join("\n", paths), """
                /src/test/resources/infrastructure/domain/cve/importer/mitre/cveMitreDao/data/2022/4xxx/CVE-2022-32415.json
                /src/test/resources/infrastructure/domain/cve/importer/mitre/cveMitreDao/data/2022/4xxx/CVE-2022-32531.json
                /src/test/resources/infrastructure/domain/cve/importer/mitre/cveMitreDao/data/2022/4xxx/CVE-2022-40145.json
                /src/test/resources/infrastructure/domain/cve/importer/mitre/cveMitreDao/data/2022/4xxx/CVE-2022-43940.json
                /src/test/resources/infrastructure/domain/cve/importer/mitre/cveMitreDao/data/2022/4xxx/CVE-2022-4815.json
                /src/test/resources/infrastructure/domain/cve/importer/mitre/cveMitreDao/data/2023/CVE-2023-27902.json
                /src/test/resources/infrastructure/domain/cve/importer/mitre/cveMitreDao/data/2023/CVE-2023-30524.json
                /src/test/resources/infrastructure/domain/cve/importer/mitre/cveMitreDao/data/2023/CVE-2023-32977.json
                /src/test/resources/infrastructure/domain/cve/importer/mitre/cveMitreDao/data/2023/CVE-2023-33946.json
                """);
    }

    @Test
    void readCveFile_nominal() {
        assertTextRelative(buildDao().readCveFile(buildPath("infrastructure/domain/cve/importer/mitre/cveMitreDaoTest/CVE-2023-33246.json")),
                           "infrastructure/domain/cve/importer/mitre/cveMitreDaoTest/CVE-2023-33246_result.json");

    }


    @Test
    void readCveFile_withBadJsonFile() {
        assertLogs(() -> buildDao().readCveFile(buildPath("infrastructure/domain/cve/importer/mitre/cveMitreDaoTest/CVE-2023-33246_bad.json")),
                   CveMitreDao.class,
                   """
                           [
                               {
                                   "loggerName":"io.inugami.release.management.infrastructure.domain.cve.importer.mitre.CveMitreDao",
                                   "level":"ERROR",
                                   "mdc":{}
                                   "message":[
                                       "enable to read mitre file : /home/pguillerm/dev/workspaces/inugami/inugami-release-management/inugami-release-management-infratructure/./src/test/resources/infrastructure/domain/cve/importer/mitre/cveMitreDaoTest/CVE-2023-33246_bad.json : Unexpected end-of-input: expected close marker for Object (start marker at [Source: (String)\\"{",
                                       "    \\"dataType\\": \\"CVE_RECORD\\",",
                                       "    \\"dataVersion\\": \\"5.0\\",",
                                       "    \\"cveMetadata\\": {",
                                       "        \\"cveId\\": \\"CVE-2023-33246\\",",
                                       "        \\"assignerOrgId\\": \\"f0158376-9dc2-43b6-827c-5f631a4d8d09\\",",
                                       "        \\"state\\": \\"PUBLISHED\\",",
                                       "        \\"assignerShortName\\": \\"apache\\",",
                                       "        \\"dateReserved\\": \\"2023-05-21T08:12:11.944Z\\",",
                                       "        \\"datePublished\\": \\"2023-05-24T14:45:25.684Z\\",",
                                       "        \\"dateUpdated\\": \\"2023-05-24T14:45:25.684Z\\"",
                                       "    },",
                                       "    \\"containers\\": {",
                                       "        \\"cna\\": {",
                                       "            \\"affected\\": [",
                                       "                {",
                                       "         \\"[truncated 492 chars]; line: 14, column: 16])",
                                       " at [Source: (String)\\"{",
                                       "    \\"dataType\\": \\"CVE_RECORD\\",",
                                       "    \\"dataVersion\\": \\"5.0\\",",
                                       "    \\"cveMetadata\\": {",
                                       "        \\"cveId\\": \\"CVE-2023-33246\\",",
                                       "        \\"assignerOrgId\\": \\"f0158376-9dc2-43b6-827c-5f631a4d8d09\\",",
                                       "        \\"state\\": \\"PUBLISHED\\",",
                                       "        \\"assignerShortName\\": \\"apache\\",",
                                       "        \\"dateReserved\\": \\"2023-05-21T08:12:11.944Z\\",",
                                       "        \\"datePublished\\": \\"2023-05-24T14:45:25.684Z\\",",
                                       "        \\"dateUpdated\\": \\"2023-05-24T14:45:25.684Z\\"",
                                       "    },",
                                       "    \\"containers\\": {",
                                       "        \\"cna\\": {",
                                       "            \\"affected\\": [",
                                       "                {",
                                       "         \\"[truncated 492 chars]; line: 29, column: 14] (through reference chain: io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto.CveContentDTO[\\"containers\\"]->io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto.CveContainersDTO[\\"cna\\"])"
                                   ]
                               }
                           ]
                           """);
    }

    @Test
    void save_nominal(){
        final CveMitreDao dao = buildDao();
        final CveDTO dto = dao.readCveFile(buildPath("infrastructure/domain/cve/importer/mitre/cveMitreDaoTest/CVE-2023-33246.json"));

        assertText(dao.save(dto),
                   """
                           """);
    }

    // =================================================================================================================
    // TOOLS
    // =================================================================================================================
    CveMitreDao buildDao() {
        final CveMitreDao dao = new CveMitreDao(
                new DownloadService(),
                new ZipService(),
                new FileService(),
                new CveMapperConfiguration().cveDTOMitreMapper(),
                JsonMarshaller.getInstance().getDefaultObjectMapper(),
                cveEntityRepository,
                new CveMapperConfiguration().cveEntityMapper()
        );

        dao.setFolderTempPath(UnitTestHelper.buildTestFilePath("infrastructure/domain/cve/importer/mitre/cveMitreDao")
                                            .getAbsolutePath());
        dao.init();
        return dao;
    }

    private static File buildPath(final String path) {
        return UnitTestHelper.buildTestFilePath(path);
    }

}