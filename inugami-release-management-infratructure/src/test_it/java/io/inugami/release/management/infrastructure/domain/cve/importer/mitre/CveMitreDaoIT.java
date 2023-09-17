package io.inugami.release.management.infrastructure.domain.cve.importer.mitre;

import io.inugami.api.marshalling.JsonMarshaller;
import io.inugami.commons.files.FilesUtils;

import io.inugami.release.management.common.services.DownloadService;
import io.inugami.release.management.common.services.ZipService;
import io.inugami.release.management.infrastructure.common.FileService;
import io.inugami.release.management.infrastructure.domain.cve.CveMapperConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

/*
-Dlogback.configurationFile={workspaces}/inugami-release-management-infratructure/src/test_it/resources/logback.xml
 */
@Slf4j
class CveMitreDaoIT {

    public static void main(final String... args) {
        final CveMitreDao dao    = buildDao();
        final File        folder = dao.downloadCve();
        final List<File>  files  = dao.getAllFiles(folder);
        log.info("nb files : {}", files.size());
    }

    static CveMitreDao buildDao() {
        final CveMitreDao dao = new CveMitreDao(
                new DownloadService(),
                new ZipService(),
                new FileService(),
                new CveMapperConfiguration().cveDTOMitreMapper(),
                JsonMarshaller.getInstance().getDefaultObjectMapper()
        );

        dao.setCveMitreUrl("https://github.com/CVEProject/cvelistV5/archive/refs/heads/main.zip");
        final File userHome = new File(System.getProperty("user.home")).getAbsoluteFile();
        final File file     = FilesUtils.buildFile(userHome, ".inugami", "release-management", "mitre");
        dao.setFolderTempPath(file.getAbsolutePath());
        dao.init();
        return dao;
    }

}