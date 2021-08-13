package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.controllers.resources.CourseResource;
import com.thoughtworks.lpe.be_template.controllers.resources.builders.CourseResourceBuilder;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.domains.builders.CourseBuilder;
import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.dtos.builders.CourseDtoBuilder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.thoughtworks.lpe.be_template.util.Constants.DATETIME_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;

public class CourseMapperTest {

    private CourseMapper courseMapper;

    @Before
    public void setUp() {
        this.courseMapper = new CourseMapper();
    }

    @Test
    public void shouldReturnCourseDtoFromGivenCourse() {
        LocalDateTime date = LocalDateTime.now();
        CourseDto courseDto = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        Course expectedCourse = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        Course course = courseMapper.domainToDto(courseDto);

        assertThat(course).isEqualToComparingFieldByField(expectedCourse);
    }

    @Test
    public void shouldReturnCourseFromGivenResource() {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        CourseResource resource = new CourseResourceBuilder().withDescription("Description")
                .withFreeEndDate(dateString)
                .withFreeStartDate(dateString)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        CourseDto expectedCourseDto = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        CourseDto courseDto = courseMapper.resourceToDomain(resource);

        assertThat(courseDto).isEqualToComparingFieldByField(expectedCourseDto);
    }

    @Test
    public void shouldReturnCourseResourceFromGivenCourseDomain() {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        CourseDto courseDto = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();
        CourseResource expectedResource = new CourseResourceBuilder().withDescription("Description")
                .withFreeEndDate(dateString)
                .withFreeStartDate(dateString)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        CourseResource courseResource = CourseMapper.domainToResource(courseDto);
        assertThat(courseResource).isEqualToComparingFieldByField(expectedResource);
    }

    @Test
    public void shouldReturnCourseFromGivenCourseDto() {
        LocalDateTime date = LocalDateTime.now();
        Course course = new Course(1,"Test course", "Description",
                BigDecimal.TEN, "url", date, date);
        CourseDto expectedCourseDto = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        CourseDto courseDto = CourseMapper.dtoToDomain(course);
        assertThat(courseDto).isEqualToComparingFieldByField(expectedCourseDto);
    }
}
