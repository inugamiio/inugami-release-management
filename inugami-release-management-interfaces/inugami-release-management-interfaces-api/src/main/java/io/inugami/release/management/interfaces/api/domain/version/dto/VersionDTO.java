package io.inugami.release.management.interfaces.api.domain.version.dto;


import lombok.*;

import java.util.Set;

import static io.inugami.release.management.interfaces.api.common.Constants.DOUBLE_DOT;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Setter
@Getter
public class VersionDTO {

    private Long id;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String groupId;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String artifactId;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String version;
    @ToString.Include
    @EqualsAndHashCode.Include
    private String type;

    private String  hash;
    private Boolean currentProject;

    private Set<VersionDTO> dependencies;
    private Set<VersionDTO> testDependencies;
    private Set<VersionDTO> projectDependencies;

    public static class VersionDTOBuilder {
        public VersionDTOBuilder buildHash() {
            this.hash = String.format(DOUBLE_DOT, groupId, artifactId, version, type);
            return this;
        }
    }
}
