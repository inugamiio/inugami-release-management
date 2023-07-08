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
package io.inugami.release.management.infrastructure.domain.cve.importer.mitre.mapper;

import io.inugami.release.management.api.domain.cve.dto.CveDTO;
import io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto.CveContentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CveDTOMitreMapper {


    @Mapping(target = "uuid", source = "data.cveMetadata.cveId")
    @Mapping(target = "name", source = "data.cveMetadata.cveId")
    @Mapping(target = "cveSeverity", expression = "java(CveDTOMitreMapperUtils.resolveSeverity(data))")
    @Mapping(target = "title", source = "data.containers.cna.title")
    @Mapping(target = "link", expression = "java(CveDTOMitreMapperUtils.resolveLink(data))")
    @Mapping(target = "javaArtifact", expression = "java(CveDTOMitreMapperUtils.isMavenCve(data))")
    @Mapping(target = "datePublished", source = "data.cveMetadata.datePublished")
    @Mapping(target = "productsAffected", expression = "java(CveDTOMitreMapperUtils.resolveProductsAffected(data))")
    CveDTO convertToCveDto(CveContentDTO data);


}
