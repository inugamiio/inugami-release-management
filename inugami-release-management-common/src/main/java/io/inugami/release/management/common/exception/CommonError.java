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
package io.inugami.release.management.common.exception;

import io.inugami.api.exceptions.DefaultErrorCode;
import io.inugami.api.exceptions.ErrorCode;

import static io.inugami.api.exceptions.DefaultErrorCode.newBuilder;

public enum CommonError implements ErrorCode {
    // =================================================================================================================
    // 0 - GLOBAL
    // =================================================================================================================
    UNZIP_FILE_REQUIRED(newBuilder()
                                .errorCode("COMMON-0_0")
                                .statusCode(500)
                                .message("Zip file required")
                                .errorTypeTechnical()
                                .domain(CommonError.DOMAIN)),
    UNZIP_FILE_NOT_EXISTS(newBuilder()
                                  .errorCode("COMMON-0_1")
                                  .statusCode(500)
                                  .message("Zip file doesn't exists")
                                  .errorTypeTechnical()
                                  .domain(CommonError.DOMAIN)),

    UNZIP_TARGET_REQUIRED(newBuilder()
                                  .errorCode("COMMON-0_2")
                                  .statusCode(500)
                                  .message("target file required")
                                  .errorTypeTechnical()
                                  .domain(CommonError.DOMAIN)),
    ;


    // =================================================================================================================
    // IMPL
    // =================================================================================================================
    private static final String DOMAIN = "common";

    private CommonError(final DefaultErrorCode.DefaultErrorCodeBuilder errorBuilder) {
        errorCode = errorBuilder.build();
    }

    private final ErrorCode errorCode;

    @Override
    public ErrorCode getCurrentErrorCode() {
        return errorCode;
    }
}
