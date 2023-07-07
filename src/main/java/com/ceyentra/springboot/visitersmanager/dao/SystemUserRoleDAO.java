package com.ceyentra.springboot.visitersmanager.dao;

import com.ceyentra.springboot.visitersmanager.entity.SystemUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRoleDAO extends JpaRepository<SystemUserRole, Integer> {
}
