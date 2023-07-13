package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.SystemUserDTO;
import com.ceyentra.springboot.visitersmanager.entity.SystemUserEntity;

import java.util.List;
import java.util.Optional;

public interface SystemUserService {

    SystemUserDTO saveSystemUser(SystemUserDTO systemUserDTO);

    SystemUserDTO updateSystemUser(SystemUserDTO systemUserDTO);

    String deleteSystemUserById(int id);

    SystemUserDTO readSystemUserById(int id);

    List<SystemUserDTO> readAllSystemUsers();

    Optional<SystemUserEntity> findByUserName(String userName);
}
