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
package io.inugami.release.management.infrastructure.domain.cve.importer.mitre;

import io.inugami.commons.connectors.IHttpBasicConnector;
import io.inugami.release.management.api.common.dto.DependencyRuleDTO;
import io.inugami.release.management.api.common.dto.GavDTO;
import io.inugami.release.management.api.domain.cve.dto.CveDTO;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto.CveAffectedDTO;
import io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto.CveContentDTO;
import io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto.CveVersionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CveMitreDao implements ICveMitreDao {

    // =================================================================================================================
    // ATTRIBUTES
    // =================================================================================================================
    public static final String              MAVEN = "maven";
    private final       IHttpBasicConnector mitreImporterHttpConnector;


    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    @Override
    public CveDTO save(final CveDTO cve) {
        return null;
    }

    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Override
    public boolean isCveZipFileExists() {
        return false;
    }

    @Override
    public File downloadCve() {
        return null;
    }

    @Override
    public List<File> getAllFiles() {
        final List<File> result = new ArrayList<>();
        return result;
    }

    @Override
    public CveDTO readCveFile(final File file) {
        return null;
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


    private List<DependencyRuleDTO> convertToDependencyRuleDTO(final CveContentDTO cveContent) {
        final List<DependencyRuleDTO> result = new ArrayList<>();


        return result;
    }

    private DependencyRuleDTO buildDependencyRule(final GavDTO gav, final CveContentDTO cveContent) {
        final var builder = DependencyRuleDTO.builder();
        builder.cveId(cveContent.getCveMetadata().getCveId());

        return builder.build();
    }
}
