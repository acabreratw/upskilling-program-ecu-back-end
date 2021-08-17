package com.thoughtworks.lpe.be_template.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thoughtworks.lpe.be_template.controllers.resources.ErrorResponse;
import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.thoughtworks.lpe.be_template.util.Constants.DATETIME_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
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
        CourseDto courseResource = CourseDto.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(dateString))
                .freeStartDate(LocalDateTime.parse(dateString))
                .imageUrl("url")
                .name("Test course")
                .build();

        String jsonCourse = mapper.writeValueAsString(courseResource);

        CourseDto expectedCourseDto =  CourseDto.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .freeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .imageUrl("url")
                .name("Test course")
                .build();

        RequestBuilder requestBuilder = post("/api/v1/course")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCourse);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        ArgumentCaptor<CourseDto> argumentCaptor = ArgumentCaptor.forClass(CourseDto.class);
        verify(courseService).saveCourse(argumentCaptor.capture());
        CourseDto captureCourseDto = argumentCaptor.<CourseDto>getValue();

        assertThat(captureCourseDto).isEqualToComparingFieldByField(expectedCourseDto);
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
        List<CourseDto> courseResourceList =
                mapper.readerForListOf(CourseDto.class)
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
        List<CourseDto> courseResourceList =
                mapper.readerForListOf(CourseDto.class)
                        .readValue(result.getResponse().getContentAsString());
        assertThat(courseResourceList.size()).isEqualTo(6);
    }

    @Test
    public void shouldReturn200WhenCourseIsUpdatedSuccessfully() throws Exception {
        LocalDateTime endDateTime = LocalDateTime.parse(LocalDateTime.now().plusDays(5).format(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
        LocalDateTime startDateTime = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
        
        CourseDto courseResource = CourseDto.builder().description("Description")
                .freeEndDate(endDateTime)
                .freeStartDate(startDateTime)
                .imageUrl("url")
                .name("Test course")
                .build();

        String jsonCourse = mapper.writeValueAsString(courseResource);

        Course expectedCourse = Course.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(endDateTime.toString(), DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .freeStartDate(LocalDateTime.parse(startDateTime.toString(), DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .imageUrl("url")
                .name("Test course")
                .build();

        RequestBuilder requestBuilder = put("/api/v1/course")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCourse);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        ArgumentCaptor<CourseDto> argumentCaptor = ArgumentCaptor.forClass(CourseDto.class);
        verify(courseService).updateCourse(argumentCaptor.capture());
        CourseDto captureCourse = argumentCaptor.getValue();

        assertThat(captureCourse).isEqualToComparingFieldByField(expectedCourse);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getResponse().getContentAsString()).isEqualTo("updated successfully");
    }

    @Test
    public void shouldReturn404WhenCourseIdDoNotExist() throws Exception {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        CourseDto courseResource = CourseDto.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(dateString))
                .freeStartDate(LocalDateTime.parse(dateString))
                .imageUrl("url")
                .name("Test course")
                .build();

        String jsonCourse = mapper.writeValueAsString(courseResource);

        CourseDto expectedCourseDto = CourseDto.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .freeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .imageUrl("url")
                .name("Test course")
                .build();

        RequestBuilder requestBuilder = put("/api/v1/course")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCourse);

        doThrow(new EntityNotFoundException()).when(courseService).updateCourse(any(CourseDto.class));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        ArgumentCaptor<CourseDto> argumentCaptor = ArgumentCaptor.forClass(CourseDto.class);
        verify(courseService).updateCourse(argumentCaptor.capture());
        CourseDto captureCourse = argumentCaptor.getValue();

        assertThat(captureCourse).isEqualToComparingFieldByField(expectedCourseDto);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

    }

    private List<CourseDto> mockFindOpenedCourses(LocalDateTime dateTime){
        return Arrays.asList(
                new CourseDto(8,"Course 8th", "Description",
                        "Image url", dateTime, dateTime),
                new CourseDto(9,"Course 9th", "Description",
                        "Image url", dateTime, dateTime));
    }

    private List<CourseDto> mockFindAllOpenedCourses(LocalDateTime dateTime){
        return Arrays.asList(
                new CourseDto(3, "Course 3th", "Description",
                        "Image url", dateTime, dateTime),
                new CourseDto(4, "Course 4th", "Description",
                        "Image url", dateTime, dateTime),
                new CourseDto(6, "Course 6th", "Description",
                        "Image url", dateTime, dateTime),
                new CourseDto(7, "Course 7th", "Description",
                        "Image url", dateTime, dateTime),
                new CourseDto(8, "Course 8th", "Description",
                        "Image url", dateTime, dateTime),
                new CourseDto(9, "Course 9th", "Description",
                        "Image url", dateTime, dateTime));
    }

    @Test
    public void shouldReturnCourseDetailsGivenCourseId() throws Exception {
        String dateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
        CourseDto expectedCourseDto = CourseDto.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(dateString)).freeStartDate(LocalDateTime.parse(dateString)).imageUrl("Image url")
                .name("Course 1th").id(1).build();
        CourseDto courseDto = CourseDto.builder().description("Description")
                .freeEndDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .freeStartDate(LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern(DATETIME_FORMAT)))
                .imageUrl("Image url").name("Course 1th").id(1).build();
        when(courseService.findCourseById(1)).thenReturn(courseDto);
        RequestBuilder requestBuilder = get("/api/v1/course/{id}", String.valueOf(1))
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        CourseDto courseResource =
                mapper.readValue(result.getResponse().getContentAsString(), CourseDto.class);
        assertThat(courseResource).isEqualToComparingFieldByField(expectedCourseDto);
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
}