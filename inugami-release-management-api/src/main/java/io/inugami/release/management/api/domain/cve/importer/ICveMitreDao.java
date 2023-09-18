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
package io.inugami.release.management.api.domain.cve.importer;

import io.inugami.release.management.api.domain.cve.dto.CveDTO;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface ICveMitreDao {
    // =================================================================================================================
    // CREATE
    // =================================================================================================================
    CveDTO save(CveDTO cve);

    // =================================================================================================================
    // READ
    // =================================================================================================================
    Optional<CveDTO> getById(final Long id);

    boolean isCveZipFileExists();

    File downloadCve();

    List<File> getAllFiles();

    CveDTO readCveFile(final File file);


}
