package com.thoughtworks.lpe.be_template.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TrainerDto {
    private String id;
    private String name;
    private String title;
    private String description;
    private String image;
}
