package io.inugami.release.management.api.common.dto;

import io.inugami.commons.test.dto.AssertDtoContext;
import org.junit.jupiter.api.Test;
import static io.inugami.commons.test.UnitTestHelper.assertDto;
import static org.assertj.core.api.Assertions.assertThat;

class PageDTOTest {

    @Test
    void versionDTO() {
        assertDto(new AssertDtoContext<PageDTO>()
                          .toBuilder()
                          .objectClass(PageDTO.class)
                          .fullArgConstructorRefPath("api/common/dto/pageDTOTest/fullArgConstructorRefPath.json")
                          .getterRefPath("api/common/dto/pageDTOTest/getterRefPath.json")
                          .toStringRefPath("api/common/dto/pageDTOTest/toStringRefPath.txt")
                          .cloneFunction(instance -> instance.toBuilder().build())
                          .noArgConstructor(() -> new PageDTO())
                          .fullArgConstructor(PageDTOTest::buildDataSet)
                          .noEqualsFunction(PageDTOTest::notEquals)
                          .checkSetters(true)
                          .build());
    }


    static void notEquals(final PageDTO value) {
        assertThat(value).isNotEqualTo(value.toBuilder());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().page(2).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().page(2).build().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().pageSize(20).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().pageSize(20).build().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().order(PageDTO.Order.ASC).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().order(PageDTO.Order.ASC).build().hashCode());
    }

    public static PageDTO buildDataSet() {
        return PageDTO.builder()
                      .page(1)
                      .pageSize(15)
                      .order(PageDTO.Order.DESC)
                      .build();
    }
}