package com.ceyentra.springboot.visitersmanager.dao;

import com.ceyentra.springboot.visitersmanager.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitorDAO extends JpaRepository<Visitor, Integer> {
}
