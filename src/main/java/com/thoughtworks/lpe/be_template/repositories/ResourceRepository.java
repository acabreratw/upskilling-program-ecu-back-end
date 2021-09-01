package com.thoughtworks.lpe.be_template.repositories;

import com.thoughtworks.lpe.be_template.domains.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    Set<Resource> findAllByCourseId(Integer courseId);
}
