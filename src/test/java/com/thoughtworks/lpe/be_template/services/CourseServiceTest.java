package com.thoughtworks.lpe.be_template.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourse;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourseId;
import com.thoughtworks.lpe.be_template.domains.User;
import com.thoughtworks.lpe.be_template.domains.enums.CourseStatus;
import com.thoughtworks.lpe.be_template.domains.enums.UserType;
import com.thoughtworks.lpe.be_template.dtos.CourseDetailDto;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.ResourceDto;
import com.thoughtworks.lpe.be_template.mappers.CourseMapper;
import com.thoughtworks.lpe.be_template.repositories.CourseRepository;
import com.thoughtworks.lpe.be_template.repositories.ResourceRepository;
import com.thoughtworks.lpe.be_template.repositories.TraineeUserCourseRepository;
import com.thoughtworks.lpe.be_template.repositories.UserRepository;
import com.thoughtworks.lpe.be_template.security.TokenDecoder;
import com.thoughtworks.lpe.be_template.util.TestData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TraineeUserCourseRepository traineeUserCourseRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenDecoder decoder;

    @Mock
    private ResourceRepository resourceRepository;

    @InjectMocks
    private final CourseService courseService = new CourseService();

    private final TestData testData = new TestData();

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
    public void shouldReturnAllCourses(){
        LocalDateTime dateTime = LocalDateTime.now();
        when(courseRepository.findAll()).thenReturn(mockFindAllCourses(dateTime));
        List<CourseDto>  courseDtoList = courseService.findAllCourses();
        assertThat(courseDtoList.size()).isEqualTo(9);
    }

    @Test
    public void shouldReturnAllOpenedCoursesGivenAUserEmailSecondPageAndTwoItemsAsLimit() {

        String userId = "aedkhdahw232";
        LocalDateTime dateTime = LocalDateTime.now();
        CourseDto expectedCourseDto7Th = CourseDto.builder().id(7).name("Course 7th").description("Description")
                .freeStartDate(dateTime).freeEndDate(dateTime).imageUrl("Image url").build();
        CourseDto expectedCourseDto8Th = CourseDto.builder().id(8).name("Course 8th").description("Description")
                .freeStartDate(dateTime).freeEndDate(dateTime).imageUrl("Image url").build();
        CourseDto expectedCourseDto9Th = CourseDto.builder().id(9).name("Course 9th").description("Description")
                .freeStartDate(dateTime).freeEndDate(dateTime).imageUrl("Image url").build();

        when(courseRepository.findAll()).thenReturn(mockFindAllCourses(dateTime));
        when(traineeUserCourseRepository.findAllByUserIdAndStatusIn(userId, Arrays.asList(CourseStatus.IN_PROGRESS, CourseStatus.PASSED)))
                .thenReturn(mockFindAllByStatusIn(userId));

        List<CourseDto> courseDtoList = courseService.findOpenedCourses(userId, 1, 3);

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
        when(traineeUserCourseRepository.findAllByUserIdAndStatusIn(userEmail, Arrays.asList(CourseStatus.IN_PROGRESS, CourseStatus.PASSED)))
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
                 this.buildTraineeUserCourse(1, CourseStatus.IN_PROGRESS),
                this.buildTraineeUserCourse(2, CourseStatus.PASSED),
                this.buildTraineeUserCourse(5, CourseStatus.IN_PROGRESS));
    }

    private TraineeUserCourse buildTraineeUserCourse(int courseId, CourseStatus status) {
        return TraineeUserCourse.builder()
                .status(status)
                .pKey(TraineeUserCourseId.builder()
                        .course(Course.builder().id(courseId).build())
                        .traineeUser(new User())
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
    public void shouldGetOpenCourseStatus() {
        LocalDateTime courseStartDate = LocalDateTime.now().plusDays(3);
        LocalDateTime courseEndDate = LocalDateTime.now().plusDays(33);
        String expectedStatus = CourseStatus.OPEN.name();

        String courseStatus = courseService.getCourseStatusByStartAndEndDate(courseStartDate, courseEndDate);

        assertThat(courseStatus)
                .isNotNull()
                .isNotBlank()
                .isEqualTo(expectedStatus);
    }

    @Test
    public void shouldGetInProgressCourseStatus() {
        LocalDateTime courseStartDate = LocalDateTime.now().minusDays(3);
        LocalDateTime courseEndDate = LocalDateTime.now().plusDays(7);
        String expectedStatus = CourseStatus.IN_PROGRESS.name();

        String courseStatus = courseService.getCourseStatusByStartAndEndDate(courseStartDate, courseEndDate);

        assertThat(courseStatus)
                .isNotNull()
                .isNotBlank()
                .isEqualTo(expectedStatus);
    }

    @Test
    public void shouldGetClosedCourseStatus() {
        LocalDateTime courseStartDate = LocalDateTime.now().minusDays(3);
        LocalDateTime courseEndDate = LocalDateTime.now().minusDays(1);
        String expectedStatus = CourseStatus.CLOSED.name();

        String courseStatus = courseService.getCourseStatusByStartAndEndDate(courseStartDate, courseEndDate);

        assertThat(courseStatus)
                .isNotNull()
                .isNotBlank()
                .isEqualTo(expectedStatus);
    }

    @Test
    public void shouldGetOpenCourseDetailsWithUserNotEnrolledAndNoResources() throws JsonProcessingException {
        final int COURSE_ID = 1;
        final String USER_TOKEN = "someUserToken";
        final LocalDateTime COURSE_START_DATE = LocalDateTime.now().plusDays(3);
        final LocalDateTime COURSE_END_DATE = LocalDateTime.now().plusDays(33);
        final String USER_ID = "1298428321231";

        Set<ResourceDto> resources = new HashSet<>();

        for (int i = 0; i < 4; i++) {
            resources.add(ResourceDto.builder()
                    .image("https://www.freeiconspng.com/uploads/video-play-icon-1.jpg")
                    .title("Video " + ( i + 1))
                    .content("Here we're going to see an introduction to the topic.")
                    .id((long)i)
                    .build());
        }

        CourseDetailDto expectedResponse = testData.getCourseDetailDTO();
        expectedResponse.setResources(resources);
        expectedResponse.setEnrolled(false);
        expectedResponse.setStatus(CourseStatus.OPEN.name());

        Course courseFound = testData.getCourseWithFakeData();
        courseFound.setId(COURSE_ID);
        courseFound.setFreeStartDate(COURSE_START_DATE);
        courseFound.setFreeEndDate(COURSE_END_DATE);

        User foundUser = testData.getUser(UserType.TRAINEE);

        when(courseRepository.findById(COURSE_ID)).thenReturn(Optional.of(courseFound));
        when(traineeUserCourseRepository.existsById(any(TraineeUserCourseId.class))).thenReturn(false);
        when(decoder.getTokenPayload(anyString())).thenReturn("fakePayload");
        when(decoder.getCustomPropertyFromToken(anyString(), eq("userId"))).thenReturn(USER_ID);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(foundUser));

        CourseDetailDto actualResponse = courseService.getCourseDetails(COURSE_ID, USER_TOKEN);

        assertThat(actualResponse)
                .isNotNull()
                .isEqualToComparingFieldByField(expectedResponse);

        verify(courseRepository).findById(COURSE_ID);
        verify(decoder).getTokenPayload(anyString());
        verify(decoder).getCustomPropertyFromToken(anyString(), eq("userId"));
        verify(userRepository).findById(anyString());
        verify(traineeUserCourseRepository).existsById(any(TraineeUserCourseId.class));
    }
}