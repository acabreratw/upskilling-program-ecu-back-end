package com.thoughtworks.lpe.be_template.controllers;

import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.mappers.CourseMapper;
import com.thoughtworks.lpe.be_template.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    @PostMapping
    public ResponseEntity<String> saveCourse(@RequestBody CourseDto courseDto) {
        courseService.saveCourse(courseDto);
        return ResponseEntity.ok("saved successfully");
    }

    @GetMapping(path = {
            "/all/{userEmail}/{page}/{limit}",
            "/all/{userEmail}/{page}",
            "/all/{userEmail}"})
    public ResponseEntity<List<CourseDto>> getOpenedCourses(@PathVariable("userEmail") final String userEmail,
                                                            @PathVariable("page") final Optional<Integer> page,
                                                            @PathVariable("limit") final Optional<Integer> limit) {
        List<CourseDto> courseDtoList =
                new ArrayList<>(courseService.findOpenedCourses(userEmail, page.orElse(0), limit.orElse(10)));
        return ResponseEntity.ok(courseDtoList);
    }

    @GetMapping(path = "/allCourses")
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        List<CourseDto> courseDtoListCourses = courseService.findAllCourses();
        return ResponseEntity.ok(courseDtoListCourses);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable("id") Integer id) {
        CourseDto courseDto =  courseService.findCourseById(id);
        return ResponseEntity.ok(courseDto);
    }

    @PutMapping
    public ResponseEntity<String> updateCourse(@RequestBody CourseDto courseDto) {
        courseService.updateCourse(courseDto);
        return ResponseEntity.ok("updated successfully");
    }

}
