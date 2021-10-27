package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourse;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourseId;
import com.thoughtworks.lpe.be_template.domains.User;
import org.springframework.stereotype.Service;

import static com.thoughtworks.lpe.be_template.domains.enums.CourseStatus.IN_PROGRESS;

@Service
public class TraineeUserCourseMapper {

    public TraineeUserCourse convert(Course course, User user) {
        return TraineeUserCourse.builder()
                .pKey(TraineeUserCourseId.builder()
                        .traineeUser(user)
                        .course(course)
                        .build())
                .status(IN_PROGRESS)
                .build();
    }

}
