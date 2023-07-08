package io.inugami.release.management.infrastructure.domain.cve.importer.mitre;

import io.inugami.commons.connectors.IHttpBasicConnector;
import io.inugami.commons.marshaling.JsonMarshaller;
import io.inugami.commons.test.UnitTestHelper;
import io.inugami.release.management.common.services.DownloadService;
import io.inugami.release.management.common.services.ZipService;
import io.inugami.release.management.infrastructure.common.FileService;
import io.inugami.release.management.infrastructure.domain.cve.CveMapperConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.List;

import static io.inugami.commons.test.UnitTestHelper.assertText;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CveMitreDaoTest {

    @Mock
    private IHttpBasicConnector mitreImporterHttpConnector;

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

    // =================================================================================================================
    // TOOLS
    // =================================================================================================================
    CveMitreDao buildDao() {
        final CveMitreDao dao = new CveMitreDao(
                new DownloadService(),
                new ZipService(),
                new FileService(),
                new CveMapperConfiguration().cveDTOMitreMapper(),
                JsonMarshaller.getInstance().getDefaultObjectMapper()
        );

        dao.setFolderTempPath(UnitTestHelper.buildTestFilePath("infrastructure/domain/cve/importer/mitre/cveMitreDao")
                                            .getAbsolutePath());
        dao.init();
        return dao;
    }
}