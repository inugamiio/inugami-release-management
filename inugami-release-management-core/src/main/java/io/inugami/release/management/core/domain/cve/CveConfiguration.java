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
package io.inugami.release.management.core.domain.cve;

import io.inugami.commons.threads.ThreadsExecutorService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class CveConfiguration {
    private static final Map<String, ThreadsExecutorService> THREADS_POOLS = new ConcurrentHashMap<>();

    @PreDestroy
    public void shutdown() {
        THREADS_POOLS.entrySet()
                     .stream()
                     .map(Map.Entry::getValue)
                     .forEach(ThreadsExecutorService::shutdown);
    }

    @Bean
    public ThreadsExecutorService cveServiceExecutorService(@Value("inugami.release.management.domain.cve.importer.maxThreads:20") final int maxThread,
                                                            @Value("inugami.release.management.domain.cve.importer.timeout:30000") final long timeout) {
        final ThreadsExecutorService result = new ThreadsExecutorService("cveServiceExecutorService",
                                                                         maxThread,
                                                                         true,
                                                                         timeout
        );
        THREADS_POOLS.put("cveServiceExecutorService", result);
        return result;
    }


    @Bean
    public ThreadsExecutorService mitreImporterExecutorService(@Value("inugami.release.management.domain.cve.importer.mitre.maxThreads:20") final int maxThread,
                                                               @Value("inugami.release.management.domain.cve.importer.mitre.timeout:30000") final long timeout) {
        final ThreadsExecutorService result = new ThreadsExecutorService("cveServiceExecutorService",
                                                                         maxThread,
                                                                         true,
                                                                         timeout
        );
        THREADS_POOLS.put("mitreImporterExecutorService", result);
        return result;
    }


}
