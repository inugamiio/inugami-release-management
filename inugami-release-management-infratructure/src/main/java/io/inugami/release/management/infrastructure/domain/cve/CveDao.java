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
package io.inugami.release.management.infrastructure.domain.cve;

import io.inugami.release.management.api.domain.cve.ICveDao;
import io.inugami.release.management.api.domain.cve.dto.CveImportRunDTO;
import io.inugami.release.management.api.domain.cve.dto.CveImportRunStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CveDao implements ICveDao {
    private final Clock clock;

    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @Transactional
    @Override
    public CveImportRunDTO saveNewImportRun(final String processUid) {
        return CveImportRunDTO.builder()
                              .startDate(LocalDateTime.now(clock))
                              .status(CveImportRunStatus.IN_PROGRESS)
                              .build();
    }

    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Override
    public boolean isImportRunning() {
        return false;
    }
    // =================================================================================================================
    // UPDATE
    // =================================================================================================================

    // =================================================================================================================
    // DELETE
    // =================================================================================================================


}
