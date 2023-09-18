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
package io.inugami.release.management.infrastructure.datasource.neo4j;

import io.inugami.release.management.infrastructure.datasource.neo4j.mapper.*;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableNeo4jRepositories("io.inugami.release.management.infrastructure.datasource.neo4j")
@Configuration
public class DatasourceNeo4jConfiguration {


    @Bean
    public VersionEntityMapper versionEntityMapper() {
        return Mappers.getMapper(VersionEntityMapper.class);
    }

    @Bean
    public CveImportRunEntityMapper cveImportRunEntityMapper() {
        return Mappers.getMapper(CveImportRunEntityMapper.class);
    }

    @Bean
    public CveEntityMapper cveEntityMapper() {
        return Mappers.getMapper(CveEntityMapper.class);
    }

    @Bean
    public ProductAffectedEntityMapper productAffectedEntityMapper() {
        return Mappers.getMapper(ProductAffectedEntityMapper.class);
    }

    @Bean
    public RuleEntityMapper ruleEntityMapper() {
        return Mappers.getMapper(RuleEntityMapper.class);
    }

    @Bean
    public VersionRulesEntityMapper versionRulesEntityMapper() {
        return Mappers.getMapper(VersionRulesEntityMapper.class);
    }
}
