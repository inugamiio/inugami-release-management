package io.inugami.release.management.core.domain.cve.importer.mitre;

import io.inugami.commons.test.UnitTestHelper;
import io.inugami.release.management.api.domain.cve.dto.CveDTO;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static io.inugami.commons.test.UnitTestHelper.assertText;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MitreImporterScanTaskTest {
    @Mock
    private ICveMitreDao cveMitreDao;
    @Mock
    private File         file;


    // =================================================================================================================
    // INIT
    // =================================================================================================================
    @BeforeEach
    void init() {
        lenient().when(cveMitreDao.readCveFile(any(File.class)))
                 .thenAnswer(answer -> UnitTestHelper.loadJson(answer.getArgument(0, File.class).getPath(),
                                                               CveDTO.class));

        lenient().when(cveMitreDao.save(any(CveDTO.class)))
                 .thenAnswer(answer -> answer.getArgument(0, CveDTO.class).toBuilder().id(1L).build());
    }

    // =================================================================================================================
    // CALL
    // =================================================================================================================
    //TODO: implement task
    @Test
    void call_CVE_2023_33246() throws Exception {
        when(file.getPath()).thenReturn("core/domain/cve/importer/mitre/mitreImporterScanTask/cveDTO_CVE-2023-33246.json");
        assertText(buildTask().call(),
                   """
                           {
                             "comment" : "Spark's Apache Maven-based build includes a convenience script, 'build/mvn', that downloads and runs a zinc server to speed up compilation. It has been included in release branches since 1.3.x, up to and including master. This server will accept connections from external hosts by default. A specially-crafted request to the zinc server could cause it to reveal information in files readable to the developer account running the build. Note that this issue does not affect end users of Spark, only developers building Spark from source code.",
                             "cveSeverity" : "LOW",
                             "datePublished" : "2018-10-24T00:00:00",
                             "id" : 1,
                             "javaArtifact" : true,
                             "link" : "http://www.securityfocus.com/bid/105756",
                             "name" : "CVE-2018-11804",
                             "productsAffected" : [ {
                               "product" : "Apache Spark",
                               "vendor" : "Apache Software Foundation"
                             } ],
                             "title" : "[dev] 20181024 CVE-2018-11804: Apache Spark build/mvn runs zinc, and can expose information from build machines",
                             "uuid" : "CVE-2018-11804"
                           }
                           """);

    }

    // =================================================================================================================
    // TOOLS
    // =================================================================================================================
    MitreImporterScanTask buildTask() {
        return MitreImporterScanTask.builder()
                                    .cveMitreDao(cveMitreDao)
                                    .file(file)
                                    .build();
    }
}