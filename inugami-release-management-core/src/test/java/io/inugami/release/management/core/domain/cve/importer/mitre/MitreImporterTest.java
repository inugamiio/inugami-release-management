package io.inugami.release.management.core.domain.cve.importer.mitre;

import io.inugami.api.exceptions.UncheckedException;
import io.inugami.api.monitoring.MdcService;
import io.inugami.commons.test.UnitTestHelper;
import io.inugami.commons.test.api.SkipLineMatcher;
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
import java.util.List;

import static io.inugami.commons.test.UnitTestHelper.assertThrows;
import static io.inugami.release.management.api.domain.cve.exception.CveError.ERROR_IN_IMPORTING_STEP;
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
        UnitTestHelper.assertLogs(() -> {
                                      lenient().when(cveMitreDao.isCveZipFileExists()).thenReturn(false);
                                      lenient().when(cveMitreDao.getAllFiles()).thenReturn(List.of(file));
                                      final String file = "core/domain/cve/importer/mitre/mitreImporterScanTask/CVE-2023-33246.json";
                                      final CveDTO cve  = UnitTestHelper.loadJson(file, CveDTO.class);
                                      lenient().when(cveMitreDao.readCveFile(any(File.class))).thenReturn(cve);

                                      final ThreadsExecutorService executor = buildExecutor();
                                      buildImporter(executor).process();
                                  },
                                  MitreImporter.class,
                                  """
                                          [
                                              {
                                                  "loggerName":"io.inugami.release.management.core.domain.cve.importer.mitre.MitreImporter",
                                                  "level":"INFO",
                                                  "mdc":{}
                                                  "message":"retrieve mitre files..."
                                              },
                                              {
                                                  "loggerName":"io.inugami.release.management.core.domain.cve.importer.mitre.MitreImporter",
                                                  "level":"INFO",
                                                  "mdc":{}
                                                  "message":"1 mitre files found"
                                              }
                                          ]
                                          """);
    }


    @Test
    void process_withError() {
        UnitTestHelper.assertLogs(() -> {
                                      lenient().when(cveMitreDao.isCveZipFileExists()).thenReturn(false);
                                      lenient().when(cveMitreDao.getAllFiles()).thenReturn(List.of(file));
                                      final String file = "core/domain/cve/importer/mitre/mitreImporterScanTask/CVE-2023-33246.json";
                                      final CveDTO cve  = UnitTestHelper.loadJson(file, CveDTO.class);

                                      lenient().when(cveMitreDao.readCveFile(any(File.class))).thenThrow(new UncheckedException("some error"));
                                      final ThreadsExecutorService executor = buildExecutor();


                                      assertThrows(ERROR_IN_IMPORTING_STEP,
                                                   () -> buildImporter(executor).process(),
                                                   "core/domain/cve/importer/mitre/mitreImporterTest/process_withError.json");
                                      executor.shutdown();
                                  },
                                  MitreImporter.class,
                                  """
                                          [
                                              {
                                                  "loggerName":"io.inugami.release.management.core.domain.cve.importer.mitre.MitreImporter",
                                                  "level":"INFO",
                                                  "mdc":{}
                                                  "message":"retrieve mitre files..."
                                              },
                                              {
                                                  "loggerName":"io.inugami.release.management.core.domain.cve.importer.mitre.MitreImporter",
                                                  "level":"INFO",
                                                  "mdc":{}
                                                  "message":"1 mitre files found"
                                              },
                                              {
                                                  "loggerName":"io.inugami.release.management.core.domain.cve.importer.mitre.MitreImporter",
                                                  "level":"ERROR",
                                                  "mdc":{
                                                      "correlation_id":"24e68fae-e0a6-4a07-88b9-d3030b0d3d27",
                                                      "deviceIdentifier":"system",
                                                      "request_id":"4a73ed1b-e5c2-49ba-adc5-72594a8bee8c",
                                                      "service":"technical_main",
                                                      "traceId":"4a73ed1b-e5c2-49ba-adc5-72594a8bee8c"
                                                  },
                                                  "message":"io.inugami.api.exceptions.UncheckedException: some error"
                                              },
                                              {
                                                  "loggerName":"io.inugami.release.management.core.domain.cve.importer.mitre.MitreImporter",
                                                  "level":"INFO",
                                                  "mdc":{
                                                      "correlation_id":"24e68fae-e0a6-4a07-88b9-d3030b0d3d27",
                                                      "deviceIdentifier":"system",
                                                      "request_id":"4a73ed1b-e5c2-49ba-adc5-72594a8bee8c",
                                                      "service":"technical_main",
                                                      "traceId":"4a73ed1b-e5c2-49ba-adc5-72594a8bee8c"
                                                  },
                                                  "message":"MitreImporter process 1/1"
                                              },
                                              {
                                                  "loggerName":"io.inugami.release.management.core.domain.cve.importer.mitre.MitreImporter",
                                                  "level":"INFO",
                                                  "mdc":{}
                                                  "message":"MitreImporter process 2/1"
                                              }
                                          ]
                                          """,
                                  SkipLineMatcher.of(17, 19, 21, 29, 31, 33));
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