package io.inugami.release.management.infrastructure.datasource.neo4j.entity;

import io.inugami.commons.test.dto.AssertDtoContext;
import io.inugami.release.management.infrastructure.domain.version.VersionDataset;
import org.junit.jupiter.api.Test;

import static io.inugami.commons.test.UnitTestHelper.assertDto;
import static io.inugami.release.management.api.common.Constants.XXX;
import static org.assertj.core.api.Assertions.assertThat;

class VersionEntityTest {

    @Test
    void versionEntity() {
        assertDto(new AssertDtoContext<VersionEntity>()
                          .toBuilder()
                          .objectClass(VersionEntity.class)
                          .fullArgConstructorRefPath("infrastructure/datasource/neo4j/entity/VersionEntityTest/fullArgConstructorRefPath.json")
                          .getterRefPath("infrastructure/datasource/neo4j/entity/VersionEntityTest/getterRefPath.json")
                          .toStringRefPath("infrastructure/datasource/neo4j/entity/VersionEntityTest/toStringRefPath.txt")
                          .cloneFunction(instance -> instance.toBuilder().build())
                          .noArgConstructor(() -> new VersionEntity())
                          .fullArgConstructor(VersionDataset::versionEntity)
                          .noEqualsFunction(VersionEntityTest::notEquals)
                          .checkSetters(true)
                          .build());
    }


    static void notEquals(final VersionEntity value) {
        assertThat(value).isNotEqualTo(value.toBuilder());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().groupId(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().groupId(XXX).build().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().artifactId(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().artifactId(XXX).build().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().version(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().version(XXX).build().hashCode());

        //
        assertThat(value).isNotEqualTo(value.toBuilder().packaging(XXX).build());
        assertThat(value.hashCode()).isNotEqualTo(value.toBuilder().packaging(XXX).build().hashCode());
    }

    @Test
    void buildName_nominal() {
        assertThat(VersionDataset.versionEntity()
                                 .toBuilder()
                                 .name(null)
                                 .build()
                                 .buildName()
                                 .getName())
                .isEqualTo("io.inugami.release.management:inugami-release-management-common:1.0.0-SNAPSHOT:jar");
    }
}