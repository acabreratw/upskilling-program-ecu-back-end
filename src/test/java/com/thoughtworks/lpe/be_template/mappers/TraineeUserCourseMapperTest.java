package com.thoughtworks.lpe.be_template.mappers;

import com.thoughtworks.lpe.be_template.domains.Course;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourse;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourseId;
import com.thoughtworks.lpe.be_template.domains.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static com.thoughtworks.lpe.be_template.domains.enums.CourseStatus.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class TraineeUserCourseMapperTest {

    @InjectMocks
    private TraineeUserCourseMapper traineeUserCourseMapper;

    @Test
    public void shouldReturnTraineeUserCourseWhenInvokeConvertMethod() {

        TraineeUserCourse actual = traineeUserCourseMapper.convert(buildCourse(), buildUser());
        assertNotNull(actual);
        assertEquals(buildExpectedTraineeUserCourse(buildCourse(), buildUser()), actual);
    }

    private TraineeUserCourse buildExpectedTraineeUserCourse(Course course, User user) {
        return TraineeUserCourse.builder()
                .pKey(TraineeUserCourseId.builder()
                        .traineeUser(user)
                        .course(course)
                        .build())
                .status(IN_PROGRESS)
                .build();
    }

    private Course buildCourse() {
        return Course.builder()
                .id(123456)
                .build();
    }

    private User buildUser() {
        return User.builder()
                .id("user_123456")
                .build();
    }
}