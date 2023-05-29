package io.inugami.release.management.infrastructure.domain.cve.importer.mitre.mapper;

import io.inugami.commons.test.UnitTestHelper;
import io.inugami.release.management.infrastructure.domain.cve.CveMapperConfiguration;
import io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto.CveContentDTO;
import org.junit.jupiter.api.Test;

import static io.inugami.commons.test.UnitTestHelper.assertText;

class CveDTOMitreMapperTest {

    // =================================================================================================================
    // TEST
    // =================================================================================================================
    @Test
    void convertToCveDto() {
        final CveContentDTO input = UnitTestHelper.loadJson("infrastructure/domain/cve/importer/mitre/mapper/cveDTOMitreMapperTest/CVE-2023-33246.json", CveContentDTO.class);
        assertText(buildMapper().convertToCveDto(input), """
                {
                   "cveSeverity" : "MODERATE",
                   "datePublished" : "2023-05-24T14:45:25.684",
                   "javaArtifact" : true,
                   "link" : "https://lists.apache.org/thread/1s8j2c8kogthtpv3060yddk03zq0pxyp",
                   "name" : "CVE-2023-33246",
                   "productsAffected" : [ {
                     "product" : "Apache RocketMQ",
                     "rules" : [ {
                       "major" : {
                         "ruleType" : "=",
                         "version" : 5
                       },
                       "minor" : {
                         "ruleType" : "<=",
                         "version" : 1
                       },
                       "patch" : {
                         "ruleType" : "<=",
                         "version" : 1
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
    CveDTOMitreMapper buildMapper() {
        return new CveMapperConfiguration().cveDTOMitreMapper();
    }
}