package com.thoughtworks.lpe.be_template.services;

import com.thoughtworks.lpe.be_template.dtos.CourseDto;

import java.util.List;

public interface CourseService {

    void saveCourse(CourseDto courseDto);

    List<CourseDto> findOpenedCourses(String userEmail, int page, int limit);

    void updateCourse(CourseDto updateCourseDto);

    CourseDto findCourseById(int courseId);

    void deleteCourse(int courseId);
}
