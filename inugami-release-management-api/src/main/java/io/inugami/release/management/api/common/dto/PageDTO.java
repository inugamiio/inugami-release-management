package io.inugami.release.management.api.common.dto;

import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
@Getter
public class PageDTO {
    private int page;
    private int pageSize;
    private Order order;

    public static enum Order{
        DESC,
        ASC
    }
}
