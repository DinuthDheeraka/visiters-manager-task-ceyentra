package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.entity.SystemUserDTO;

import java.util.List;

public interface SystemUserService {

    SystemUserDTO saveSystemUser(SystemUserDTO systemUserDTO);

    SystemUserDTO updateSystemUser(SystemUserDTO systemUserDTO);

    String deleteSystemUserById(int id);

    SystemUserDTO readSystemUserById(int id);

    List<SystemUserDTO> readAllSystemUsers();
}
