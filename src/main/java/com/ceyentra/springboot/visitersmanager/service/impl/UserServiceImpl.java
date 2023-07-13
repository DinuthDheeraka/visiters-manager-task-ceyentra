/**
 * @author :  Dinuth Dheeraka
 * Created : 7/13/2023 9:38 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dto.SystemUserDTO;
import com.ceyentra.springboot.visitersmanager.dto.UserDTO;
import com.ceyentra.springboot.visitersmanager.entity.SystemUserEntity;
import com.ceyentra.springboot.visitersmanager.entity.UserEntity;
import com.ceyentra.springboot.visitersmanager.repository.UserRepository;
import com.ceyentra.springboot.visitersmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        userDTO.setId(0);
        UserEntity save = userRepository.save(modelMapper.map(userDTO, UserEntity.class));
        return modelMapper.map(save,UserDTO.class);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserEntity save = userRepository.save(modelMapper.map(userDTO, UserEntity.class));
        return modelMapper.map(save,UserDTO.class);
    }

    @Override
    public String deleteUserById(int id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if(byId.isPresent()){
            userRepository.deleteById(id);
            return  "user "+id+" deleted";
        }
        return null;
    }

    @Override
    public UserDTO readUserById(int id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        if(byId.isPresent()){
            return modelMapper.map(byId,UserDTO.class);
        }
        return null;
    }

    @Override
    public List<UserDTO> readAllUsers() {

        Optional<List<UserEntity>> optionalUserEntities = Optional.ofNullable(
                userRepository.findAll());

        if(optionalUserEntities.isPresent()){
            return modelMapper.map(optionalUserEntities.get(),
                    new TypeToken<ArrayList<UserDTO>>() {
                    }.getType());
        }

        return null;
    }
}
