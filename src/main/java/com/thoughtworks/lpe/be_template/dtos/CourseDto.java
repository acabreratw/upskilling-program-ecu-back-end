package com.thoughtworks.lpe.be_template.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private int id;
    private String name;
    private String description;
    private String imageUrl;
    private LocalDateTime freeStartDate;
    private LocalDateTime freeEndDate;

}
