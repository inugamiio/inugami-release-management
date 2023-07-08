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

import io.inugami.commons.connectors.HttpBasicConnector;
import io.inugami.commons.connectors.IHttpBasicConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MitreConfiguration {

    @Bean
    public IHttpBasicConnector mitreImporterHttpConnector(@Value("${inugami.release.management.domain.cve.importer.mitre.download.timeout:120000}") final int timeout,
                                                          @Value("${inugami.release.management.domain.cve.importer.mitre.download.ttl:120000}") final int timeToLive,
                                                          @Value("${inugami.release.management.domain.cve.importer.mitre.download.max-connection:10}") final int maxConnections,
                                                          @Value("${inugami.release.management.domain.cve.importer.mitre.download.max-route:10}") final int maxPerRoute,
                                                          @Value("${inugami.release.management.domain.cve.importer.mitre.download.socket-timeout:120000}") final int socketTimeout) {
        return new HttpBasicConnector(timeout,
                                      timeToLive,
                                      maxConnections,
                                      maxPerRoute,
                                      socketTimeout);
    }
}
