package com.thoughtworks.lpe.be_template.services;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.UserCourse;
import com.thoughtworks.lpe.be_template.domains.enums.CourseStatus;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.builders.CourseDtoBuilder;
import com.thoughtworks.lpe.be_template.mappers.CourseMapper;
import com.thoughtworks.lpe.be_template.repositories.CourseRepository;
import com.thoughtworks.lpe.be_template.repositories.UserCourseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserCourseRepository userCourseRepository;

    @InjectMocks
    private final CourseService courseService = new CourseServiceImpl();

    @Test
    public void shouldSaveGivenCourse() {
        LocalDateTime date = LocalDateTime.now();
        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        CourseDto courseDto = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .build();

        Course expectedCourse = new Course("Test course", "Description", BigDecimal.TEN,
                "url", date, date);

        courseService.saveCourse(courseDto);

        verify(courseRepository).save(captor.capture());
        assertThat(captor.getValue()).isEqualToComparingFieldByField(expectedCourse);
    }

    @Test
    public void shouldReturnAllOpenedCoursesGivenAUserEmailSecondPageAndTwoItemsAsLimit() {

        String userEmail = "getabstract@mail.com";
        LocalDateTime dateTime = LocalDateTime.now();
        CourseDto expectedCourseDto7Th = new CourseDtoBuilder().withId(7).withName("Course 7th").withDescription("Description")
                .withFreeStartDate(dateTime).withFreeEndDate(dateTime).withImageUrl("Image url").withPrice(BigDecimal.TEN).build();
        CourseDto expectedCourseDto8Th = new CourseDtoBuilder().withId(8).withName("Course 8th").withDescription("Description")
                .withFreeStartDate(dateTime).withFreeEndDate(dateTime).withImageUrl("Image url").withPrice(BigDecimal.TEN).build();
        CourseDto expectedCourseDto9Th = new CourseDtoBuilder().withId(9).withName("Course 9th").withDescription("Description")
                .withFreeStartDate(dateTime).withFreeEndDate(dateTime).withImageUrl("Image url").withPrice(BigDecimal.TEN).build();

        when(courseRepository.findAll()).thenReturn(mockFindAllCourses(dateTime));
        when(userCourseRepository.findAllByUserEmailAndStatusIn(userEmail, Arrays.asList(CourseStatus.PRO, CourseStatus.APR)))
                .thenReturn(mockFindAllByStatusIn(userEmail));

        List<CourseDto> courseDtoList = courseService.findOpenedCourses(userEmail, 1, 3);

        assertThat(courseDtoList.size()).isEqualTo(3);
        assertThat(courseDtoList.get(0)).isEqualToComparingFieldByField(expectedCourseDto7Th);
        assertThat(courseDtoList.get(1)).isEqualToComparingFieldByField(expectedCourseDto8Th);
        assertThat(courseDtoList.get(2)).isEqualToComparingFieldByField(expectedCourseDto9Th);
    }

    @Test
    public void shouldReturnAllOpenedCoursesGivenAUserEmailOfAUserWithoutSubscribedCourses() throws Exception {

        String userEmail = "user-without-courses@mail.com";
        LocalDateTime dateTime = LocalDateTime.now();

        when(courseRepository.findAll()).thenReturn(mockFindAllCourses(dateTime));
        when(userCourseRepository.findAllByUserEmailAndStatusIn(userEmail, Arrays.asList(CourseStatus.PRO, CourseStatus.APR)))
                .thenReturn(Collections.emptyList());

        List<CourseDto> courseDtoList = courseService.findOpenedCourses(userEmail, 0, 10);

        assertThat(courseDtoList.get(0).getId()).isEqualTo(1);
    }

    @Test
    public void shouldUpdateExistingCourseWhenISendNewDataWithAnId(){
        //Arrange
        LocalDateTime date = LocalDateTime.now();

        CourseDto courseToUpdate = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        Course updateCourse = CourseMapper.dtoToDomain(new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build());

        when(courseRepository.findById(1)).thenReturn(Optional.of(updateCourse));

        //Act
        courseService.updateCourse(courseToUpdate);

        //Assert/Verify
        verify(courseRepository).save(any(Course.class));
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldTrowEntityNotFoundExceptionWhenCourseNotExist() {
        LocalDateTime date = LocalDateTime.now();

        CourseDto updateCourse = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        courseService.updateCourse(updateCourse);
    }

    private List<Course> mockFindAllCourses(LocalDateTime dateTime){
        return Arrays.asList(new Course(1,"Course 1th", "Description",
                BigDecimal.TEN, "Image url", dateTime, dateTime),
                new Course(2,"Course 2th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new Course(3,"Course 3th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new Course(4,"Course 4th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new Course(5,"Course 5th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new Course(6,"Course 6th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new Course(7,"Course 7th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new Course(8,"Course 8th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime),
                new Course(9,"Course 9th", "Description",
                        BigDecimal.TEN, "Image url", dateTime, dateTime));
    }

    private List<UserCourse> mockFindAllByStatusIn(String userEmail){
        return Arrays.asList(
                new UserCourse(1,CourseStatus.PRO, userEmail),
                new UserCourse(2,CourseStatus.APR, userEmail),
                new UserCourse(5,CourseStatus.PRO, userEmail));
    }

    @Test
    public void shouldReturnTheCourseDetailsGivenAnCourseId(){
        LocalDateTime date = LocalDateTime.now();
        Course course = new Course(1,"Course 1th", "Description",
                BigDecimal.TEN, "Image url", date, date);
        CourseDto expectedCourseDto = new CourseDtoBuilder().withDescription("Description")
                .withFreeEndDate(date)
                .withFreeStartDate(date)
                .withImageUrl("Image url")
                .withName("Course 1th")
                .withPrice(BigDecimal.TEN)
                .withId(1)
                .build();
        when(courseRepository.findById(1)).thenReturn(Optional.of(course));

        CourseDto courseDtoDetails = courseService.findCourseById(1);

        assertThat(courseDtoDetails).isEqualToComparingFieldByField(expectedCourseDto);
    }

    @Test
    public void shouldDeleteACourseGivenAOpenedCourseId() {
        int courseId = 6;
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        courseService.deleteCourse(courseId);

        verify(userCourseRepository).deleteAllByCourseId(captor.capture());
        verify(courseRepository).deleteById(captor.capture());
        assertThat(captor.getAllValues()).contains(courseId);
    }

}