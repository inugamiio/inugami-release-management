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
import io.inugami.release.management.api.common.dto.DependencyRuleDTO;
import io.inugami.release.management.api.domain.cve.dto.CveAffectedDTO;
import io.inugami.release.management.api.domain.cve.dto.CveContentDTO;
import io.inugami.release.management.api.domain.cve.dto.CveVersionDTO;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

@NotSpringBean
@Builder
@RequiredArgsConstructor
public class MitreImporterScanTask implements Callable<DependencyRuleDTO> {
    public static final String       MAVEN = "maven";
    private final       ICveMitreDao cveMitreDao;
    private final       File         file;


    // =================================================================================================================
    // CALL
    // =================================================================================================================
    @Override
    public DependencyRuleDTO call() throws Exception {
        final CveContentDTO cveContent = cveMitreDao.readCveFile(file);
        if (!isMavenCve(cveContent)) {
            return null;
        }

        return convertToDependencyRuleDTO(cveContent);
    }


    // =================================================================================================================
    // PRIVATE
    // =================================================================================================================
    protected boolean isMavenCve(final CveContentDTO cveContent) {
        return cveContent != null
                && cveContent.getContainers() != null
                && cveContent.getContainers().getCna() != null
                && cveContent.getContainers().getCna().getAffected() != null
                && containMavenVersion(cveContent.getContainers().getCna().getAffected());
    }

    private boolean containMavenVersion(final List<CveAffectedDTO> affected) {
        for (final CveAffectedDTO affectedDTO : affected) {
            for (final CveVersionDTO version : Optional.ofNullable(affectedDTO.getVersions()).orElse(new ArrayList<>())) {
                if (MAVEN.equalsIgnoreCase(version.getVersionType())) {
                    return true;
                }
            }
        }
        return false;
    }


    private DependencyRuleDTO convertToDependencyRuleDTO(final CveContentDTO cveContent) {
        final var builder = DependencyRuleDTO.builder();
        // TODO : implement mapper
        return builder.build();
    }
}
