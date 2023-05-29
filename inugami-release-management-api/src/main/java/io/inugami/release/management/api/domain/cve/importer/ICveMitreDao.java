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

import io.inugami.release.management.api.domain.cve.dto.CveContentDTO;

import java.io.File;
import java.util.List;

public interface ICveMitreDao {

    // =================================================================================================================
    // READ
    // =================================================================================================================
    boolean isCveZipFileExists();

    File downloadCve();

    List<File> getAllFiles();

    CveContentDTO readCveFile(final File file);
}
