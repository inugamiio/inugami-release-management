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
import io.inugami.release.management.api.domain.cve.dto.CveContentDTO;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CveMitreDao implements ICveMitreDao {

    private final IHttpBasicConnector mitreImporterHttpConnector;

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
    public CveContentDTO readCveFile(final File file) {
        return null;
    }
}
