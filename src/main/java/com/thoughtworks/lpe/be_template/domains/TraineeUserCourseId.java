package com.thoughtworks.lpe.be_template.domains;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TraineeUserCourseId implements Serializable {
    @JoinColumn(referencedColumnName = "id", name = "course_id")
    @NotNull
    private Course course;

    @JoinColumn(referencedColumnName = "id", name = "user_id")
    @NotNull
    private User user;
}
