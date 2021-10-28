package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.thoughtworks.lpe.be_template.util.Constants.DATETIME_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseMapperTest {
    @Test
    public void shouldReturnCourseDtoFromGivenCourse() {
        LocalDateTime date = LocalDateTime.now();
        CourseDto courseDto = CourseDto.builder().description("Description")
                .freeEndDate(date)
                .freeStartDate(date)
                .imageUrl("url")
                .name("Test course")
                .id(1)
                .build();

        Course expectedCourse = Course.builder().description("Description")
                .freeEndDate(date)
                .freeStartDate(date)
                .imageUrl("url")
                .name("Test course")
                .id(1)
                .build();

        Course course = CourseMapper.dtoToDomain(courseDto);

        assertThat(course).isEqualToComparingFieldByField(expectedCourse);
    }

    @Test
    public void shouldReturnCourseFromGivenResource() {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        CourseDto courseDto = CourseDto.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .freeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .imageUrl("url")
                .name("Test course")
                .id(1)
                .build();

        CourseDto expectedCourseDto = CourseDto.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .freeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .imageUrl("url")
                .name("Test course")
                .id(1)
                .build();

        Course course = CourseMapper.dtoToDomain(courseDto);
        course.setId(1);

        assertThat(courseDto).isEqualToComparingFieldByField(expectedCourseDto);
    }

    @Test
    public void shouldReturnCourseDtoFromGivenCourseDomain() {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        Course course = Course.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .freeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .imageUrl("url")
                .name("Test course")
                .id(1)
                .build();
        CourseDto expectedResource = CourseDto.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .freeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .imageUrl("url")
                .name("Test course")
                .id(1)
                .build();

        CourseDto courseResource = CourseMapper.domainToDto(course);
        assertThat(courseResource).isEqualToComparingFieldByField(expectedResource);
    }

    @Test
    public void shouldReturnCourseFromGivenCourseDto() {
        LocalDateTime date = LocalDateTime.now();
        CourseDto courseDto = new CourseDto(1,"Test course", "Description",
                "url", date, date);
        Course expectedCourse = Course.builder().id(1)
                .description("Description")
                .freeEndDate(date)
                .freeStartDate(date)
                .imageUrl("url")
                .name("Test course")
                .trainer(null)
                .build();

        Course course = CourseMapper.dtoToDomain(courseDto);
        course.setId(1);
        assertThat(course).isEqualToComparingFieldByField(expectedCourse);
    }
}
