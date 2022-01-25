package com.thoughtworks.lpe.be_template.repositories;

import com.thoughtworks.lpe.be_template.domains.Resource;
import com.thoughtworks.lpe.be_template.domains.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {
}
