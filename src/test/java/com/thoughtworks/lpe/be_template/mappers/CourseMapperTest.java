package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.builders.CourseBuilder;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.builders.CourseDtoBuilder;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.thoughtworks.lpe.be_template.util.Constants.DATETIME_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseMapperTest {
    @Test
    public void shouldReturnCourseDtoFromGivenCourse() {
        LocalDateTime date = LocalDateTime.now();
        CourseDto courseDto = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        Course expectedCourse = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        Course course = CourseMapper.dtoToDomain(courseDto);

        assertThat(course).isEqualToComparingFieldByField(expectedCourse);
    }

    @Test
    public void shouldReturnCourseFromGivenResource() {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        CourseDto courseDto = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        CourseDto expectedCourseDto = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        Course course = CourseMapper.dtoToDomain(courseDto);
        course.setId(1);

        assertThat(courseDto).isEqualToComparingFieldByField(expectedCourseDto);
    }

    @Test
    public void shouldReturnCourseDtoFromGivenCourseDomain() {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        Course course = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();
        CourseDto expectedResource = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        CourseDto courseResource = CourseMapper.domainToDto(course);
        assertThat(courseResource).isEqualToComparingFieldByField(expectedResource);
    }

    @Test
    public void shouldReturnCourseFromGivenCourseDto() {
        LocalDateTime date = LocalDateTime.now();
        CourseDto courseDto = new CourseDto("Test course", "Description",
                BigDecimal.TEN, "url", date, date, 1);
        CourseDto expectedCourseDto = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        Course course = CourseMapper.dtoToDomain(courseDto);
        course.setId(1);
        assertThat(course).isEqualToComparingFieldByField(expectedCourseDto);
    }
}
