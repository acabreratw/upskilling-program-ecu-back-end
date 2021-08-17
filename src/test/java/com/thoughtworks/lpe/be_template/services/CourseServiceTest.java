package com.thoughtworks.lpe.be_template.services;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourse;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourseId;
import com.thoughtworks.lpe.be_template.domains.User;
import com.thoughtworks.lpe.be_template.domains.enums.CourseStatus;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.mappers.CourseMapper;
import com.thoughtworks.lpe.be_template.repositories.CourseRepository;
import com.thoughtworks.lpe.be_template.repositories.TraineeUserCourseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
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
    private TraineeUserCourseRepository userCourseRepository;

    @InjectMocks
    private final CourseService courseService = new CourseService();

    @Test
    public void shouldSaveGivenCourse() {
        LocalDateTime date = LocalDateTime.now();
        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        CourseDto courseDto = CourseDto.builder().description("Description")
                .freeEndDate(date)
                .freeStartDate(date)
                .imageUrl("url")
                .name("Test course")
                .build();

        Course expectedCourse = new Course(1,"Test course", "Description",
                "url", date, date);

        courseService.saveCourse(courseDto);

        verify(courseRepository).save(captor.capture());

        Course savedCourse = captor.getValue();
        savedCourse.setId(1);

        assertThat(savedCourse).isEqualToComparingFieldByField(expectedCourse);
    }

    @Test
    public void shouldReturnAllOpenedCoursesGivenAUserEmailSecondPageAndTwoItemsAsLimit() {

        String userEmail = "getabstract@mail.com";
        LocalDateTime dateTime = LocalDateTime.now();
        CourseDto expectedCourseDto7Th = CourseDto.builder().id(7).name("Course 7th").description("Description")
                .freeStartDate(dateTime).freeEndDate(dateTime).imageUrl("Image url").build();
        CourseDto expectedCourseDto8Th = CourseDto.builder().id(8).name("Course 8th").description("Description")
                .freeStartDate(dateTime).freeEndDate(dateTime).imageUrl("Image url").build();
        CourseDto expectedCourseDto9Th = CourseDto.builder().id(9).name("Course 9th").description("Description")
                .freeStartDate(dateTime).freeEndDate(dateTime).imageUrl("Image url").build();

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

        CourseDto courseToUpdate = CourseDto.builder().description("Description")
                .freeEndDate(date)
                .freeStartDate(date)
                .imageUrl("url")
                .name("Test course")
                .id(1)
                .build();

        Course updateCourse = CourseMapper.dtoToDomain(CourseDto.builder().description("Description")
                .freeEndDate(date)
                .freeStartDate(date)
                .imageUrl("url")
                .name("Test course")
                .id(1)
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

        CourseDto updateCourse = CourseDto.builder().description("Description")
                .freeEndDate(date)
                .freeStartDate(date)
                .imageUrl("url")
                .name("Test course")
                .id(1)
                .build();

        when(courseRepository.findById(1)).thenReturn(Optional.empty());

        courseService.updateCourse(updateCourse);
    }

    private List<Course> mockFindAllCourses(LocalDateTime dateTime){
        return Arrays.asList(new Course(1,"Course 1th", "Description",
                "Image url", dateTime, dateTime),
                new Course(2,"Course 2th", "Description",
                         "Image url", dateTime, dateTime),
                new Course(3,"Course 3th", "Description",
                        "Image url", dateTime, dateTime),
                new Course(4,"Course 4th", "Description",
                         "Image url", dateTime, dateTime),
                new Course(5,"Course 5th", "Description",
                        "Image url", dateTime, dateTime),
                new Course(6,"Course 6th", "Description",
                        "Image url", dateTime, dateTime),
                new Course(7,"Course 7th", "Description",
                        "Image url", dateTime, dateTime),
                new Course(8,"Course 8th", "Description",
                         "Image url", dateTime, dateTime),
                new Course(9,"Course 9th", "Description",
                        "Image url", dateTime, dateTime));
    }

    private List<TraineeUserCourse> mockFindAllByStatusIn(String userEmail){
        return Arrays.asList(
                 this.buildTraineeUserCourse(1, CourseStatus.PRO),
                this.buildTraineeUserCourse(2, CourseStatus.APR),
                this.buildTraineeUserCourse(5, CourseStatus.PRO));
    }

    private TraineeUserCourse buildTraineeUserCourse(int courseId, CourseStatus status) {
        return TraineeUserCourse.builder()
                .status(status)
                .traineeUserCoursePKey(TraineeUserCourseId.builder()
                        .course(Course.builder().id(courseId).build())
                        .user(new User())
                        .build())
                .build();
    }

    @Test
    public void shouldReturnTheCourseDetailsGivenAnCourseId(){
        LocalDateTime date = LocalDateTime.now();
        Course course = new Course(1,"Course 1th", "Description",
                "Image url", date, date);
        CourseDto expectedCourseDto = CourseDto.builder().description("Description")
                .freeEndDate(date)
                .freeStartDate(date)
                .imageUrl("Image url")
                .name("Course 1th")
                .id(1)
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