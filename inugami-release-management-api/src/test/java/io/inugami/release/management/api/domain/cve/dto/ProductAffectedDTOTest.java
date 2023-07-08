package io.inugami.release.management.api.domain.cve.dto;

import io.inugami.commons.test.dto.AssertDtoContext;
import io.inugami.release.management.api.common.dto.RuleDTO;
import io.inugami.release.management.api.common.dto.RuleType;
import io.inugami.release.management.api.common.dto.VersionRulesDTO;
import io.inugami.release.management.api.domain.version.dto.VersionDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.inugami.commons.test.UnitTestHelper.assertDto;
import static org.assertj.core.api.Assertions.assertThat;

class ProductAffectedDTOTest {

    public static final String XXX = "XXX";

    @Test
    void productAffectedDTO() {
        assertDto(new AssertDtoContext<ProductAffectedDTO>()
                          .toBuilder()
                          .objectClass(ProductAffectedDTO.class)
                          .fullArgConstructorRefPath("api/domain/cve/dto/productAffectedDTO/fullArgConstructorRefPath.json")
                          .getterRefPath("api/domain/cve/dto/productAffectedDTO/getterRefPath.json")
                          .toStringRefPath("api/domain/cve/dto/productAffectedDTO/toStringRefPath.txt")
                          .cloneFunction(instance -> instance.toBuilder().build())
                          .noArgConstructor(() -> new ProductAffectedDTO())
                          .fullArgConstructor(ProductAffectedDTOTest::buildDataSet)
                          .noEqualsFunction(ProductAffectedDTOTest::notEquals)
                          .checkSetters(true)
                          .build());
    }


    static void notEquals(final ProductAffectedDTO value) {
        assertThat(value).isNotEqualTo(value.toBuilder());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().product(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().product(XXX).build().hashCode());
        //
        assertThat(value).isNotEqualTo(value.toBuilder().vendor(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().vendor(XXX).build().hashCode());
        //
        assertThat(value).isNotEqualTo(value.toBuilder().rules(null).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().rules(null).build().hashCode());
        //
        assertThat(value).isNotEqualTo(value.toBuilder().rules(List.of()).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().rules(List.of()).build().hashCode());
        //
        assertThat(value).isNotEqualTo(value.toBuilder().rules(List.of()).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().rules(List.of()).build().hashCode());


        final List<VersionRulesDTO> otherRules = List.of(
                VersionRulesDTO.builder()
                               .major(RuleDTO.builder()
                                             .version(6)
                                             .ruleType(RuleType.EQUALS)
                                             .build())
                               .minor(RuleDTO.builder()
                                             .version(3)
                                             .ruleType(RuleType.LESS)
                                             .build())
                               .patch(RuleDTO.builder()
                                             .version(1)
                                             .ruleType(RuleType.LESS_EQUALS)
                                             .build())
                               .build()
        );
        //
        assertThat(value).isNotEqualTo(value.toBuilder().rules(otherRules).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().rules(otherRules).build().hashCode());
    }

    public static ProductAffectedDTO buildDataSet() {
        return ProductAffectedDTO.builder()
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
                                 .build();

    }
}