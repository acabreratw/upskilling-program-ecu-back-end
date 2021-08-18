package com.thoughtworks.lpe.be_template.domains;

import com.sun.istack.NotNull;
import com.thoughtworks.lpe.be_template.domains.enums.CourseStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "trainee_user_course")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TraineeUserCourse implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @NotNull
    private TraineeUserCourseId pKey;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull
    private CourseStatus status;

}
