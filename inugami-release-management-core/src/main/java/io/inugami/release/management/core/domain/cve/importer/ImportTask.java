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
package io.inugami.release.management.core.domain.cve.importer;

import io.inugami.api.exceptions.UncheckedException;
import io.inugami.release.management.api.domain.cve.ICveDao;
import io.inugami.release.management.api.domain.cve.importer.CveImporter;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
@Builder
@RequiredArgsConstructor
public class ImportTask implements Callable<Void> {
    private final ICveDao     cveDao;
    private final CveImporter importer;

    @Override
    public Void call() throws Exception {
        try {
            importer.process();
        } catch (final Throwable e) {
            throw new UncheckedException(e.getMessage(), e);
        }
        return null;
    }
}
