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

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;

    public CourseService(CourseRepository courseRepository, UserCourseRepository userCourseRepository) {
        this.courseRepository = courseRepository;
        this.userCourseRepository = userCourseRepository;
    }

    public void saveCourse(Course course) {
        courseRepository.save(new CourseMapper().domainToDto(course));
    }

    public List<Course> findOpenedCourses(String userEmail, int page, int limit){
        return courseRepository.findAll().stream()
                .filter(isAnOpenedCourseForTheUser(userEmail))
                .map(CourseMapper::dtoToDomain)
                .skip(page*limit).limit(limit)
                .collect(Collectors.toList());
    }

    private Predicate<CourseDto> isAnOpenedCourseForTheUser(String userEmail) {
        List<CourseStatus> subscribedCoursesStatuses = Arrays.asList(CourseStatus.PRO, CourseStatus.APR);
        return courseDto -> !userCourseRepository.findAllByUserEmailAndStatusIn(userEmail, subscribedCoursesStatuses).stream()
                .map(UserCourseDto::getCourseId)
                .collect(Collectors.toList())
                .contains(courseDto.getId());
    }

    public void updateCourse(Course updateCourse) {
        Optional<CourseDto> course = courseRepository.findById(updateCourse.getId());

        CourseDto updateCourseDto = new CourseMapper().domainToDto(updateCourse);
        CourseDto courseDto = course.orElseThrow(() -> new EntityNotFoundException()) ;
        courseDto = updateCourseDto;

        courseRepository.save(courseDto);
    }

    public Course findCourseById(int courseId) {
        return courseRepository.findById(courseId)
                .map(CourseMapper::dtoToDomain)
                .orElseThrow(this::throwLogicBusinessExceptionBecauseOfInvalidCourseId);
    }

    private LogicBusinessException throwLogicBusinessExceptionBecauseOfInvalidCourseId(){
        return new LogicBusinessException(INVALID_COURSE_ID);
    }

    @Transactional
    public void deleteCourse(int courseId){
        userCourseRepository.deleteAllByCourseId(courseId);
        courseRepository.deleteById(courseId);
    }
}
