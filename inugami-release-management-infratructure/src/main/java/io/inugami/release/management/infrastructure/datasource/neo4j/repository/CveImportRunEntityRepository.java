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

import io.inugami.release.management.infrastructure.datasource.neo4j.entity.CveImportRunEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface CveImportRunEntityRepository extends Neo4jRepository<CveImportRunEntity, Long> {

    @Query("""
            MATCH (n:CveImportRun) where n.status="IN_PROGRESS"
            RETURN n
            """)
    Optional<CveImportRunEntity> findRunning();

    Optional<CveImportRunEntity> findByUid(String processUid);
}
