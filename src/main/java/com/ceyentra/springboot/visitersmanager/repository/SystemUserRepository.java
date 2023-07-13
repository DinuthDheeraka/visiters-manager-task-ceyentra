package com.ceyentra.springboot.visitersmanager.repository;

import com.ceyentra.springboot.visitersmanager.entity.SystemUserEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Registered
public interface SystemUserRepository extends JpaRepository<SystemUserEntity, Integer> {

    Optional<SystemUserEntity> findByUserName(String userName);
}
