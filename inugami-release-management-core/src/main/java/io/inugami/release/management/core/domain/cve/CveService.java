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
import io.inugami.release.management.api.domain.cve.ICveDao;
import io.inugami.release.management.api.domain.cve.ICveService;
import io.inugami.release.management.api.domain.cve.exception.CveError;
import io.inugami.release.management.api.domain.cve.importer.CveImporter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static io.inugami.api.exceptions.Asserts.assertFalse;

@Slf4j
@RequiredArgsConstructor
@Service
public class CveService implements ICveService {

    // =================================================================================================================
    // ATTRIBUTES
    // =================================================================================================================
    private static final Logger            PROCESS_LOG = LoggerFactory.getLogger("PROCESS");
    public static final  String            SUCCESS     = "SUCCESS";
    public static final  String            ERROR       = "ERROR";
    private final        ICveDao           cveDao;
    private final        List<CveImporter> importers;


    // =================================================================================================================
    // CREATE
    // =================================================================================================================

    @Override
    public String importCve() {
        final MdcService mdc = MdcService.getInstance();
        assertFalse(CveError.IMPORT_ALREADY_RUNNING, cveDao.isImportRunning());

        final String processUid = UUID.randomUUID().toString();
        mdc.processId(processUid)
           .processName("importCve");
        cveDao.saveNewImportRun(processUid);

        mdc.lifecycleIn(() -> PROCESS_LOG.info("start importing CVE"));

        final List<Throwable> errors = new ArrayList<>();
        for (final CveImporter importer : importers) {

            try {
                log.info("starting importer : {}", importer.getName());
                importer.process();
                log.info("done importer : {}", importer.getName());
            } catch (final Throwable e) {
                log.info("error importer : {} - {}", importer.getName(), e.getMessage());
                errors.add(e);
                if (log.isDebugEnabled()) {
                    log.error(e.getMessage(), e);
                }
            }
        }


        if (errors.isEmpty()) {
            mdc.processStatus(SUCCESS);
            mdc.lifecycleOut(() -> PROCESS_LOG.info("done importing CVE"));
        } else {
            mdc.processStatus(ERROR);
            mdc.lifecycleOut(() -> PROCESS_LOG.error("done importing CVE"));
        }


        return processUid;
    }


}
