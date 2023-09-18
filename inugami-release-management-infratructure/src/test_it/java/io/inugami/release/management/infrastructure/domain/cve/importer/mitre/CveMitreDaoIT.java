package io.inugami.release.management.infrastructure.domain.cve.importer.mitre;

import io.inugami.commons.test.UnitTestHelper;
import io.inugami.commons.test.api.NumberLineMatcher;
import io.inugami.release.management.api.domain.cve.dto.CveDTO;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import io.inugami.release.management.infrastructure.SpringBootIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static io.inugami.commons.test.UnitTestHelper.assertText;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CveMitreDaoIT extends SpringBootIntegrationTest {
    // =================================================================================================================
    // ATTRIBUTES
    // =================================================================================================================
    @Autowired
    private ICveMitreDao dao;


    // =================================================================================================================
    // INIT
    // =================================================================================================================
    protected void init() {
    }

    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @Test
    void save_nominal() {
        CveDTO data = UnitTestHelper.loadIntegrationJson("infrastructure/domain/cve/importer/mitre/cveMitreDaoIT/save_nominal_data.json", CveDTO.class);

        final CveDTO result = dao.save(data);
        assertText(result,
                   """
                           {
                             "cveSeverity" : "MODERATE",
                             "datePublished" : "2023-05-24T14:45:25.684",
                             "id" : 0,
                             "javaArtifact" : true,
                             "link" : "https://lists.apache.org/thread/1s8j2c8kogthtpv3060yddk03zq0pxyp",
                             "name" : "CVE-2023-33246",
                             "productsAffected" : [ {
                               "id" : 1,
                               "product" : "Apache RocketMQ",
                               "rules" : [ {
                                 "id" : 2,
                                 "major" : {
                                   "id" : 5,
                                   "ruleType" : "=",
                                   "version" : 5
                                 },
                                 "minor" : {
                                   "id" : 3,
                                   "ruleType" : "<=",
                                   "version" : 1
                                 },
                                 "patch" : {
                                   "id" : 4,
                                   "ruleType" : "<=",
                                   "version" : 1
                                 }
                               } ],
                               "vendor" : "Apache Software Foundation"
                             } ],
                             "title" : "Apache RocketMQ: Possible remote code execution vulnerability when using the update configuration function",
                             "uuid" : "CVE-2023-33246"
                           }
                           """,
                   NumberLineMatcher.of(8,11,13,18,23));

        final Optional<CveDTO> cve = dao.getById(result.getId());
        assertThat(cve).isPresent();
    }


    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Test
    void downloadCve_nominal() {
        dao.downloadCve();
        final List<File> files = dao.getAllFiles();
        assertThat(files).isNotEmpty();
    }


}