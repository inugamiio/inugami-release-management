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
package io.inugami.release.management.api.common.dto;

import io.inugami.api.tools.StringComparator;
import lombok.*;

import java.time.LocalDateTime;

@ToString(onlyExplicitlyIncluded = true)
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
public class DependencyRuleDTO implements Comparable<DependencyRuleDTO> {
    @ToString.Include
    @EqualsAndHashCode.Include
    private String          groupId;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String          artifactId;
    @ToString.Include
    @EqualsAndHashCode.Include
    private VersionRulesDTO rules;
    private String          comment;
    private String          link;
    private Level           level;

    private LocalDateTime datePublished;
    private LocalDateTime dateUpdated;
    private String        state;
    private String        title;
    private Double        score;
    private String        descriptions;
    private String        problemTypes;
    private String        source;
    private String        credits;
    private String        impacts;
    private String        solutions;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String        cveId;

    @Override
    public int compareTo(final DependencyRuleDTO other) {
        return StringComparator.compareTo(toString(), other == null ? null : other.toString());
    }
}
