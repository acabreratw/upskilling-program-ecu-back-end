package com.thoughtworks.lpe.be_template.controllers;

import com.thoughtworks.lpe.be_template.services.CourseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CourseControllerTest {

    @InjectMocks
    private CourseController courseController;

    @Mock
    private CourseService courseService;

    @Test
    public void testEnrollCourse() {

        final String token = "";
        final Integer courseId = 0;

        when(courseService.enrollCourse(token, courseId))
                .thenReturn(null);

        ResponseEntity<Object> objectResponseEntity = courseController.enrollCourse(token, courseId);
        assertNull(objectResponseEntity.getBody());
        assertEquals(HttpStatus.OK, objectResponseEntity.getStatusCode());

        verify(courseService).enrollCourse(same(token), same(courseId));

        verifyNoMoreInteractions(courseService);

    }
}