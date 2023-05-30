package io.inugami.release.management.core.domain.cve.importer.mitre;

import io.inugami.commons.test.UnitTestHelper;
import io.inugami.release.management.api.common.dto.DependencyRuleDTO;
import io.inugami.release.management.api.domain.cve.dto.CveContentDTO;
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
                                                               CveContentDTO.class));
    }

    // =================================================================================================================
    // CALL
    // =================================================================================================================
    @Test
    void call_CVE_2023_33246() throws Exception {
        when(file.getPath()).thenReturn("core/domain/cve/importer/mitre/mitreImporterScanTask/CVE-2023-33246.json");
        final DependencyRuleDTO result = buildTask().call();

        assertText(result, """
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