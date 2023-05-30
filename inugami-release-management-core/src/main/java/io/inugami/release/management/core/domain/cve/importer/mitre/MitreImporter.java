/* --------------------------------------------------------------------
 *  Inugami
 * --------------------------------------------------------------------
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package io.inugami.release.management.core.domain.cve.importer.mitre;

import io.inugami.api.exceptions.TechnicalException;
import io.inugami.commons.threads.ThreadsExecutorService;
import io.inugami.release.management.api.common.dto.DependencyRuleDTO;
import io.inugami.release.management.api.domain.cve.importer.CveImporter;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

// https://cwe.mitre.org/data/downloads.html
@Slf4j
@Component
@RequiredArgsConstructor
public class MitreImporter implements CveImporter {
    final         long                   timeout;
    private final ICveMitreDao           cveMitreDao;
    private final ThreadsExecutorService mitreImporterExecutorService;

    // =================================================================================================================
    // PROCESS
    // =================================================================================================================
    @Override
    public List<DependencyRuleDTO> process() {
        if (!cveMitreDao.isCveZipFileExists()) {
            cveMitreDao.downloadCve();
        }
        return scanCve();
    }


    // =================================================================================================================
    // PRIVATE
    // =================================================================================================================
    protected List<DependencyRuleDTO> scanCve() {
        List<DependencyRuleDTO> result = null;

        final List<File>                        files = cveMitreDao.getAllFiles();
        final List<Callable<DependencyRuleDTO>> tasks = new ArrayList<>();

        for (final File file : files) {
            tasks.add(MitreImporterScanTask.builder()
                                           .file(file)
                                           .cveMitreDao(cveMitreDao)
                                           .build());
        }

        final int           nbTasks = tasks.size();
        final AtomicInteger cursor  = new AtomicInteger();

        try {
            result = mitreImporterExecutorService.runAndGrab(tasks,
                                                             (value, task) -> this.onStepDone(value, task, nbTasks, cursor),
                                                             (exception, task) -> this.onError(exception, task, nbTasks, cursor));
        } catch (final TechnicalException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        return result;
    }

    private <T> void onStepDone(final T t, final Callable<T> tCallable, final int nbTasks, final AtomicInteger cursor) {
        logProgression(nbTasks, cursor);
    }

    private <T> void onError(final Exception e, final Callable<T> tCallable, final int nbTasks, final AtomicInteger cursor) {
        logProgression(nbTasks, cursor);
    }

    private void logProgression(final int nbTasks, final AtomicInteger cursor) {
        final int currentCursor = cursor.incrementAndGet();
        log.info("MitreImporter process {}/{}", currentCursor, nbTasks);
    }

}
