package com.thoughtworks.lpe.be_template.integration;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.builders.CourseBuilder;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.CourseStatus;
import com.thoughtworks.lpe.be_template.dtos.UserCourseDto;
import com.thoughtworks.lpe.be_template.exceptions.LogicBusinessException;
import com.thoughtworks.lpe.be_template.repositories.CourseRepository;
import com.thoughtworks.lpe.be_template.repositories.UserCourseRepository;
import com.thoughtworks.lpe.be_template.services.CourseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class CourseServiceIT {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void shouldReturnAllOpenedCoursesGivenAUserEmailSecondPageAndTwoItemsAsLimit() throws Exception {

        String userEmail = "getabstract@mail.com";

        List<Course> courseDtoList = courseService.findOpenedCourses(userEmail, 1, 4);

        assertThat(courseDtoList.size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnAllOpenedCoursesGivenAUserEmailOfAUserWithoutSubscribedCourses() throws Exception {

        String userEmail = "user-without-courses@mail.com";

        List<Course> courseDtoList = courseService.findOpenedCourses(userEmail, 0, 10);

        assertThat(courseDtoList.size()).isEqualTo(9);
    }

    @Test
    public void shouldReturnCourseDetailsGivenCourseId() throws Exception {

        String expectedCourseName = "Course test first";

        Course course = courseService.findCourseById(1);

        assertThat(course.getName()).isEqualTo(expectedCourseName);
    }

    @Test
    public void shouldAnExceptionGivenInvalidCourseId() throws Exception {

        Integer invalidCourseId = -1;

        assertThatThrownBy(() -> courseService.findCourseById(invalidCourseId))
                .isInstanceOf(LogicBusinessException.class);
    }

    @Test
    public void shouldDeleteACourseGivenAOpenedCourseId() throws Exception {

        Integer courseId = 10;

        assertThat(courseRepository.findById(courseId).isPresent()).isTrue();

        courseService.deleteCourse(courseId);

        assertThat(courseRepository.findById(courseId).isPresent()).isFalse();
    }
}