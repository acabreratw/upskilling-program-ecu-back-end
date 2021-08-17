package com.thoughtworks.lpe.be_template.domains;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeUserCourseId implements Serializable {
    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "course_id")
    @NotNull
    private Course course;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    @NotNull
    private User traineeUser;
}
