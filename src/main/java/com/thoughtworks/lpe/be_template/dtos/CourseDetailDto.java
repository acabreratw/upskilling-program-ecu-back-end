package com.thoughtworks.lpe.be_template.dtos;

import lombok.*;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CourseDetailDto {
    private Integer id;
    private String title;
    private String description;
    private String image;
    private Set<ResourceDto> resources;
    private Boolean enrolled;
    private String status;
    private TrainerDto trainer;
}
