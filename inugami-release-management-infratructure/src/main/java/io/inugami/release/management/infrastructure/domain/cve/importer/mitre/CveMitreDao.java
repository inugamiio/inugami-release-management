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
import io.inugami.commons.files.FilesUtils;
import io.inugami.release.management.api.common.IFileService;
import io.inugami.release.management.api.domain.cve.dto.CveDTO;
import io.inugami.release.management.api.domain.cve.importer.ICveMitreDao;
import io.inugami.release.management.common.services.IDownloadService;
import io.inugami.release.management.common.services.IZipService;
import io.inugami.release.management.infrastructure.datasource.neo4j.entity.CveEntity;
import io.inugami.release.management.infrastructure.datasource.neo4j.mapper.CveEntityMapper;
import io.inugami.release.management.infrastructure.datasource.neo4j.repository.CveEntityRepository;
import io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto.CveContentDTO;
import io.inugami.release.management.infrastructure.domain.cve.importer.mitre.mapper.CveDTOMitreMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static io.inugami.release.management.api.domain.cve.exception.CveError.ERROR_UNZIP_MITRE_FILE;

@Slf4j
@Setter(AccessLevel.PACKAGE)
@Service
@RequiredArgsConstructor
public class CveMitreDao implements ICveMitreDao {


    // =================================================================================================================
    // ATTRIBUTES
    // =================================================================================================================
    public static final String              MITRE_CVE_ZIP          = "mitre-cve.zip";
    public static final String              DATA                   = "data";
    public static final String              RECENT_ACTIVITIES_JSON = "recent_activities.json";
    public static final String              JSON                   = ".json";
    private final       IDownloadService    downloadService;
    private final       IZipService         zipService;
    private final       IFileService        fileService;
    private final       CveDTOMitreMapper   cveDTOMitreMapper;
    private final       ObjectMapper        objectMapper;
    private final       CveEntityRepository cveEntityRepository;
    private final       CveEntityMapper     cveEntityMapper;

    @Value("${inugami.release.management.domain.cve.importer.mitre.folder.temp:#{null}}")
    private String folderTempPath;

    @Value("${inugami.release.management.domain.cve.importer.mitre.url:https://github.com/CVEProject/cvelistV5/archive/refs/heads/main.zip}")
    private String cveMitreUrl;

    private File folderTemp;

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
    @Transactional
    @Override
    public CveDTO save(final CveDTO cve) {
        final CveEntity entity = cveEntityMapper.convertToEntity(cve);
        return cveEntityMapper.convertToDto(cveEntityRepository.save(entity));
    }

    // =================================================================================================================
    // READ
    // =================================================================================================================
    @Transactional
    @Override
    public Optional<CveDTO> getById(final Long id) {
        return cveEntityRepository.findById(id)
                .map(cveEntityMapper::convertToDto);
    }
    @Override
    public boolean isCveZipFileExists() {
        return folderTemp.listFiles().length > 0;
    }

    @Override
    public File downloadCve() {
        final File file   = new File(folderTemp.getAbsolutePath() + File.separator + MITRE_CVE_ZIP);
        final File target = new File(folderTemp.getAbsolutePath() + File.separator + DATA);

        if (!isCveZipFileExists()) {
            if (file.exists()) {
                log.warn("file {} already exists, doanload skip", file.getAbsolutePath());
            } else {
                downloadService.download(cveMitreUrl, file);
            }
        }

        if (!mitreCveHaveBeenUnzipped()) {
            try {
                zipService.unzipFile(file, target);
            } catch (final IOException e) {
                throw new UncheckedException(ERROR_UNZIP_MITRE_FILE.addDetail(e.getMessage()), e);
            }
        }


        return target;
    }


    private boolean mitreCveHaveBeenUnzipped() {
        return !Arrays.asList(folderTemp.listFiles())
                      .stream()
                      .filter(file -> file.isDirectory()).toList()
                      .isEmpty();
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
                if (file.getName().toLowerCase().endsWith(JSON) && !file.getName().equalsIgnoreCase(RECENT_ACTIVITIES_JSON)) {
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
            log.error("enable to read mitre file : {} : {}", file.getAbsolutePath(), e.getMessage());
        }

        return data == null ? null : cveDTOMitreMapper.convertToCveDto(data);
    }

}
