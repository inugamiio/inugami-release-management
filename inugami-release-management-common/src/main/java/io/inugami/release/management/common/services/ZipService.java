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
package io.inugami.release.management.common.services;

import io.inugami.release.management.common.exception.CommonError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static io.inugami.api.exceptions.Asserts.assertFileExists;
import static io.inugami.api.exceptions.Asserts.assertNotNull;

@Slf4j
@Service
public class ZipService implements IZipService {

    public static final String MACOSX_DELETED = "__MACOSX";

    // =================================================================================================================
    // unzipFile
    // =================================================================================================================
    @Override
    public void unzipFile(final File file, final File target) throws IOException {
        assertNotNull(CommonError.UNZIP_FILE_REQUIRED, file);
        assertFileExists(CommonError.UNZIP_FILE_NOT_EXISTS.addDetail("{0}", file.getAbsoluteFile()), file);
        assertNotNull(CommonError.UNZIP_TARGET_REQUIRED, target);

        if (!target.getParentFile().exists()) {
            target.getParentFile().mkdirs();
        }

        log.info("begin unzip {} to {}", file.getAbsolutePath(), target.getAbsolutePath());
        processUnzip(file, target);
        log.info("file unzipped {} to {}", file.getAbsolutePath(), target.getAbsolutePath());
    }

    private void processUnzip(final File file, final File target) throws IOException {
        final File            targetFile    = target.getAbsoluteFile();
        final FileInputStream fileZipStream = openFileInputStream(file);
        final ZipInputStream  zip           = new ZipInputStream(fileZipStream);
        try {
            ZipEntry entry;
            do {
                entry = zip.getNextEntry();
                if (entry != null && !targetFile.getAbsolutePath().contains(MACOSX_DELETED)) {
                    unzipFile(targetFile, zip, entry);
                }
            } while (entry != null);

        } catch (final IOException e) {
            log.error(e.getMessage());
            throw e;
        } finally {
            close(() -> zip.closeEntry());
            close(() -> zip.close());
            close(() -> fileZipStream.close());
        }
    }

    private void unzipFile(final File target,
                           final ZipInputStream zip,
                           final ZipEntry entry)
            throws FileNotFoundException, IOException {
        final byte[] buffer   = new byte[1024];
        final String fileName = entry.getName();
        final File   newFile  = buildFileEntry(target, fileName);

        log.info("unzip : {}", newFile.getAbsolutePath());


        if (entry.isDirectory()) {
            newFile.mkdirs();
        } else {
            try (final FileOutputStream fos = new FileOutputStream(newFile)) {
                int len;
                while ((len = zip.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
            }
        }

        close(() -> zip.closeEntry());
        close(() -> zip.closeEntry());
    }


    // =================================================================================================================
    // TOOLS
    // =================================================================================================================
    private FileInputStream openFileInputStream(final File tomcatZip) throws IOException {
        try {
            return new FileInputStream(tomcatZip);
        } catch (final FileNotFoundException e) {
            throw e;
        }
    }

    private void close(final AutoCloseable closable) {
        try {
            closable.close();
        } catch (final Exception e) {
            log.error(e.getMessage());
        }

    }

    private File buildFileEntry(final File target, final String fileName) {

        final String path = new StringBuilder(target.getAbsolutePath())
                .append(File.separator)
                .append(fileName)
                .toString();

        final File result = new File(path);

        final File parent = result.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }

        return result;
    }

}
