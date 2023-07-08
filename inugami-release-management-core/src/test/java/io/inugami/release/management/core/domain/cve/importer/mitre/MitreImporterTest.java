package io.inugami.release.management.core.domain.cve.importer.mitre;

import io.inugami.api.exceptions.UncheckedException;
import io.inugami.api.monitoring.MdcService;
import io.inugami.commons.test.UnitTestHelper;
import io.inugami.commons.test.logs.BasicLogEvent;
import io.inugami.commons.test.logs.DefaultLogListener;
import io.inugami.commons.test.logs.LogListener;
import io.inugami.commons.test.logs.LogTestAppender;
import io.inugami.commons.threads.ThreadsExecutorService;
import io.inugami.release.management.api.domain.cve.dto.CveDTO;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import io.inugami.release.management.core.domain.cve.CveConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.inugami.commons.test.UnitTestHelper.assertThrows;
import static io.inugami.release.management.api.domain.cve.exception.CveError.ERROR_IN_IMPORTING_STEP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MitreImporterTest {


    @Mock
    private ICveMitreDao cveMitreDao;
    @Mock
    private File         file;

    // =================================================================================================================
    // INIT
    // =================================================================================================================


    @BeforeEach
    void init() {
        MdcService.getInstance().clear();
        lenient().when(cveMitreDao.readCveFile(any(File.class)))
                 .thenAnswer(answer -> UnitTestHelper.loadJson(answer.getArgument(0, File.class).getPath(),
                                                               CveDTO.class));
    }


    // =================================================================================================================
    // process
    // =================================================================================================================
    @Test
    void process_nominal() {

        final List<BasicLogEvent> logs     = new ArrayList<>();
        final LogListener         listener = new DefaultLogListener(MitreImporter.class, logs::add);
        LogTestAppender.register(listener);

        when(cveMitreDao.isCveZipFileExists()).thenReturn(false);
        when(cveMitreDao.getAllFiles()).thenReturn(List.of(file));
        final String file = "core/domain/cve/importer/mitre/mitreImporterScanTask/CVE-2023-33246.json";
        final CveDTO cve  = UnitTestHelper.loadJson(file, CveDTO.class);
        when(cveMitreDao.readCveFile(any(File.class))).thenReturn(cve);

        final ThreadsExecutorService executor = buildExecutor();
        buildImporter(executor).process();


        LogTestAppender.removeListener(listener);
        assertThat(logs).isNotEmpty();
    }


    @Test
    void process_withError() {
        MdcService.getInstance().clear();
        final List<BasicLogEvent> logs     = new ArrayList<>();
        final LogListener         listener = new DefaultLogListener(MitreImporter.class, logs::add);
        LogTestAppender.register(listener);

        when(cveMitreDao.isCveZipFileExists()).thenReturn(false);
        when(cveMitreDao.getAllFiles()).thenReturn(List.of(file));
        final String file = "core/domain/cve/importer/mitre/mitreImporterScanTask/CVE-2023-33246.json";
        final CveDTO cve  = UnitTestHelper.loadJson(file, CveDTO.class);

        when(cveMitreDao.readCveFile(any(File.class))).thenThrow(new UncheckedException("some error"));
        final ThreadsExecutorService executor = buildExecutor();


        assertThrows(ERROR_IN_IMPORTING_STEP,
                     () -> buildImporter(executor).process(),
                     "core/domain/cve/importer/mitre/mitreImporterTest/process_withError.json");
        executor.shutdown();

        LogTestAppender.removeListener(listener);
        assertThat(logs).isNotEmpty();
        MdcService.getInstance().clear();
    }


    // =================================================================================================================
    // TOOLS
    // =================================================================================================================
    MitreImporter buildImporter(final ThreadsExecutorService executor) {
        return MitreImporter.builder()
                            .cveMitreDao(cveMitreDao)
                            .mitreImporterExecutorService(executor)
                            .build();
    }

    private ThreadsExecutorService buildExecutor() {
        return new CveConfiguration().mitreImporterExecutorService(
                20,
                1000L
        );
    }
}