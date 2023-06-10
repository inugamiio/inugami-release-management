package io.inugami.release.management.api.domain.cve.dto;

import io.inugami.commons.test.dto.AssertDtoContext;
import io.inugami.release.management.api.common.dto.RuleDTO;
import io.inugami.release.management.api.common.dto.RuleType;
import io.inugami.release.management.api.common.dto.VersionRulesDTO;
import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static io.inugami.commons.test.UnitTestHelper.assertDto;
import static org.assertj.core.api.Assertions.assertThat;

class CveDTOTest {

    public static final String XXX = "XXX";

    @Test
    void cveDTO() {
        assertDto(new AssertDtoContext<CveDTO>()
                          .toBuilder()
                          .objectClass(CveDTO.class)
                          .fullArgConstructorRefPath("api/domain/cve/dto/cveDTO/fullArgConstructorRefPath.json")
                          .getterRefPath("api/domain/cve/dto/cveDTO/getterRefPath.json")
                          .toStringRefPath("api/domain/cve/dto/cveDTO/toStringRefPath.txt")
                          .cloneFunction(instance -> instance.toBuilder().build())
                          .noArgConstructor(() -> new CveDTO())
                          .fullArgConstructor(CveDTOTest::buildDataSet)
                          .noEqualsFunction(CveDTOTest::notEquals)
                          .checkSetters(true)
                          .build());
    }


    static void notEquals(final CveDTO value) {
        assertThat(value).isNotEqualTo(value.toBuilder());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().hashCode());


        assertThat(value).isNotEqualTo(value.toBuilder().uuid(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().uuid(XXX).build().hashCode());
    }

    public static CveDTO buildDataSet() {
        return CveDTO.builder()
                     .id(1L)
                     .uuid("CVE-2023-33246")
                     .name("CVE-2023-33246")
                     .datePublished(LocalDateTime.of(2023, 5, 24, 14, 45, 25))
                     .cveSeverity(CveSeverity.MODERATE)
                     .title("Apache RocketMQ: Possible remote code execution vulnerability when using the update configuration function")
                     .link("https://lists.apache.org/thread/1s8j2c8kogthtpv3060yddk03zq0pxyp")
                     .comment("""
                                      For RocketMQ versions 5.1.0 and below, under certain conditions, there is a risk of remote command execution.
                                                  
                                      Several components of RocketMQ, including NameServer, Broker, and Controller, are leaked on the extranet and lack permission verification, an attacker can exploit this vulnerability by using the update configuration function to execute commands as the system users that RocketMQ is running as. Additionally, an attacker can achieve the same effect by forging the RocketMQ protocol content.
                                                               
                                      To prevent these attacks, users are recommended to upgrade to version 5.1.1 or above for using RocketMQ 5.x or 4.9.6 or above for using RocketMQ 4.x.
                                      """)
                     .productsAffected(List.of(
                             ProductAffectedDTO.builder()
                                               .product("Apache RocketMQ")
                                               .vendor("Apache Software Foundation")
                                               .rules(List.of(
                                                       VersionRulesDTO.builder()
                                                                      .major(RuleDTO.builder()
                                                                                    .version(5)
                                                                                    .ruleType(RuleType.EQUALS)
                                                                                    .build())
                                                                      .minor(RuleDTO.builder()
                                                                                    .version(1)
                                                                                    .ruleType(RuleType.LESS)
                                                                                    .build())
                                                                      .patch(RuleDTO.builder()
                                                                                    .version(0)
                                                                                    .ruleType(RuleType.LESS_EQUALS)
                                                                                    .build())
                                                                      .build()
                                               ))
                                               .affectVersions(List.of(
                                                       VersionDTO.builder()
                                                                 .groupId("io.inugami")
                                                                 .artifactId("some-artifact")
                                                                 .version("1.0.0")
                                                                 .build()
                                               ))
                                               .build()
                     ))
                     .build();
    }
}