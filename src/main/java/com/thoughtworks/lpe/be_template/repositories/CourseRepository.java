package com.thoughtworks.lpe.be_template.repositories;

import com.thoughtworks.lpe.be_template.domains.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
