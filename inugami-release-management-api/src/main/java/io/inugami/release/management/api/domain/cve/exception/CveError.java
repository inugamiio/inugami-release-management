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
package io.inugami.release.management.api.domain.cve.exception;

import io.inugami.api.exceptions.DefaultErrorCode;
import io.inugami.api.exceptions.ErrorCode;

import static io.inugami.api.exceptions.DefaultErrorCode.newBuilder;

@SuppressWarnings({"java:S1192"})
public enum CveError implements ErrorCode {
    // =================================================================================================================
    // 0 - GLOBAL
    // =================================================================================================================
    VERSION_UNDEFINED_ERROR(newBuilder()
                                    .errorCode("CVE-0_0")
                                    .statusCode(400)
                                    .message("undefined error occurs")
                                    .errorTypeTechnical()
                                    .domain(CveError.DOMAIN)),

    // =================================================================================================================
    // 1 - CREATE
    // =================================================================================================================

    // =================================================================================================================
    // 2 - READ
    // =================================================================================================================
    IMPORT_ALREADY_RUNNING(newBuilder()
                                   .errorCode("CVE-2_0")
                                   .statusCode(409)
                                   .message("CVE import already running")
                                   .errorTypeTechnical()
                                   .domain(CveError.DOMAIN)),
    // =================================================================================================================
    // 3 - UPDATE
    // =================================================================================================================

    // =================================================================================================================
    // 4 - DELETE
    // =================================================================================================================


    ;


    // =================================================================================================================
    // IMPL
    // =================================================================================================================
    private static final String DOMAIN = "cve";

    private CveError(final DefaultErrorCode.DefaultErrorCodeBuilder errorBuilder) {
        errorCode = errorBuilder.build();
    }

    private final ErrorCode errorCode;

    @Override
    public ErrorCode getCurrentErrorCode() {
        return errorCode;
    }
}