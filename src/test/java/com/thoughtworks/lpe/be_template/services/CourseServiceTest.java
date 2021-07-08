package com.thoughtworks.lpe.be_template.services;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.builders.CourseBuilder;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.CourseStatus;
import com.thoughtworks.lpe.be_template.dtos.UserCourseDto;
import com.thoughtworks.lpe.be_template.dtos.builders.CourseDtoBuilder;
import com.thoughtworks.lpe.be_template.repositories.CourseRepository;
import com.thoughtworks.lpe.be_template.repositories.UserCourseRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    private CourseRepository courseRepository;
    private UserCourseRepository userCourseRepository;

    private CourseService courseService;

    @Before
    public void setUp() {
        this.courseRepository = mock(CourseRepository.class);
        this.userCourseRepository = mock(UserCourseRepository.class);
        this.courseService = new CourseService(this.courseRepository, userCourseRepository);
    }

    @Test
    public void shouldSaveGivenCourse() {
        LocalDateTime date = LocalDateTime.now();
        ArgumentCaptor<CourseDto> captor = ArgumentCaptor.forClass(CourseDto.class);
        Course course = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .build();

        CourseDto expectedCourseDto = new CourseDto("Test course", "Description", BigDecimal.TEN,
                "url", date, date);

        courseService.saveCourse(course);

        verify(courseRepository).save(captor.capture());
        assertThat(captor.getValue()).isEqualToComparingFieldByField(expectedCourseDto);
    }

    @Test
    public void shouldReturnAllOpenedCoursesGivenAUserEmailSecondPageAndTwoItemsAsLimit() throws Exception {

        String userEmail = "getabstract@mail.com";
        LocalDateTime dateTime = LocalDateTime.now();
        Course expectedCourse7th = new CourseBuilder().withId(7).withName("Course 7th").withDescription("Description")
                .withFreeStartDate(dateTime).withFreeEndDate(dateTime).withImageUrl("Image url").withPrice(BigDecimal.TEN).build();
        Course expectedCourse8th = new CourseBuilder().withId(8).withName("Course 8th").withDescription("Description")
                .withFreeStartDate(dateTime).withFreeEndDate(dateTime).withImageUrl("Image url").withPrice(BigDecimal.TEN).build();
        Course expectedCourse9th = new CourseBuilder().withId(9).withName("Course 9th").withDescription("Description")
                .withFreeStartDate(dateTime).withFreeEndDate(dateTime).withImageUrl("Image url").withPrice(BigDecimal.TEN).build();

        when(courseRepository.findAll()).thenReturn(mockFindAllCourses(dateTime));
        when(userCourseRepository.findAllByUserEmailAndStatusIn(userEmail, Arrays.asList(CourseStatus.PRO, CourseStatus.APR)))
                .thenReturn(mockFindAllByStatusIn(userEmail));

        List<Course> courseList = courseService.findOpenedCourses(userEmail, 1, 3);

        assertThat(courseList.size()).isEqualTo(3);
        assertThat(courseList.get(0)).isEqualToComparingFieldByField(expectedCourse7th);
        assertThat(courseList.get(1)).isEqualToComparingFieldByField(expectedCourse8th);
        assertThat(courseList.get(2)).isEqualToComparingFieldByField(expectedCourse9th);
    }

    @Test
    public void shouldReturnAllOpenedCoursesGivenAUserEmailOfAUserWithoutSubscribedCourses() throws Exception {

        String userEmail = "user-without-courses@mail.com";
        LocalDateTime dateTime = LocalDateTime.now();

        when(courseRepository.findAll()).thenReturn(mockFindAllCourses(dateTime));
        when(userCourseRepository.findAllByUserEmailAndStatusIn(userEmail, Arrays.asList(CourseStatus.PRO, CourseStatus.APR)))
                .thenReturn(Arrays.asList());

        List<Course> courseList = courseService.findOpenedCourses(userEmail, 0, 10);

        assertThat(courseList.get(0).getId()).isEqualTo(1);
    }

    @Test
    public void shouldUpdateExistingCourseWhenISendNewDataWithAnId(){
        LocalDateTime date = LocalDateTime.now();

        Course expectedCourse = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        CourseDto courseToUpdate = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        Course updateCourse = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        when(courseRepository.findById(1)).thenReturn(Optional.of(courseToUpdate));

        courseService.updateCourse(updateCourse);

        verify(courseRepository).save(any(CourseDto.class));

    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldTrowEntityNotFoundExceptionWhenCourseNotExist() {
        LocalDateTime date = LocalDateTime.now();

        Course updateCourse = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        when(courseRepository.findById(1)).thenReturn(Optional.ofNullable(null));

        courseService.updateCourse(updateCourse);
    }

    private List<CourseDto> mockFindAllCourses(LocalDateTime dateTime){
        return Arrays.asList(new CourseDto(1,"Course 1th", "Description",
                BigDecimal.TEN, "Image url", dateTime, dateTime),
                new CourseDto(2,"Course 2th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new CourseDto(3,"Course 3th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new CourseDto(4,"Course 4th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new CourseDto(5,"Course 5th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new CourseDto(6,"Course 6th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new CourseDto(7,"Course 7th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new CourseDto(8,"Course 8th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new CourseDto(9,"Course 9th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime));
    }

    private List<UserCourseDto> mockFindAllByStatusIn(String userEmail){
        return Arrays.asList(
                new UserCourseDto(1,CourseStatus.PRO, userEmail),
                new UserCourseDto(2,CourseStatus.APR, userEmail),
                new UserCourseDto(5,CourseStatus.PRO, userEmail));
    }

    @Test
    public void shouldReturnTheCourseDetailsGivenAnCourseId(){
        LocalDateTime date = LocalDateTime.now();
        CourseDto courseDto = new CourseDto(1,"Course 1th", "Description",
                BigDecimal.TEN, "Image url", date, date);
        Course expectedCourse = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("Image url")
                .withName("Course 1th")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();
        when(courseRepository.findById(1)).thenReturn(Optional.of(courseDto));

        Course courseDetails = courseService.findCourseById(1);

        assertThat(courseDetails).isEqualToComparingFieldByField(expectedCourse);
    }

    @Test
    public void shouldDeleteACourseGivenAOpenedCourseId() {
        Integer courseId = 6;
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        courseService.deleteCourse(courseId);

        verify(userCourseRepository).deleteAllByCourseId(captor.capture());
        verify(courseRepository).deleteById(captor.capture());
        assertThat(captor.getAllValues()).contains(courseId);
    }

}