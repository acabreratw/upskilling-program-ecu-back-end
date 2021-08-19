package com.thoughtworks.lpe.be_template.services;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourseId;
import com.thoughtworks.lpe.be_template.domains.User;
import com.thoughtworks.lpe.be_template.domains.enums.CourseStatus;
import com.thoughtworks.lpe.be_template.dtos.CourseDetailDto;
import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import com.thoughtworks.lpe.be_template.dtos.TrainerDto;
import com.thoughtworks.lpe.be_template.exceptions.LogicBusinessException;
import com.thoughtworks.lpe.be_template.mappers.CourseMapper;
import com.thoughtworks.lpe.be_template.repositories.CourseRepository;
import com.thoughtworks.lpe.be_template.repositories.TraineeUserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.thoughtworks.lpe.be_template.exceptions.enums.Error.INVALID_COURSE_ID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TraineeUserCourseRepository traineeUserCourseRepository;

    public void saveCourse(CourseDto courseDto) {
        courseRepository.save(CourseMapper.dtoToDomain(courseDto));
    }

    public List<CourseDto> findAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseMapper::domainToDto)
                .collect(Collectors.toList());
    }

    public List<CourseDto> findOpenedCourses(String userEmail, int page, int limit) {
        return courseRepository.findAll().stream()
                .filter(isAnOpenedCourseForTheUser(userEmail))
                .map(CourseMapper::domainToDto)
                .skip((long) page *limit).limit(limit)
                .collect(Collectors.toList());
    }

    public void updateCourse(CourseDto updateCourseDto) {
        Optional<Course> course = Optional.ofNullable(courseRepository.findById(updateCourseDto.getId()).orElseThrow(EntityNotFoundException::new));

        if (course.isPresent()) {
            Course actualCourse = CourseMapper.dtoToDomain(updateCourseDto);
            actualCourse.setId(course.get().getId());

            courseRepository.save(actualCourse);
        }
    }
    
    public CourseDto findCourseById(int courseId) {
        return courseRepository.findById(courseId).map(CourseMapper::domainToDto)
                .orElseThrow(this::throwLogicBusinessExceptionBecauseOfInvalidCourseId);
    }

    private Predicate<Course> isAnOpenedCourseForTheUser(String userEmail) {
        List<CourseStatus> subscribedCoursesStatuses = Arrays.asList(CourseStatus.IN_PROGRESS, CourseStatus.PASSED);
        return courseDto -> !traineeUserCourseRepository.findAllByUserIdAndStatusIn(userEmail, subscribedCoursesStatuses).stream()
                .map(traineeUserCourse -> traineeUserCourse.getPKey().getCourse().getId())
                .collect(Collectors.toList())
                .contains(courseDto.getId());
    }

    private LogicBusinessException throwLogicBusinessExceptionBecauseOfInvalidCourseId(){
        return new LogicBusinessException(INVALID_COURSE_ID);
    }

    public CourseDetailDto getCourseDetails(int courseId, String accessToken) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (!optionalCourse.isPresent()) {
            //TODO: Make a more suitable exception handling
            throw new RuntimeException(String.format("Could not find any Course with id %s", courseId));
        }

        Course actualCourse = optionalCourse.get();


        //TODO: Replace this with access token decryption and user query
        User traineeUser = new User();

        String courseStatus = this.getCourseStatusByStartAndEndDate(actualCourse.getFreeStartDate(), actualCourse.getFreeEndDate());

        TraineeUserCourseId lookupComposeId = TraineeUserCourseId.builder()
                .course(actualCourse)
                .traineeUser(traineeUser)
                .build();

        boolean isUserAlreadyEnrolled = traineeUserCourseRepository.existsById(lookupComposeId);

        //TODO: This section should be replaced with a proper query in next sprints when Trainer's stories get into the scope
        final String FAKE_TRAINER_ID = "Mock_trainer_id";
        final String FAKE_TRAINER_DESCRIPTION = "Best educator in the world. Won Nobel prize of Education in 2021";
        final String FAKE_TRAINER_NAME = "Mary Lee";
        final String FAKE_TRAINER_TITLE = "Best of the Best";
        final String FAKE_TRAINER_IMAGE = "https://media.istockphoto.com/photos/portrait-of-smiling-professor-in-the-amphitheater-picture-id1128666909?k=6&m=1128666909&s=612x612&w=0&h=gwBz0Hi_DIhpcwrX64agp-iYbGGehPpRfuw6KnsRU8s=";

        return CourseDetailDto.builder()
                .id(actualCourse.getId())
                .title(actualCourse.getName())
                .description(actualCourse.getDescription())
                .resources(new HashSet<>()) //TODO: Query resources and add them here
                .trainer(TrainerDto.builder()
                        .id(FAKE_TRAINER_ID)
                        .name(FAKE_TRAINER_NAME)
                        .description(FAKE_TRAINER_DESCRIPTION)
                        .title(FAKE_TRAINER_TITLE)
                        .image(FAKE_TRAINER_IMAGE)
                        .build())
                .status(courseStatus)
                .enrolled(isUserAlreadyEnrolled)
                .image(actualCourse.getImageUrl())
                .build();
    }

    public String getCourseStatusByStartAndEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        boolean isOpen = currentDateTime.isEqual(startDate) || currentDateTime.isBefore(startDate);
        boolean isInProgress = currentDateTime.isAfter(startDate) && (currentDateTime.isEqual(endDate) || currentDateTime.isBefore(endDate));

        if (isOpen) {
            return CourseStatus.OPEN.name();
        } else {
            if (isInProgress) {
                return CourseStatus.IN_PROGRESS.name();
            } else {
                return CourseStatus.CLOSED.name();
            }
        }
    }
}
