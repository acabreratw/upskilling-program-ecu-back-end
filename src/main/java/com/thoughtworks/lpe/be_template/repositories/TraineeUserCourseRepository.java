package com.thoughtworks.lpe.be_template.repositories;

import com.thoughtworks.lpe.be_template.domains.enums.CourseStatus;
import com.thoughtworks.lpe.be_template.domains.TraineeUserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TraineeUserCourseRepository extends JpaRepository<TraineeUserCourse, Integer> {
    List<TraineeUserCourse> findAllByUserEmailAndStatusIn(String userEmail, Collection<CourseStatus> statuses);
    void deleteAllByCourseId(int courseId);
}
