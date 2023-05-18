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
package io.inugami.release.management.infrastructure.datasource.neo4j.repository;

import io.inugami.release.management.infrastructure.datasource.neo4j.entity.VersionEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface VersionRepository extends Neo4jRepository<VersionEntity, Long> {

    Optional<VersionEntity> findByGroupIdAndArtifactIdAndVersionAndPackaging(final String groupId, final String artifactId, final String version, final String packaging);

    @Query("""
            MATCH (v:Version) where v.groupId=$groupId AND v.artifactId=$artifactId AND v.version=$version  AND v.packaging=$packaging
            OPTIONAL MATCH (d:Version)-[]->(v)
            RETURN d
            """)
    List<VersionEntity> searchVersionUsing(final String groupId, final String artifactId, final String version, final String packaging);
}
