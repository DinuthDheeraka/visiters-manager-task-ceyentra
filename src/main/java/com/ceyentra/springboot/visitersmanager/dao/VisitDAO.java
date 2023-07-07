package com.ceyentra.springboot.visitersmanager.dao;

import com.ceyentra.springboot.visitersmanager.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitDAO extends JpaRepository<Visit, Integer> {
}
