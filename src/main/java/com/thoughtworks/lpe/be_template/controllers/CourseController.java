package com.thoughtworks.lpe.be_template.controllers;

import com.thoughtworks.lpe.be_template.controllers.resources.CourseResource;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.mappers.CourseMapper;
import com.thoughtworks.lpe.be_template.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    @PostMapping
    public ResponseEntity<String> saveCourse(@RequestBody CourseResource courseResource) {
        CourseDto courseDto = courseMapper.resourceToDomain(courseResource);
        courseService.saveCourse(courseDto);
        return ResponseEntity.ok("saved successfully");
    }

    @GetMapping(path = {
            "/all/{userEmail}/{page}/{limit}",
            "/all/{userEmail}/{page}",
            "/all/{userEmail}"})
    public ResponseEntity<List<CourseResource>> getOpenedCourses(@PathVariable("userEmail") final String userEmail,
                                                                 @PathVariable("page") final Optional<Integer> page,
                                                                 @PathVariable("limit") final Optional<Integer> limit) {
        List<CourseResource> courseResourceList =
                courseService.findOpenedCourses(userEmail, page.orElse(0), limit.orElse(10)).stream()
                .map(CourseMapper::domainToResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseResourceList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CourseResource> getCourse(@PathVariable("id") Integer id) {
        CourseResource courseResource =  CourseMapper.domainToResource(courseService.findCourseById(id));
        return ResponseEntity.ok(courseResource);
    }

    @PutMapping
    public ResponseEntity<String> updateCourse(@RequestBody CourseResource courseResource) {
        CourseDto courseDto = courseMapper.resourceToDomain(courseResource);
        courseService.updateCourse(courseDto);
        return ResponseEntity.ok("updated successfully");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") Integer id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("deleted successfully");
    }
}
