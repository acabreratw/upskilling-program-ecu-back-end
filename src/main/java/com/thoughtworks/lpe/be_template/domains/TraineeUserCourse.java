package com.thoughtworks.lpe.be_template.domains;

import com.sun.istack.NotNull;
import com.thoughtworks.lpe.be_template.domains.enums.CourseStatus;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Table(name = "trainee_user_course")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class TraineeUserCourse implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @NotNull
    private TraineeUserCourseId traineeUserCoursePKey;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private CourseStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TraineeUserCourse that = (TraineeUserCourse) o;
        return Objects.equals(this.traineeUserCoursePKey, that.traineeUserCoursePKey);
    }

    @Override
    public int hashCode() {
        return this.traineeUserCoursePKey.hashCode();
    }


}