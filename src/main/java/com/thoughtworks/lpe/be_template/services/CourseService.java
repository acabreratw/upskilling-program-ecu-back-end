package com.thoughtworks.lpe.be_template.services;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.CourseStatus;
import com.thoughtworks.lpe.be_template.dtos.UserCourseDto;
import com.thoughtworks.lpe.be_template.exceptions.LogicBusinessException;
import com.thoughtworks.lpe.be_template.exceptions.enums.Error;
import com.thoughtworks.lpe.be_template.mappers.CourseMapper;
import com.thoughtworks.lpe.be_template.repositories.CourseRepository;
import com.thoughtworks.lpe.be_template.repositories.UserCourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.thoughtworks.lpe.be_template.exceptions.enums.Error.INVALID_COURSE_ID;

public interface CourseService {

    void saveCourse(Course course);

    List<Course> findOpenedCourses(String userEmail, int page, int limit);

    void updateCourse(Course updateCourse);

    Course findCourseById(int courseId);

    void deleteCourse(int courseId);
}
