package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.builders.CourseBuilder;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.builders.CourseDtoBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.thoughtworks.lpe.be_template.util.Constants.DATETIME_FORMAT;

@Component
public class CourseMapper {
    public static CourseDto domainToDto(Course course) {
        return new CourseDtoBuilder()
                .withDescription(course.getDescription())
                .withFreeEndDate(course.getFreeEndDate())
                .withFreeStartDate(course.getFreeStartDate())
                .withId(course.getId())
                .withImageUrl(course.getImageUrl())
                .withName(course.getName())
                .withPrice(course.getPrice())
                .build();
    }

    public static Course dtoToDomain(CourseDto courseDto){
        return new CourseBuilder().withName(courseDto.getName())
                .withId(courseDto.getId())
                .withDescription(courseDto.getDescription())
                .withImageUrl(courseDto.getImageUrl())
                .withFreeStartDate(courseDto.getFreeStartDate())
                .withFreeEndDate(courseDto.getFreeEndDate())
                .withPrice(courseDto.getPrice()).build();
    }

    private static String formatterDate(LocalDateTime availability){
        return availability.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }
}
