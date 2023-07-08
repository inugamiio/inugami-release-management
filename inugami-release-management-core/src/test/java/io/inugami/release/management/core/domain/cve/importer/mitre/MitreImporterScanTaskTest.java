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
    }

    // =================================================================================================================
    // CALL
    // =================================================================================================================
    @Test
    void call_CVE_2023_33246() throws Exception {
        when(file.getPath()).thenReturn("core/domain/cve/importer/mitre/mitreImporterScanTask/CVE-2023-33246.json");
        when(cveMitreDao.save(any(CveDTO.class))).thenAnswer(answer -> answer.getArgument(0, CveDTO.class).toBuilder().id(1L).build());
        final CveDTO result = buildTask().call();

        assertText(result, """
                {
                    "comment" : "For RocketMQ versions 5.1.0 and below, under certain conditions, there is a risk of remote command execution.\\n\\nSeveral components of RocketMQ, including NameServer, Broker, and Controller, are leaked on the extranet and lack permission verification, an attacker can exploit this vulnerability by using the update configuration function to execute commands as the system users that RocketMQ is running as. Additionally, an attacker can achieve the same effect by forging the RocketMQ protocol content.\\n\\nTo prevent these attacks, users are recommended to upgrade to version 5.1.1 or above for using RocketMQ 5.x or 4.9.6 or above for using RocketMQ 4.x.\\n",
                    "cveSeverity" : "MODERATE",
                    "datePublished" : "2023-05-24T14:45:25",
                    "id" : 1,
                    "link" : "https://lists.apache.org/thread/1s8j2c8kogthtpv3060yddk03zq0pxyp",
                    "name" : "CVE-2023-33246",
                    "productsAffected" : [ {
                      "affectVersions" : [ {
                        "artifactId" : "some-artifact",
                        "groupId" : "io.inugami",
                        "version" : "1.0.0"
                      } ],
                      "product" : "Apache RocketMQ",
                      "rules" : [ {
                        "major" : {
                          "ruleType" : "=",
                          "version" : 5
                        },
                        "minor" : {
                          "ruleType" : "<",
                          "version" : 1
                        },
                        "patch" : {
                          "ruleType" : "<=",
                          "version" : 0
                        }
                      } ],
                      "vendor" : "Apache Software Foundation"
                    } ],
                    "title" : "Apache RocketMQ: Possible remote code execution vulnerability when using the update configuration function",
                    "uuid" : "CVE-2023-33246"
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