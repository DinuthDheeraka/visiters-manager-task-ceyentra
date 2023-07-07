package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.SystemUserDTO;

import java.util.List;

public interface SystemUserService {

    SystemUserDTO saveSystemUser(SystemUserDTO systemUserDTO);

    SystemUserDTO updateSystemUser(SystemUserDTO systemUserDTO);

    SystemUserDTO deleteSystemUserById(int id);

    SystemUserDTO readSystemUserById(int id);

    List<SystemUserDTO> readAllSystemUsers();
}
