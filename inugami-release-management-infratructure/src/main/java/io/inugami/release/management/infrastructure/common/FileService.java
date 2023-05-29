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
package io.inugami.release.management.infrastructure.common;

import io.inugami.commons.files.FilesUtils;
import io.inugami.release.management.api.common.IFileService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
public class FileService implements IFileService {
    @Override
    public byte[] readFile(final File file) throws IOException {
        return FilesUtils.readBytes(file);
    }

    @Override
    public String readTextFile(final File file) throws IOException {
        return readTextFile(file, StandardCharsets.UTF_8);
    }

    @Override
    public String readTextFile(final File file, final Charset charset) throws IOException {
        final byte[] content = readFile(file);
        return content == null ? null : new String(content, charset == null ? StandardCharsets.UTF_8 : charset);
    }
}
