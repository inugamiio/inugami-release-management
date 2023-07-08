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

import io.inugami.api.exceptions.UncheckedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

@Slf4j
@Service
public class DownloadService implements IDownloadService {

    @Override
    public void download(final String url, final File target) {
        if (url == null || target == null) {
            return;
        }

        try {
            processDownload(url, target);
        } catch (final IOException e) {
            throw new UncheckedException(e);
        }
    }

    private void processDownload(final String urlToDownload, final File target) throws IOException {

        log.info("downloading {} to {}", urlToDownload, target.getAbsolutePath());
        int                 count;
        final URL           url        = new URL(urlToDownload);
        final URLConnection connection = url.openConnection();
        connection.connect();

        final long lengthOfFile = connection.getContentLengthLong();
        log.info("file {} size : {}", urlToDownload, lengthOfFile);
        final long steps = (long) (lengthOfFile / 10000);

        try (final InputStream input = new BufferedInputStream(url.openStream(), 8192)) {

            if (target.exists()) {
                log.warn("delete existing file {}", target.getAbsolutePath());
                target.delete();
            }
            if (!target.getParentFile().exists()) {
                log.info("create folder {}", target.getParentFile().getAbsolutePath());
                target.getParentFile().mkdirs();
            }

            final int bufferSize = 1024;
            // 100Ko
            try (final OutputStream output = new FileOutputStream(target.getAbsolutePath())) {
                final byte[] data   = new byte[bufferSize];
                long         cursor = 0;
                long         total  = 0;
                long         i      = 0;

                while ((count = input.read(data)) != -1) {
                    i++;
                    cursor += bufferSize;
                    total += count;

                    final long left = (lengthOfFile - total);
                    if (cursor >= steps) {
                        publishProgress(total, lengthOfFile, i, urlToDownload);
                        output.flush();
                        cursor = 0;
                    }

                    output.write(data, 0, count);
                }

                output.flush();
            }
        }


    }

    private void publishProgress(final long downloaded, final long totalSize, final long index, final String urlToDownload) {
        final BigDecimal download = BigDecimal.valueOf(downloaded);
        final BigDecimal total    = BigDecimal.valueOf(totalSize);
        final boolean    step     = index % 10 == 0;

        if (totalSize == -1) {
            if (step) {
                log.info("{}: in progress ....", urlToDownload);
            } else {
                log.trace("{}: in progress ....", urlToDownload);
            }
        } else {
            final double diff = (((double) downloaded) / ((double) totalSize)) * 100;

            if (step) {
                log.info("{}: {} %", urlToDownload, DecimalFormat.getInstance().format(diff));
            } else {
                log.trace("{}: {} %", urlToDownload, DecimalFormat.getInstance().format(diff));
            }
        }
    }
}
