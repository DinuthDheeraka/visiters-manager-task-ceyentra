package com.ceyentra.springboot.visitersmanager.repository;

import com.ceyentra.springboot.visitersmanager.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByEmail(String email);
}
