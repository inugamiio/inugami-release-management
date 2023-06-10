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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.inugami.api.exceptions.UncheckedException;
import io.inugami.commons.connectors.IHttpBasicConnector;
import io.inugami.commons.files.FilesUtils;
import io.inugami.release.management.api.common.IFileService;
import io.inugami.release.management.api.domain.cve.dto.CveDTO;
import io.inugami.release.management.api.domain.cve.exception.CveError;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto.CveContentDTO;
import io.inugami.release.management.infrastructure.domain.cve.importer.mitre.mapper.CveDTOMitreMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter(AccessLevel.PACKAGE)
@Service
@RequiredArgsConstructor
public class CveMitreDao implements ICveMitreDao {

    // =================================================================================================================
    // ATTRIBUTES
    // =================================================================================================================
    private final IHttpBasicConnector mitreImporterHttpConnector;
    private final IFileService        fileService;
    private final CveDTOMitreMapper   cveDTOMitreMapper;
    private final ObjectMapper        objectMapper;

    @Value("${inugami.release.management.domain.cve.importer.mitre.folder.temp:#{null}}")
    private String folderTempPath;
    private File   folderTemp;

    // =================================================================================================================
    // init
    // =================================================================================================================
    @PostConstruct
    public void init() {
        if (folderTempPath == null) {
            folderTempPath = FilesUtils.buildPath(new File(System.getProperty("user.home")), ".inugami", "inugami_cve_importer_mitre");
        }
        folderTemp = new File(folderTempPath);
        if (!folderTemp.exists()) {
            folderTemp.mkdirs();
        }
    }

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
        return folderTemp.listFiles().length > 0;
    }

    @Override
    public File downloadCve() {
        return null;
    }

    @Override
    public List<File> getAllFiles() {
        final List<File> result = new ArrayList<>();

        result.addAll(getAllFiles(folderTemp));
        Collections.sort(result);
        return result;
    }

    public List<File> getAllFiles(final File folder) {
        final List<File> result = new ArrayList<>();
        if (folder == null || !folder.isDirectory()) {
            return result;
        }

        for (final File file : folder.listFiles()) {
            if (file.isDirectory()) {
                result.addAll(getAllFiles(file));
            } else {
                if (file.getName().toLowerCase().endsWith(".json")) {
                    try {
                        result.add(file.getCanonicalFile());
                    } catch (final IOException e) {
                    }
                }
            }
        }
        return result;
    }

    @Override
    public CveDTO readCveFile(final File file) {
        CveContentDTO data = null;
        try {
            final String content = fileService.readTextFile(file);
            data = objectMapper.readValue(content, CveContentDTO.class);
        } catch (final Throwable e) {
            throw new UncheckedException(CveError.ERROR_READING_MITRE_FILE.addDetail(e.getMessage()), e);
        }

        return cveDTOMitreMapper.convertToCveDto(data);
    }

}
