package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO saveUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    String deleteUserById(int id);

    UserDTO readUserById(int id);

    List<UserDTO> readAllUsers();

//    Optional<SystemUserEntity> findByUserName(String userName);
}
