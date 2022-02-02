package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.domains.Category;
import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.Trainer;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public static CourseDto domainToDto(Course course) {
        return CourseDto.builder().description(course.getDescription())
                .freeEndDate(course.getFreeEndDate())
                .freeStartDate(course.getFreeStartDate())
                .id(course.getId())
                .imageUrl(course.getImageUrl())
                .name(course.getName())
                .trainerName(course.getTrainer().getName())
                .categoryName(course.getCategory().getName())
                .build();
    }

    public static Course dtoToDomain(CourseDto courseDto){
        Trainer trainer = Trainer.builder()
                .name(courseDto.getTrainerName())
                .build();
        
        Category category = Category.builder()
                .name(courseDto.getCategoryName())
                .build();

        return Course.builder().name(courseDto.getName())
                .id(courseDto.getId())
                .description(courseDto.getDescription())
                .imageUrl(courseDto.getImageUrl())
                .freeStartDate(courseDto.getFreeStartDate())
                .freeEndDate(courseDto.getFreeEndDate())
                .trainer(trainer)
                .category(category)
                .build();
    }

}
