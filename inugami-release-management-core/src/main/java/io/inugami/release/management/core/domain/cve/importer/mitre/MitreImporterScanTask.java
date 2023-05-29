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

import io.inugami.api.spring.NotSpringBean;
import io.inugami.release.management.api.domain.cve.dto.CveDTO;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.concurrent.Callable;

@NotSpringBean
@Builder
@RequiredArgsConstructor
public class MitreImporterScanTask implements Callable<CveDTO> {
    private final ICveMitreDao cveMitreDao;
    private final File         file;


    // =================================================================================================================
    // CALL
    // =================================================================================================================
    @Override
    public CveDTO call() throws Exception {
        final CveDTO data   = cveMitreDao.readCveFile(file);
        return cveMitreDao.save(data);
    }

}
