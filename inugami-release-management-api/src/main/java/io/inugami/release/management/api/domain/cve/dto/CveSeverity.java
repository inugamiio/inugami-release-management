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
package io.inugami.release.management.api.domain.cve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CveSeverity {
    CRITICAL("critical", 10),
    HIGH("high", 9),
    MODERATE("moderate", 5),
    LOW("low", 1);

    private static final CveSeverity[] VALUES = values();
    private final        String        label;
    private final        int           level;

    public static CveSeverity of(final String value) {
        CveSeverity result = null;
        if (value == null) {
            return null;
        }

        for (final CveSeverity item : VALUES) {
            if (value.equalsIgnoreCase(item.label)) {
                result = item;
                break;
            }
        }
        return result;
    }
}
