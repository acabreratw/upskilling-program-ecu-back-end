package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.thoughtworks.lpe.be_template.util.Constants.DATETIME_FORMAT;

@Component
public class CourseMapper {
    public static CourseDto domainToDto(Course course) {
        return CourseDto.builder().description(course.getDescription())
                .freeEndDate(course.getFreeEndDate())
                .freeStartDate(course.getFreeStartDate())
                .id(course.getId())
                .imageUrl(course.getImageUrl())
                .name(course.getName())
                .build();
    }

    public static Course dtoToDomain(CourseDto courseDto){
        return Course.builder().name(courseDto.getName())
                .id(courseDto.getId())
                .description(courseDto.getDescription())
                .imageUrl(courseDto.getImageUrl())
                .freeStartDate(courseDto.getFreeStartDate())
                .freeEndDate(courseDto.getFreeEndDate()).build();
    }

    private static String formatterDate(LocalDateTime availability){
        return availability.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }
}
