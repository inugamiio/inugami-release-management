package io.inugami.release.management.infrastructure.domain.cve.importer.mitre;

import io.inugami.commons.test.UnitTestHelper;
import io.inugami.release.management.api.domain.cve.dto.CveDTO;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import io.inugami.release.management.infrastructure.SpringBootIntegrationTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

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
                           """);
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