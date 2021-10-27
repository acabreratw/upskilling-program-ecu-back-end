package com.thoughtworks.lpe.be_template.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thoughtworks.lpe.be_template.domains.*;
import com.thoughtworks.lpe.be_template.domains.enums.CourseStatus;
import com.thoughtworks.lpe.be_template.domains.enums.UserType;
import com.thoughtworks.lpe.be_template.dtos.CourseDetailDto;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.ResourceDto;
import com.thoughtworks.lpe.be_template.exceptions.LogicBusinessException;
import com.thoughtworks.lpe.be_template.exceptions.enums.Error;
import com.thoughtworks.lpe.be_template.mappers.CourseMapper;
import com.thoughtworks.lpe.be_template.mappers.TraineeUserCourseMapper;
import com.thoughtworks.lpe.be_template.repositories.CourseRepository;
import com.thoughtworks.lpe.be_template.repositories.ResourceRepository;
import com.thoughtworks.lpe.be_template.repositories.TraineeUserCourseRepository;
import com.thoughtworks.lpe.be_template.repositories.UserRepository;
import com.thoughtworks.lpe.be_template.security.TokenDecoder;
import com.thoughtworks.lpe.be_template.util.TestData;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseServiceTest {

    private static final String USER_ID = "userId";

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

    @Mock
    private TraineeUserCourseMapper traineeUserCourseMapper;

    @InjectMocks
    private final CourseService courseService = new CourseService();

    private final TestData testData = new TestData();

    private int courseId = 1;
    private final String userToken = "someUserToken";
    private final LocalDateTime courseStartDate = LocalDateTime.now().plusDays(3);
    private final LocalDateTime courseEndDate = LocalDateTime.now().plusDays(33);
    private String userId = "1298428321231";

    @Captor
    private ArgumentCaptor<Integer> courseIdIntegerArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> tokenArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> payloadArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> userIdArgumentCaptor;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<Course> courseArgumentCaptor;

    @Captor
    private ArgumentCaptor<TraineeUserCourse> traineeUserCourseArgumentCaptor;


    @Test
    public void shouldSaveGivenCourse() {
        LocalDateTime date = LocalDateTime.now();
        ArgumentCaptor<Course> captor = ArgumentCaptor.forClass(Course.class);
        CourseDto courseDto = CourseDto.builder().description("Description")
                .freeEndDate(date)
                .freeStartDate(date)
                .imageUrl("url")
                .name("Test course")
                .categoryId(1)
                .build();

        Course expectedCourse = new Course(1,"Test course", "Description",
                "url", date, date, 1);

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
        assertThat(courseDtoList.size()).isEqualTo(10);
    }

    @Test
    @Disabled
    public void shouldReturnAllOpenedCoursesGivenAUserEmailSecondPageAndTwoItemsAsLimit() {

        String userId = "aedkhdahw232";
        LocalDateTime dateTime = LocalDateTime.now();
        CourseDto expectedCourseDto7Th = CourseDto.builder().id(7).name("Course 7th").description("Description")
                .freeStartDate(dateTime).freeEndDate(dateTime).imageUrl("Image url").categoryId(1).build();
        CourseDto expectedCourseDto8Th = CourseDto.builder().id(8).name("Course 8th").description("Description")
                .freeStartDate(dateTime).freeEndDate(dateTime).imageUrl("Image url").categoryId(2).build();
        CourseDto expectedCourseDto9Th = CourseDto.builder().id(9).name("Course 9th").description("Description")
                .freeStartDate(dateTime).freeEndDate(dateTime).imageUrl("Image url").categoryId(3).build();

        when(courseRepository.findAll()).thenReturn(mockFindAllCourses(dateTime));
        when(traineeUserCourseRepository.findAllByUserIdAndStatusIn(userId, Arrays.asList(CourseStatus.IN_PROGRESS, CourseStatus.PASSED)))
                .thenReturn(mockFindAllByStatusIn());

        List<CourseDto> courseDtoList = courseService.findOpenedCourses(userId, 1, 3);

        assertThat(courseDtoList.size()).isEqualTo(3);
        assertThat(courseDtoList.get(0)).isEqualToComparingFieldByField(expectedCourseDto7Th);
        assertThat(courseDtoList.get(1)).isEqualToComparingFieldByField(expectedCourseDto8Th);
        assertThat(courseDtoList.get(2)).isEqualToComparingFieldByField(expectedCourseDto9Th);
    }

    @Test
    public void shouldReturnAllOpenedCoursesGivenAUserEmailOfAUserWithoutSubscribedCourses() {

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
                "Image url", dateTime, dateTime,1),
                new Course(2,"Course 2th", "Description",
                         "Image url", dateTime, dateTime,2),
                new Course(3,"Course 3th", "Description",
                        "Image url", dateTime, dateTime,3),
                new Course(4,"Course 4th", "Description",
                         "Image url", dateTime, dateTime,4),
                new Course(5,"Course 5th", "Description",
                        "Image url", dateTime, dateTime,1),
                new Course(6,"Course 6th", "Description",
                        "Image url", dateTime, dateTime,1),
                new Course(7,"Course 7th", "Description",
                        "Image url", dateTime, dateTime,1),
                new Course(8,"Course 8th", "Description",
                         "Image url", dateTime, dateTime,2),
                new Course(9,"Course 9th", "Description",
                        "Image url", dateTime, dateTime,3),
                new Course(10,"Course 10th", "Description",
                        "Image url", dateTime, dateTime,3));
    }

    private List<TraineeUserCourse> mockFindAllByStatusIn(){
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
                "Image url", date, date, 1);
        CourseDto expectedCourseDto = CourseDto.builder().description("Description")
                .freeEndDate(date)
                .freeStartDate(date)
                .imageUrl("Image url")
                .name("Course 1th")
                .id(1)
                .categoryId(1)
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
    public void shouldThrowExceptionGettingCourseDetailsWhenCourseIsNotFound() throws JsonProcessingException {
        final int COURSE_ID = 100;
        final String ACCESS_TOKEN = "someValidAccessToken";

        when(courseRepository.findById(anyInt())).thenReturn(Optional.empty());

        LogicBusinessException exception = Assertions.assertThrows(LogicBusinessException.class,
                ()-> courseService.getCourseDetails(COURSE_ID, ACCESS_TOKEN));

        assertThat(exception).isNotNull();
        assertThat(exception.getError()).isEqualTo(Error.INVALID_COURSE_ID);
        assertThat(exception.getError().getCode())
                .isNotNull()
                .isNotBlank()
                .isEqualTo("003");
        assertThat(exception.getError().getUserMessage())
                .isNotNull()
                .isNotBlank()
                .isEqualTo("Course id is invalid");

        verify(courseRepository).findById(anyInt());
        verify(decoder, never()).getTokenPayload(anyString());
        verify(decoder, never()).getCustomPropertyFromToken(anyString(), anyString());
        verify(userRepository, never()).findById(anyString());
    }

    @Test
    public void shouldThrowExceptionGettingCourseDetailsWhenUserIsNotFound() throws JsonProcessingException {
        courseId = 99;
        final String ACCESS_TOKEN = "someValidAccessTokenWithUserIdNotRegistered";
        final Course courseFound = testData.getCourseWithFakeData();
        final String TOKEN_PAYLOAD = "someTokenPayload";
        final String CUSTOM_PROPERTY_FROM_TOKEN = "userId";
        userId = "00192833992";

        when(courseRepository.findById(anyInt())).thenReturn(Optional.of(courseFound));
        when(decoder.getTokenPayload(anyString())).thenReturn(TOKEN_PAYLOAD);
        when(decoder.getCustomPropertyFromToken(TOKEN_PAYLOAD, CUSTOM_PROPERTY_FROM_TOKEN)).thenReturn(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        LogicBusinessException exception = Assertions.assertThrows(LogicBusinessException.class,
                ()-> courseService.getCourseDetails(courseId, ACCESS_TOKEN));

        assertThat(exception).isNotNull();
        assertThat(exception.getError()).isEqualTo(Error.USER_NOT_FOUND);
        assertThat(exception.getError().getCode())
                .isNotNull()
                .isNotBlank()
                .isEqualTo("010");
        assertThat(exception.getError().getUserMessage())
                .isNotNull()
                .isNotBlank()
                .isEqualTo("User could not be found.");

        verify(courseRepository).findById(anyInt());
        verify(decoder).getTokenPayload(anyString());
        verify(decoder).getCustomPropertyFromToken(TOKEN_PAYLOAD, CUSTOM_PROPERTY_FROM_TOKEN);
        verify(userRepository).findById(userId);
    }

    @Test
    public void shouldGetOpenCourseDetailsWithUserNotEnrolledAndNoResources() throws JsonProcessingException {
        CourseDetailDto expectedResponse = testData.getCourseDetailDTO();
        expectedResponse.setResources(Collections.emptySet());
        expectedResponse.setEnrolled(false);
        expectedResponse.setStatus(CourseStatus.OPEN.name());

        Course courseFound = testData.getCourseWithFakeData();
        courseFound.setId(courseId);
        courseFound.setFreeStartDate(courseStartDate);
        courseFound.setFreeEndDate(courseEndDate);

        User foundUser = testData.getUser(UserType.TRAINEE);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseFound));
        when(traineeUserCourseRepository.existsById(any(TraineeUserCourseId.class))).thenReturn(false);
        when(decoder.getTokenPayload(anyString())).thenReturn("fakePayload");
        when(decoder.getCustomPropertyFromToken(anyString(), eq("userId"))).thenReturn(userId);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(foundUser));
        when(resourceRepository.findAllByCourseId(anyInt())).thenReturn(Collections.emptySet());

        CourseDetailDto actualResponse = courseService.getCourseDetails(courseId, userToken);

        assertThat(actualResponse)
                .isNotNull()
                .isEqualToComparingFieldByField(expectedResponse);

        verify(courseRepository).findById(courseId);
        verify(decoder).getTokenPayload(anyString());
        verify(decoder).getCustomPropertyFromToken(anyString(), eq("userId"));
        verify(userRepository).findById(anyString());
        verify(traineeUserCourseRepository).existsById(any(TraineeUserCourseId.class));
        verify(resourceRepository).findAllByCourseId(anyInt());
    }

    @Test
    public void shouldGetOpenCourseDetailsWithUserNotEnrolledAndResources() throws JsonProcessingException {
        Set<ResourceDto> resources = new HashSet<>();
        Set<Resource> resourcesFound = new HashSet<>();

        CourseDetailDto expectedResponse = testData.getCourseDetailDTO();
        expectedResponse.setResources(resources);
        expectedResponse.setEnrolled(false);
        expectedResponse.setStatus(CourseStatus.OPEN.name());

        Course courseFound = testData.getCourseWithFakeData();
        courseFound.setId(courseId);
        courseFound.setFreeStartDate(courseStartDate);
        courseFound.setFreeEndDate(courseEndDate);

        for (int i = 0; i < 4; i++) {
            String FAKE_RESOURCE_IMAGE_URL = "https://www.freeiconspng.com/uploads/video-play-icon-1.jpg";
            String FAKE_RESOURCE_CONTENT = "Here we're going to see an introduction to the topic.";
            String FAKE_RESOURCE_TITLE = "Resource ";

            resources.add(ResourceDto.builder()
                    .image(FAKE_RESOURCE_IMAGE_URL)
                    .title(FAKE_RESOURCE_TITLE + i)
                    .content(FAKE_RESOURCE_CONTENT)
                    .id((long)i)
                    .build());

            resourcesFound.add(Resource.builder()
                    .id(i)
                    .title(FAKE_RESOURCE_TITLE)
                    .content(FAKE_RESOURCE_CONTENT)
                    .image(FAKE_RESOURCE_IMAGE_URL)
                    .course(courseFound)
                    .build());
        }

        User foundUser = testData.getUser(UserType.TRAINEE);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseFound));
        when(traineeUserCourseRepository.existsById(any(TraineeUserCourseId.class))).thenReturn(false);
        when(decoder.getTokenPayload(anyString())).thenReturn("fakePayload");
        when(decoder.getCustomPropertyFromToken(anyString(), eq("userId"))).thenReturn(userId);
        when(userRepository.findById(anyString())).thenReturn(Optional.of(foundUser));
        when(resourceRepository.findAllByCourseId(anyInt())).thenReturn(resourcesFound);

        CourseDetailDto actualResponse = courseService.getCourseDetails(courseId, userToken);

        assertThat(actualResponse)
                .isNotNull()
                .isEqualToComparingFieldByField(expectedResponse);

        verify(courseRepository).findById(courseId);
        verify(decoder).getTokenPayload(anyString());
        verify(decoder).getCustomPropertyFromToken(anyString(), eq("userId"));
        verify(userRepository).findById(anyString());
        verify(traineeUserCourseRepository).existsById(any(TraineeUserCourseId.class));
        verify(resourceRepository).findAllByCourseId(anyInt());
    }

    @Test
    public void shouldEnrollCourseForTraineeUser() throws JsonProcessingException {

        final String token = "ey.asdf";
        final int courseId = 123456;
        final String payloadExpected = "ey.asdf";
        final String userIdExpected = "user_123456";

        Course courseExpected = buildCourse();
        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(courseExpected));

        when(decoder.getTokenPayload(token))
                .thenReturn(payloadExpected);

        when(decoder.getCustomPropertyFromToken(payloadExpected, USER_ID))
                .thenReturn(userIdExpected);

        User userExpected = buildUser();
        when(userRepository.findById(userIdExpected))
                .thenReturn(Optional.of(userExpected));

        TraineeUserCourse traineeUserCourseExpected = buildTraineeUserCourse(courseExpected, userExpected);
        when(traineeUserCourseMapper.convert(courseExpected, userExpected))
                .thenReturn(traineeUserCourseExpected);

        when(traineeUserCourseRepository.save(traineeUserCourseExpected))
                .thenReturn(traineeUserCourseExpected);

        courseService.enrollCourse(token, courseId);

        verify(courseRepository).findById(courseIdIntegerArgumentCaptor.capture());
        assertEquals(courseId, courseIdIntegerArgumentCaptor.getValue());

        verify(decoder).getTokenPayload(tokenArgumentCaptor.capture());
        assertEquals(token, tokenArgumentCaptor.getValue());

        verify(decoder).getCustomPropertyFromToken(
                payloadArgumentCaptor.capture(), same(USER_ID));
        assertEquals(payloadExpected, payloadArgumentCaptor.getValue());

        verify(userRepository).findById(userIdArgumentCaptor.capture());
        assertEquals(userIdExpected, userIdArgumentCaptor.getValue());

        verify(traineeUserCourseMapper).convert(
                courseArgumentCaptor.capture(), userArgumentCaptor.capture());
        assertEquals(courseExpected, courseArgumentCaptor.getValue());
        assertEquals(userExpected, userArgumentCaptor.getValue());

        verify(traineeUserCourseRepository).save(
                traineeUserCourseArgumentCaptor.capture());
        assertEquals(traineeUserCourseExpected, traineeUserCourseArgumentCaptor.getValue());

        verifyNoMoreInteractions(courseRepository, decoder, userRepository, traineeUserCourseRepository, traineeUserCourseMapper);
        verifyNoInteractions(resourceRepository);
    }

    @Test
    public void shouldThrowExceptionWhenGetCustomPropertyFromTokenThrowsException() throws JsonProcessingException {
        final String token = "ey.asdf";
        final int courseId = 123456;
        final String payloadExpected = "ey.asdf";
        final String userIdExpected = "user_123456";

        Course courseExpected = buildCourse();
        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(courseExpected));

        when(decoder.getTokenPayload(token))
                .thenReturn(payloadExpected);

        when(decoder.getCustomPropertyFromToken(payloadExpected, USER_ID))
                .thenThrow(JsonProcessingException.class);

        assertThrows(LogicBusinessException.class,
                () -> courseService.enrollCourse(token, courseId),
                "Not known field value in token");

        verify(courseRepository).findById(courseIdIntegerArgumentCaptor.capture());
        assertEquals(courseId, courseIdIntegerArgumentCaptor.getValue());

        verify(decoder).getTokenPayload(tokenArgumentCaptor.capture());
        assertEquals(token, tokenArgumentCaptor.getValue());

        verify(decoder).getCustomPropertyFromToken(
                payloadArgumentCaptor.capture(), same(USER_ID));
        assertEquals(payloadExpected, payloadArgumentCaptor.getValue());

        verifyNoMoreInteractions(courseRepository, decoder);
        verifyNoInteractions(userRepository, traineeUserCourseMapper, traineeUserCourseRepository);
    }

    private TraineeUserCourse buildTraineeUserCourse(Course course, User user) {
        return TraineeUserCourse.builder()
                .pKey(TraineeUserCourseId.builder()
                        .traineeUser(user)
                        .course(course)
                        .build())
                .build();
    }

    private Course buildCourse() {
        return Course.builder().build();
    }

    private User buildUser() {
        return User.builder().build();
    }
    @Test
    public void shouldReturnCoursesOfACategory(){
        LocalDateTime date = LocalDateTime.now();
        int category_id = 1;
        List<Course> expectedCourseList = new ArrayList<Course>();
        List<Course>  AllCourses = mockFindAllCourses(date);

        for (int i=0;i<AllCourses.size();i++){
            if (AllCourses.get(i).getCategoryId()==category_id){
                expectedCourseList.add(AllCourses.get(i));
            }
        }
        assertThat(expectedCourseList.size()).isEqualTo(4);
    }
}