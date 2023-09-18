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
package io.inugami.release.management.infrastructure;

import io.inugami.api.exceptions.UncheckedException;
import io.inugami.commons.test.UnitTestHelper;
import org.neo4j.configuration.GraphDatabaseSettings;
import org.neo4j.configuration.helpers.SocketAddress;
import org.neo4j.dbms.api.DatabaseManagementService;
import org.neo4j.dbms.api.DatabaseManagementServiceBuilder;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.UUID;

public class Neo4jInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String DEFAULT_DB = "neo4J";

    public static String initializationQuery() {
        return """
                CREATE INDEX idx_Artifact FOR (n:Artifact) ON (n.name,n.groupId,n.artifactId,n.packaging,n.shortName,n.version);
                CREATE INDEX idx_Version FOR (n:Version) ON (n.name, n.uid,n.artifactId,n.groupId,n.major,n.minor,n.packaging,n.patch,n.tag,n.version);
                CREATE INDEX idx_ErrorCode FOR (n:ErrorCode) ON (n.exploitationError,n.name,n.errorCode,n.message,n.shortName,n.statusCode, n.category);
                CREATE INDEX idx_ErrorType FOR (n:ErrorType) ON (n.name,n.shortName);
                CREATE INDEX idx_Service FOR (n:Service) ON (n.accept,n.autoStartup,n.bindings,n.containerFactory,n.contentType,n.converterWinsContentType,n.destination,n.errorHandler,n.event,n.exclusive,n.identifier,n.listenerId,n.method,n.name,n.payload,n.queue,n.queuesToDeclare,n.requestPayload,n.responsePayload,n.uid,n.uri,n.verb);
                CREATE INDEX idx_ServiceType FOR (n:ServiceServiceType) ON (n.name, n.uid);
                CREATE INDEX idx_Method FOR (n:Method) ON (n.name, n.uid,n.class,n.method,n.parameters,n.returnType);
                CREATE INDEX idx_Property FOR (n:Property) ON (n.name, n.uid,n.mandatory,n.propertyType );
                CREATE INDEX idx_INPUT_DTO FOR (n:INPUT_DTO) ON (n.name, n.uid);
                CREATE INDEX idx_OUTPUT_DTO FOR (n:OUTPUT_DTO) ON (n.name, n.uid);
                CREATE INDEX idx_LocalEntity FOR (n:LocalEntity) ON (n.name, n.uid,n.payload);
                CREATE INDEX idx_Entity FOR (n:Entity) ON (n.name, n.uid,n.payload);
                CREATE INDEX idx_Scm FOR (n:Scm) ON (n.name, n.uid,n.commit);
                CREATE INDEX idx_Author FOR (n:Author) ON (n.name, n.uid,n.email);
                CREATE INDEX idx_Glossary FOR (n:Glossary) ON (n.name, n.shortName,n.language,n.type);
                CREATE INDEX idx_Issue FOR (n:Issue) ON (n.name, n.labels,n.shortName,n.title,n.url);
                CREATE INDEX idx_IssueLabel FOR (n:IssueLabel) ON (n.name, n.shortName);
                """;
    }

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        Path databaseDirectory = null;
        try {
            databaseDirectory = UnitTestHelper.buildPath("..", "target", "neo4j", "data", UUID.randomUUID().toString()).getCanonicalFile().toPath();
        } catch (IOException e) {
            throw new UncheckedException(e);
        }
        final DatabaseManagementService managementService = new DatabaseManagementServiceBuilder(databaseDirectory)
                .setConfig(GraphDatabaseSettings.SERVER_DEFAULTS)
                .setConfig(GraphDatabaseSettings.auth_enabled, false)
                .build();
        final GraphDatabaseService graphDb = managementService.database(DEFAULT_DB);


        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                managementService.shutdown();
            }
        });
    }
}
