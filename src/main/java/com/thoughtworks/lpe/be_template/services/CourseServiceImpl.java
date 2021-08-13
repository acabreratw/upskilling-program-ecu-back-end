package com.thoughtworks.lpe.be_template.services;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.UserCourse;
import com.thoughtworks.lpe.be_template.domains.enums.CourseStatus;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.exceptions.LogicBusinessException;
import com.thoughtworks.lpe.be_template.mappers.CourseMapper;
import com.thoughtworks.lpe.be_template.repositories.CourseRepository;
import com.thoughtworks.lpe.be_template.repositories.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Override
    public void saveCourse(CourseDto courseDto) {
        courseRepository.save(CourseMapper.dtoToDomain(courseDto));
    }

    @Override
    public List<CourseDto> findOpenedCourses(String userEmail, int page, int limit) {
        return courseRepository.findAll().stream()
                .filter(isAnOpenedCourseForTheUser(userEmail))
                .map(CourseMapper::domainToDto)
                .skip((long) page *limit).limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCourse(CourseDto updateCourseDto) {
        Optional<Course> course = Optional.ofNullable(courseRepository.findById(updateCourseDto.getId()).orElseThrow(EntityNotFoundException::new));

        if (course.isPresent()) {
            Course actualCourse = CourseMapper.dtoToDomain(updateCourseDto);
            actualCourse.setId(course.get().getId());

            courseRepository.save(actualCourse);
        }
    }

    @Override
    public CourseDto findCourseById(int courseId) {
        return courseRepository.findById(courseId).map(CourseMapper::domainToDto)
                .orElseThrow(this::throwLogicBusinessExceptionBecauseOfInvalidCourseId);
    }

    @Transactional
    @Override
    public void deleteCourse(int courseId) {
        userCourseRepository.deleteAllByCourseId(courseId);
        courseRepository.deleteById(courseId);
    }

    private Predicate<Course> isAnOpenedCourseForTheUser(String userEmail) {
        List<CourseStatus> subscribedCoursesStatuses = Arrays.asList(CourseStatus.PRO, CourseStatus.APR);
        return courseDto -> !userCourseRepository.findAllByUserEmailAndStatusIn(userEmail, subscribedCoursesStatuses).stream()
                .map(UserCourse::getCourseId)
                .collect(Collectors.toList())
                .contains(courseDto.getId());
    }

    private LogicBusinessException throwLogicBusinessExceptionBecauseOfInvalidCourseId(){
        return new LogicBusinessException(INVALID_COURSE_ID);
    }
}
