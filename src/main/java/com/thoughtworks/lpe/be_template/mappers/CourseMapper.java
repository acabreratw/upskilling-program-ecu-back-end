package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.controllers.resources.CourseResource;
import com.thoughtworks.lpe.be_template.controllers.resources.builders.CourseResourceBuilder;
import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.builders.CourseBuilder;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import org.springframework.stereotype.Component;
import com.thoughtworks.lpe.be_template.dtos.builders.CourseDtoBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.thoughtworks.lpe.be_template.util.Constants.DATETIME_FORMAT;

@Component
public class CourseMapper {
    public CourseDto domainToDto(Course course) {
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

    public Course resourceToDomain(CourseResource resource) {
        return new CourseBuilder()
                .withDescription(resource.getDescription())
                .withFreeEndDate(LocalDateTime.parse(resource.getFreeEndDate(),DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(resource.getFreeStartDate(), DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl(resource.getImageUrl())
                .withPrice(resource.getPrice())
                .withName(resource.getName())
                .withId(resource.getId())
                .build();
    }

    public static CourseResource domainToResource(Course course) {
        return new CourseResourceBuilder()
                .withDescription(course.getDescription())
                .withFreeEndDate(formatterDate(course.getFreeEndDate()))
                .withFreeStartDate(formatterDate(course.getFreeStartDate()))
                .withImageUrl(course.getImageUrl())
                .withPrice(course.getPrice())
                .withName(course.getName())
                .withId(course.getId())
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
