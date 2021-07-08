package com.thoughtworks.lpe.be_template.repositories;

import com.thoughtworks.lpe.be_template.dtos.CourseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseDto, Integer> {
}
