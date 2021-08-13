package com.thoughtworks.lpe.be_template.repositories;

import com.thoughtworks.lpe.be_template.dtos.CourseStatus;
import com.thoughtworks.lpe.be_template.domains.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
    List<UserCourse> findAllByUserEmailAndStatusIn(String userEmail, Collection<CourseStatus> statuses);
    void deleteAllByCourseId(int courseId);
}
