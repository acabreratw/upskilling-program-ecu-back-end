package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.controllers.resources.CourseResource;
import com.thoughtworks.lpe.be_template.controllers.resources.builders.CourseResourceBuilder;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.builders.CourseDtoBuilder;
import com.thoughtworks.lpe.be_template.domains.Course;
import org.springframework.stereotype.Component;
import com.thoughtworks.lpe.be_template.domains.builders.CourseBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.thoughtworks.lpe.be_template.util.Constants.DATETIME_FORMAT;

@Component
public class CourseMapper {
    public Course domainToDto(CourseDto courseDto) {
        return new CourseBuilder()
                .withDescription(courseDto.getDescription())
                .withFreeEndDate(courseDto.getFreeEndDate())
                .withFreeStartDate(courseDto.getFreeStartDate())
                .withId(courseDto.getId())
                .withImageUrl(courseDto.getImageUrl())
                .withName(courseDto.getName())
                .withPrice(courseDto.getPrice())
                .build();
    }

    public CourseDto resourceToDomain(CourseResource resource) {
        return new CourseDtoBuilder()
                .withDescription(resource.getDescription())
                .withFreeEndDate(LocalDateTime.parse(resource.getFreeEndDate(),DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(resource.getFreeStartDate(), DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl(resource.getImageUrl())
                .withPrice(resource.getPrice())
                .withName(resource.getName())
                .withId(resource.getId())
                .build();
    }

    public static CourseResource domainToResource(CourseDto courseDto) {
        return new CourseResourceBuilder()
                .withDescription(courseDto.getDescription())
                .withFreeEndDate(formatterDate(courseDto.getFreeEndDate()))
                .withFreeStartDate(formatterDate(courseDto.getFreeStartDate()))
                .withImageUrl(courseDto.getImageUrl())
                .withPrice(courseDto.getPrice())
                .withName(courseDto.getName())
                .withId(courseDto.getId())
                .build();
    }

    public static CourseDto dtoToDomain(Course course){
        return new CourseDtoBuilder().withName(course.getName())
                .withId(course.getId())
                .withDescription(course.getDescription())
                .withImageUrl(course.getImageUrl())
                .withFreeStartDate(course.getFreeStartDate())
                .withFreeEndDate(course.getFreeEndDate())
                .withPrice(course.getPrice()).build();
    }

    private static String formatterDate(LocalDateTime availability){
        return availability.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }
}
