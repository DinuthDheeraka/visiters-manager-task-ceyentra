package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.UserDTO;
import com.ceyentra.springboot.visitersmanager.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO saveUser(UserDTO userDTO);

    UserDTO updateUser(UserDTO userDTO);

    String deleteUserById(int id);

    UserDTO readUserById(int id);

    List<UserDTO> readAllUsers();

    Optional<UserEntity> findByEmail(String email);
}
