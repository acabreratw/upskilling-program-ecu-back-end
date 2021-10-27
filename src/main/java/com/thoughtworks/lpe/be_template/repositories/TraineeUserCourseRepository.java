package com.thoughtworks.lpe.be_template.repositories;

import com.thoughtworks.lpe.be_template.domains.TraineeUserCourse;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourseId;
import com.thoughtworks.lpe.be_template.domains.enums.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TraineeUserCourseRepository extends JpaRepository<TraineeUserCourse, TraineeUserCourseId> {
    @Query("FROM TraineeUserCourse as tuc WHERE tuc.pKey.traineeUser.id=:userId AND tuc.status IN :statuses")
    List<TraineeUserCourse> findAllByUserIdAndStatusIn(@Param("userId") String userId, @Param("statuses") Collection<CourseStatus> statuses);
}
