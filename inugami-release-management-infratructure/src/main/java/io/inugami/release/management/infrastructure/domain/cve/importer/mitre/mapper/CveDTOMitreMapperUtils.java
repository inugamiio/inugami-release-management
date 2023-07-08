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

import io.inugami.release.management.api.common.dto.RuleDTO;
import io.inugami.release.management.api.common.dto.RuleType;
import io.inugami.release.management.api.common.dto.VersionRulesDTO;
import io.inugami.release.management.api.domain.cve.dto.CveSeverity;
import io.inugami.release.management.api.domain.cve.dto.ProductAffectedDTO;
import io.inugami.release.management.infrastructure.domain.cve.importer.mitre.dto.*;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
class CveDTOMitreMapperUtils {
    private static final String MAVEN = "maven";


    static boolean isMavenCve(final CveContentDTO cveContent) {
        return cveContent != null
                && cveContent.getContainers() != null
                && cveContent.getContainers().getCna() != null
                && cveContent.getContainers().getCna().getAffected() != null
                && containMavenVersion(cveContent.getContainers().getCna().getAffected());
    }

    static boolean containMavenVersion(final List<CveAffectedDTO> affected) {
        for (final CveAffectedDTO affectedDTO : affected) {
            for (final CveVersionDTO version : Optional.ofNullable(affectedDTO.getVersions()).orElse(new ArrayList<>())) {
                if (MAVEN.equalsIgnoreCase(version.getVersionType())) {
                    return true;
                }
            }
        }
        return false;
    }

    static CveSeverity resolveSeverity(final CveContentDTO cveContent) {
        CveSeverity result = null;
        if (cveContent != null
                && cveContent.getContainers() != null
                && cveContent.getContainers().getCna() != null
                && cveContent.getContainers().getCna().getMetrics() != null) {
            result = resolveSeverity(cveContent.getContainers().getCna().getMetrics());
        }
        return result;
    }

    static CveSeverity resolveSeverity(final List<CveCneMetricDTO> metrics) {
        CveSeverity result = null;

        for (final CveCneMetricDTO metric : metrics) {
            CveSeverity item = null;
            if (metric.getOther() != null
                    && metric.getOther().getContent() != null
                    && metric.getOther().getContent().getText() != null) {
                item = CveSeverity.of(metric.getOther().getContent().getText());
            }
            if (item != null) {
                if (result == null || item.getLevel() > result.getLevel()) {
                    result = item;
                }
            }
        }
        return result;
    }


    static String resolveLink(final CveContentDTO cveContent) {
        List<CveReferencesDTO> refs = null;
        if (cveContent != null
                && cveContent.getContainers() != null
                && cveContent.getContainers().getCna() != null
                && cveContent.getContainers().getCna().getReferences() != null) {
            refs = cveContent.getContainers().getCna().getReferences();
        }

        if (refs != null) {
            for (final CveReferencesDTO ref : refs) {
                if (ref.getUrl() != null) {
                    return ref.getUrl();
                }
            }
        }
        return null;
    }


    static List<ProductAffectedDTO> resolveProductsAffected(final CveContentDTO cveContent) {
        if (cveContent == null
                || cveContent.getContainers() == null
                || cveContent.getContainers().getCna() == null
                || cveContent.getContainers().getCna().getAffected() == null) {
            return null;
        }
        final List<ProductAffectedDTO> result = new ArrayList<>();
        for (final CveAffectedDTO cveAffectedDTO : cveContent.getContainers().getCna().getAffected()) {
            result.add(resolveProductAffected(cveAffectedDTO));
        }
        return result;
    }

    private static ProductAffectedDTO resolveProductAffected(final CveAffectedDTO cveAffectedDTO) {
        final List<VersionRulesDTO> rules = resolveRules(cveAffectedDTO.getVersions());
        return ProductAffectedDTO.builder()
                                 .product(cveAffectedDTO.getProduct())
                                 .vendor(cveAffectedDTO.getVendor())
                                 .rules(rules)
                                 .build();
    }

    private static List<VersionRulesDTO> resolveRules(final List<CveVersionDTO> versions) {
        if (versions == null) {
            return null;
        }

        final List<VersionRulesDTO> result = new ArrayList<>();

        return versions.stream()
                       .map(CveDTOMitreMapperUtils::resolveRule)
                       .filter(Objects::nonNull)
                       .toList();
    }

    private static VersionRulesDTO resolveRule(final CveVersionDTO version) {

        String        currentVersion = version.getLessThanOrEqual();
        final boolean lessOrEquals   = currentVersion != null;

        if (!lessOrEquals) {
            currentVersion = version.getLessThan();
        }
        if (currentVersion == null) {
            return null;
        }

        final RuleType ruleType = lessOrEquals ? RuleType.LESS_EQUALS : RuleType.LESS;
        final String[] parts    = currentVersion.split("[.]");
        final Integer  major    = convertToInteger(parts[0]);
        final Integer  minor    = convertToInteger(parts.length > 1 ? parts[1] : null);
        final Integer  patch    = convertToInteger(parts.length > 2 ? parts[2] : null);

        return VersionRulesDTO.builder()
                              .major(buildRule(major, RuleType.EQUALS))
                              .minor(buildRule(minor, ruleType))
                              .patch(buildRule(minor, ruleType))
                              .build();
    }


    private static Integer convertToInteger(final String value) {
        try {
            return Integer.parseInt(value);
        } catch (final Exception e) {
            return null;
        }
    }

    private static RuleDTO buildRule(final Integer value, final RuleType ruleType) {
        if (value == null) {
            return null;
        }

        return RuleDTO.builder()
                      .version(value)
                      .ruleType(ruleType)
                      .build();
    }
}

