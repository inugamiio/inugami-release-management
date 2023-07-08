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
import io.inugami.release.management.infrastructure.datasource.neo4j.entity.CveImportRunEntity;
import io.inugami.release.management.infrastructure.datasource.neo4j.mapper.CveImportRunEntityMapper;
import io.inugami.release.management.infrastructure.datasource.neo4j.repository.CveImportRunEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CveDao implements ICveDao {
    public static final String                       LINE = "\n";
    private final       Clock                        clock;
    private final       CveImportRunEntityMapper     cveImportRunEntityMapper;
    private final       CveImportRunEntityRepository cveImportRunEntityRepository;

    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @Transactional
    @Override
    public CveImportRunDTO saveNewImportRun(final String processUid) {
        final CveImportRunDTO newRun = CveImportRunDTO.builder()
                                                      .uid(processUid)
                                                      .startDate(LocalDateTime.now(clock))
                                                      .status(CveImportRunStatus.IN_PROGRESS)
                                                      .build();

        final CveImportRunEntity entity = cveImportRunEntityMapper.convertToEntity(newRun);

        final CveImportRunEntity result = cveImportRunEntityRepository.save(entity);

        return cveImportRunEntityMapper.convertToDto(result);
    }


    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Override
    public boolean isImportRunning() {
        return cveImportRunEntityRepository.findRunning().isPresent();
    }

    @Override
    public CveImportRunDTO getImportRun(final String processUid) {
        return cveImportRunEntityMapper.convertToDto(getByUid(processUid));
    }

    private CveImportRunEntity getByUid(final String processUid) {
        return cveImportRunEntityRepository.findByUid(processUid).orElse(null);
    }

    // =================================================================================================================
    // UPDATE
    // =================================================================================================================

    @Transactional
    @Override
    public void changeRunState(final String processUid, final CveImportRunStatus status) {
        final CveImportRunEntity entity = getByUid(processUid);
        if (entity != null) {
            entity.setStatus(status);
            entity.setDoneDate(LocalDateTime.now(clock));
            cveImportRunEntityRepository.save(entity);
        }
    }

    @Transactional
    @Override
    public void changeRunState(final String processUid, final List<Throwable> errors) {
        final CveImportRunEntity entity = getByUid(processUid);
        if (entity != null) {
            final List<String> messages = new ArrayList<>();
            if (errors != null) {
                errors.stream()
                      .map(Throwable::getMessage)
                      .filter(Objects::nonNull)
                      .forEach(messages::add);
            }
            entity.setStatus(CveImportRunStatus.ERROR);
            entity.setMessage(String.join(LINE, messages));
            entity.setDoneDate(LocalDateTime.now(clock));
            cveImportRunEntityRepository.save(entity);
        }
    }
    // =================================================================================================================
    // DELETE
    // =================================================================================================================


}
