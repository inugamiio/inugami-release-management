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

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RuleType {
    EQUALS("="),
    LESS("<"),
    LESS_EQUALS("<="),
    HIGHER(">"),
    HIGHER_EQUALS(">=");

    private final String type;

    @JsonValue
    public String getType() {
        return type;
    }
}