package com.thoughtworks.lpe.be_template.integration;

import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.exceptions.LogicBusinessException;
import com.thoughtworks.lpe.be_template.repositories.CourseRepository;
import com.thoughtworks.lpe.be_template.services.CourseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        List<CourseDto> courseDtoDtoList = courseService.findOpenedCourses(userEmail, 1, 4);

        assertThat(courseDtoDtoList.size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnAllOpenedCoursesGivenAUserEmailOfAUserWithoutSubscribedCourses() throws Exception {

        String userEmail = "user-without-courses@mail.com";

        List<CourseDto> courseDtoDtoList = courseService.findOpenedCourses(userEmail, 0, 10);

        assertThat(courseDtoDtoList.size()).isEqualTo(9);
    }

    @Test
    public void shouldReturnCourseDetailsGivenCourseId() throws Exception {

        String expectedCourseName = "Course test first";

        CourseDto courseDto = courseService.findCourseById(1);

        assertThat(courseDto.getName()).isEqualTo(expectedCourseName);
    }

    @Test
    public void shouldAnExceptionGivenInvalidCourseId() throws Exception {

        Integer invalidCourseId = -1;

        assertThatThrownBy(() -> courseService.findCourseById(invalidCourseId))
                .isInstanceOf(LogicBusinessException.class);
    }

}