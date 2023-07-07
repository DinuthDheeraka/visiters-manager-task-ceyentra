package com.ceyentra.springboot.visitersmanager.dao;

import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface SystemUserDAO extends JpaRepository<SystemUserDAO, Integer> {
}
