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
package io.inugami.release.management.api.domain.version.exception;

import io.inugami.api.exceptions.DefaultErrorCode;
import io.inugami.api.exceptions.ErrorCode;

import static io.inugami.api.exceptions.DefaultErrorCode.newBuilder;

public enum VersionError implements ErrorCode {
    // =================================================================================================================
    // 0 - GLOBAL
    // =================================================================================================================
    VERSION_UNDEFINED_ERROR(newBuilder()
                                    .errorCode("VERSION-0_0")
                                    .statusCode(400)
                                    .message("undefined error occurs")
                                    .errorTypeTechnical()
                                    .domain(VersionError.DOMAIN)),

    // =================================================================================================================
    // 1 - CREATE
    // =================================================================================================================
    CREATE_DATA_REQUIRED(newBuilder()
                                 .errorCode("VERSION-1_0")
                                 .statusCode(400)
                                 .message("version data is required")
                                 .errorTypeFunctional()
                                 .domain(VersionError.DOMAIN)),
    CREATE_GROUP_ID_REQUIRED(newBuilder()
                                     .errorCode("VERSION-1_1")
                                     .statusCode(400)
                                     .message("group id is required")
                                     .errorTypeFunctional()
                                     .domain(VersionError.DOMAIN)),

    CREATE_ARTIFACT_ID_REQUIRED(newBuilder()
                                        .errorCode("VERSION-1_2")
                                        .statusCode(400)
                                        .message("artifact id is required")
                                        .errorTypeFunctional()
                                        .domain(VersionError.DOMAIN)),

    CREATE_VERSION_REQUIRED(newBuilder()
                                    .errorCode("VERSION-1_3")
                                    .statusCode(400)
                                    .message("version is required")
                                    .errorTypeFunctional()
                                    .domain(VersionError.DOMAIN)),
    // =================================================================================================================
    // 2 - READ
    // =================================================================================================================
    READ_NOT_FOUND_VERSIONS(newBuilder()
                                    .errorCode("VERSION-2_0")
                                    .statusCode(404)
                                    .message("no data found")
                                    .errorTypeFunctional()
                                    .domain(VersionError.DOMAIN)),
    READ_GROUP_ID_REQUIRED(newBuilder()
                                     .errorCode("VERSION-2_1")
                                     .statusCode(400)
                                     .message("group id is required")
                                     .errorTypeFunctional()
                                     .domain(VersionError.DOMAIN)),

    READ_ARTIFACT_ID_REQUIRED(newBuilder()
                                        .errorCode("VERSION-2_2")
                                        .statusCode(400)
                                        .message("artifact id is required")
                                        .errorTypeFunctional()
                                        .domain(VersionError.DOMAIN)),

    READ_VERSION_REQUIRED(newBuilder()
                                    .errorCode("VERSION-2_3")
                                    .statusCode(400)
                                    .message("version is required")
                                    .errorTypeFunctional()
                                    .domain(VersionError.DOMAIN)),

    READ_VERSION_NOT_FOUND(newBuilder()
                                  .errorCode("VERSION-2_4")
                                  .statusCode(400)
                                  .message("version can't be found")
                                  .errorTypeFunctional()
                                  .domain(VersionError.DOMAIN)),

    READ_INVALID_ID(newBuilder()
                                   .errorCode("VERSION-2_5")
                                   .statusCode(400)
                                   .message("invalid id")
                                   .errorTypeFunctional()
                                   .domain(VersionError.DOMAIN)),

    READ_VERSION_NOT_FOUND_WITH_ID(newBuilder()
                                   .errorCode("VERSION-2_6")
                                   .statusCode(400)
                                   .message("version can't be found")
                                   .errorTypeFunctional()
                                   .domain(VersionError.DOMAIN)),
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
    private static final String DOMAIN = "version";

    private VersionError(final DefaultErrorCode.DefaultErrorCodeBuilder errorBuilder) {
        errorCode = errorBuilder.build();
    }

    private final ErrorCode errorCode;

    @Override
    public ErrorCode getCurrentErrorCode() {
        return errorCode;
    }
}
