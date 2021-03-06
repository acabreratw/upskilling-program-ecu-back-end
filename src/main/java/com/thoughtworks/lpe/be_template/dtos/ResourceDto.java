package com.thoughtworks.lpe.be_template.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ResourceDto {
    private Long id;
    private String title;
    private String content;
    private String image;
    private Enum type;
    private Integer time_demand;
}
