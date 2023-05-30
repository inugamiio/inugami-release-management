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
package io.inugami.release.management.core.domain.cve;

import io.inugami.api.monitoring.MdcService;
import io.inugami.commons.threads.ThreadsExecutorService;
import io.inugami.release.management.api.common.dto.DependencyRuleDTO;
import io.inugami.release.management.api.domain.cve.ICveDao;
import io.inugami.release.management.api.domain.cve.ICveService;
import io.inugami.release.management.api.domain.cve.exception.CveError;
import io.inugami.release.management.api.domain.cve.importer.CveImporter;
import io.inugami.release.management.core.domain.cve.importer.ImportTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import static io.inugami.api.exceptions.Asserts.assertFalse;

@Slf4j
@RequiredArgsConstructor
@Service
public class CveService implements ICveService {

    // =================================================================================================================
    // ATTRIBUTES
    // =================================================================================================================
    private final ICveDao                cveDao;
    private final List<CveImporter>      importers;
    private final ThreadsExecutorService cveServiceExecutorService;

    private Map<Class<?>, List<DependencyRuleDTO>> DONE_TASKS = new ConcurrentHashMap<>();

    // =================================================================================================================
    // CREATE
    // =================================================================================================================

    @Override
    public String importCve() {
        assertFalse(CveError.IMPORT_ALREADY_RUNNING, cveDao.isImportRunning());
        DONE_TASKS.clear();
        final String processUid = UUID.randomUUID().toString();
        cveDao.saveNewImportRun(processUid);

        final List<Callable<List<DependencyRuleDTO>>> tasks   = new ArrayList<>();
        final int                                     nbTasks = importers.size();

        for (final CveImporter importer : importers) {
            tasks.add(ImportTask.builder()
                                .cveDao(cveDao)
                                .importer(importer)
                                .build());
        }
        cveServiceExecutorService.run(tasks,
                                      (values, task) -> this.onDone(values, task, nbTasks, processUid, MdcService.getInstance().getAllMdcExtended()),
                                      (exception, task) -> this.onError(exception, task, nbTasks, processUid, MdcService.getInstance().getAllMdcExtended()));

        return processUid;
    }


    // =================================================================================================================
    // Asynchronous process on importer tasks finish
    // =================================================================================================================
    private void onDone(final List<DependencyRuleDTO> values,
                        final Callable<List<DependencyRuleDTO>> task,
                        final int nbTasks,
                        final String processUid,
                        final Map<String, Serializable> allMdc) {
        MdcService.getInstance().setMdc(allMdc);
        DONE_TASKS.put(task.getClass(), values == null ? new ArrayList<>() : null);
        if (allTasksDone(nbTasks)) {
            finalizeImportProcess();
        }
    }


    private void onError(final Exception exception,
                         final Callable<List<DependencyRuleDTO>> task,
                         final int nbTasks,
                         final String processUid,
                         final Map<String, Serializable> allMdc) {
        MdcService.getInstance().setMdc(allMdc);
        log.error(exception.getMessage(), exception);
        DONE_TASKS.put(task.getClass(), new ArrayList<>());
        if (allTasksDone(nbTasks)) {
            finalizeImportProcess();
        }
    }

    private synchronized boolean allTasksDone(final int nbTasks) {
        return DONE_TASKS.size() == nbTasks;
    }

    private void finalizeImportProcess() {
        log.info("finalizing import process");

        final List<DependencyRuleDTO> cveFound = aggregateCve(DONE_TASKS);
        log.info("{} CVE found", cveFound.size());
    }

    private List<DependencyRuleDTO> aggregateCve(final Map<Class<?>, List<DependencyRuleDTO>> data) {
        final Set<DependencyRuleDTO> buffer = new LinkedHashSet<>();
        for (final Map.Entry<Class<?>, List<DependencyRuleDTO>> entry : data.entrySet()) {
            buffer.addAll(entry.getValue());
        }

        final List<DependencyRuleDTO> result = new ArrayList<>(buffer);
        Collections.sort(result);
        return result;
    }

}
