package com.thoughtworks.lpe.be_template.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thoughtworks.lpe.be_template.controllers.resources.CourseResource;
import com.thoughtworks.lpe.be_template.controllers.resources.ErrorResponse;
import com.thoughtworks.lpe.be_template.controllers.resources.builders.CourseResourceBuilder;
import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.builders.CourseBuilder;
import com.thoughtworks.lpe.be_template.exceptions.LogicBusinessException;
import com.thoughtworks.lpe.be_template.exceptions.enums.Error;
import com.thoughtworks.lpe.be_template.services.CourseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.thoughtworks.lpe.be_template.util.Constants.DATETIME_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class CourseControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private CourseService courseService;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(this.webApplicationContext).build();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void shouldReturn200WhenCourseIsSavedSuccessfully() throws Exception {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        CourseResource courseResource = new CourseResourceBuilder().withDescription("Description")
                .withFreeEndDate(dateString)
                .withFreeStartDate(dateString)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .build();

        String jsonCourse = mapper.writeValueAsString(courseResource);

        Course expectedCourse = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .build();

        RequestBuilder requestBuilder = post("/api/v1/course")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCourse);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        ArgumentCaptor<Course> argumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).saveCourse(argumentCaptor.capture());
        Course captureCourse = argumentCaptor.<Course>getValue();

        assertThat(captureCourse).isEqualToComparingFieldByField(expectedCourse);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo("saved successfully");
    }

    @Test
    public void shouldReturnOpenedCoursesGivenTheUserEmail() throws Exception {

        String userEmail = "getabstract@mail.com";
        String page = "2";
        String limit = "4";
        LocalDateTime dateTime = LocalDateTime.now();
        when(courseService.findOpenedCourses(userEmail,2,4))
                .thenReturn(mockFindOpenedCourses(dateTime));
        RequestBuilder requestBuilder = get("/api/v1/course/all/{userEmail}/{page}/{limit}",
                userEmail, page, limit)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        List<CourseResource> courseResourceList =
                mapper.readerForListOf(CourseResource.class)
                        .readValue(result.getResponse().getContentAsString());
        assertThat(courseResourceList.size()).isEqualTo(2);
    }

    @Test
    public void shouldReturnOpenedCoursesFirstPageGivenTheUserEmail() throws Exception {

        String userEmail = "getabstract@mail.com";
        LocalDateTime dateTime = LocalDateTime.now();
        when(courseService.findOpenedCourses(userEmail,0,10))
                .thenReturn(mockFindAllOpenedCourses(dateTime));
        RequestBuilder requestBuilder =
                get("/api/v1/course/all/{userEmail}/{page}/{limit}", userEmail, null, null)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        List<CourseResource> courseResourceList =
                mapper.readerForListOf(CourseResource.class)
                        .readValue(result.getResponse().getContentAsString());
        assertThat(courseResourceList.size()).isEqualTo(6);
    }

    @Test
    public void shouldReturn200WhenCourseIsUpdatedSuccessfully() throws Exception {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        CourseResource courseResource = new CourseResourceBuilder().withDescription("Description")
                .withFreeEndDate(dateString)
                .withFreeStartDate(dateString)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .build();

        String jsonCourse = mapper.writeValueAsString(courseResource);

        Course expectedCourse = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .build();

        RequestBuilder requestBuilder = put("/api/v1/course")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCourse);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        ArgumentCaptor<Course> argumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).updateCourse(argumentCaptor.capture());
        Course captureCourse = argumentCaptor.<Course>getValue();

        assertThat(captureCourse).isEqualToComparingFieldByField(expectedCourse);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo("updated successfully");
    }

    @Test
    public void shouldReturn404WhenCourseIdDoNotExist() throws Exception {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        CourseResource courseResource = new CourseResourceBuilder().withDescription("Description")
                .withFreeEndDate(dateString)
                .withFreeStartDate(dateString)
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .build();

        String jsonCourse = mapper.writeValueAsString(courseResource);

        Course expectedCourse = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl("url")
                .withName("Test course")
                .withPrice(BigDecimal.TEN)
                .build();

        RequestBuilder requestBuilder = put("/api/v1/course")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCourse);

        doThrow(new EntityNotFoundException()).when(courseService).updateCourse(any(Course.class));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        ArgumentCaptor<Course> argumentCaptor = ArgumentCaptor.forClass(Course.class);
        verify(courseService).updateCourse(argumentCaptor.capture());
        Course captureCourse = argumentCaptor.<Course>getValue();

        assertThat(captureCourse).isEqualToComparingFieldByField(expectedCourse);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    private List<Course> mockFindOpenedCourses(LocalDateTime dateTime){
        return Arrays.asList(
                new Course("Course 8th", "Description", BigDecimal.TEN,
                        "Image url", dateTime, dateTime, 8),
                new Course("Course 9th", "Description", BigDecimal.TEN,
                        "Image url", dateTime, dateTime, 9));
    }

    private List<Course> mockFindAllOpenedCourses(LocalDateTime dateTime){
        return Arrays.asList(
                new Course("Course 3th", "Description", BigDecimal.TEN,
                        "Image url", dateTime, dateTime, 3),
                new Course("Course 4th", "Description", BigDecimal.TEN,
                        "Image url", dateTime, dateTime, 4),
                new Course("Course 6th", "Description", BigDecimal.TEN,
                        "Image url", dateTime, dateTime, 6),
                new Course("Course 7th", "Description", BigDecimal.TEN,
                        "Image url", dateTime, dateTime, 7),
                new Course("Course 8th", "Description", BigDecimal.TEN,
                        "Image url", dateTime, dateTime, 8),
                new Course("Course 9th", "Description", BigDecimal.TEN,
                        "Image url", dateTime, dateTime, 9));
    }

    @Test
    public void shouldReturnCourseDetailsGivenCourseId() throws Exception {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        CourseResource expectedCourseResource = new CourseResourceBuilder().withDescription("Description")
                .withFreeEndDate(dateString).withFreeStartDate(dateString).withImageUrl("Image url")
                .withName("Course 1th").withPrice(BigDecimal.TEN).withId(1).build();
        Course course = new CourseBuilder().withDescription("Description")
                .withFreeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withFreeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .withImageUrl("Image url").withName("Course 1th").withPrice(BigDecimal.TEN).withId(1).build();
        when(courseService.findCourseById(1)).thenReturn(course);
        RequestBuilder requestBuilder = get("/api/v1/course/{id}", String.valueOf(1))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        CourseResource courseResource =
                mapper.readValue(result.getResponse().getContentAsString(), CourseResource.class);
        assertThat(courseResource).isEqualToComparingFieldByField(expectedCourseResource);
    }

    @Test
    public void shouldReturnAnErrorResponseGivenAnInvalidCourseId() throws Exception {
        LocalDateTime date = LocalDateTime.now();
        String invalidCourseId = "-1";
        ErrorResponse expectedErrorResponse = new ErrorResponse(Error.INVALID_COURSE_ID.getCode(),
                Error.INVALID_COURSE_ID.getDeveloperMessage(),Error.INVALID_COURSE_ID.getUserMessage());
        when(courseService.findCourseById(-1))
                .thenThrow(new LogicBusinessException(Error.INVALID_COURSE_ID));
        RequestBuilder requestBuilder = get("/api/v1/course/{id}", invalidCourseId)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ErrorResponse courseResource =
                mapper.readValue(result.getResponse().getContentAsString(), ErrorResponse.class);
        assertThat(courseResource).isEqualToComparingFieldByField(expectedErrorResponse);
    }

    @Test
    public void shouldReturn200WhenCourseIsDeletedSuccessfully() throws Exception {

        Integer courseId = 10;
        RequestBuilder requestBuilder = delete("/api/v1/course/{id}", courseId)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(courseService).deleteCourse(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualToComparingFieldByField(courseId);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo("deleted successfully");
    }
}