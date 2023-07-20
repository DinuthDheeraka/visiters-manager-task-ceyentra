/**
 * @author :  Dinuth Dheeraka
 * Created : 7/13/2023 9:38 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dto.UserDTO;
import com.ceyentra.springboot.visitersmanager.entity.UserEntity;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.UserException;
import com.ceyentra.springboot.visitersmanager.repository.UserRepository;
import com.ceyentra.springboot.visitersmanager.service.TokenService;
import com.ceyentra.springboot.visitersmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    private final TokenService tokenService;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {

        try {

            Optional<UserEntity> byEmail = findByEmail(userDTO.getEmail());

            if (byEmail.isPresent()) {
                throw new UserException(String.format("User with this Email - %s Already Exists.", userDTO.getEmail()));
            }

            userDTO.setId(0);
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return modelMapper.map(userRepository.save(modelMapper.map(userDTO,
                    UserEntity.class)),
                    UserDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {

        try {

            //check user if exists
            UserDTO currentUserDTO = readUserById(userDTO.getId());

            //check user if exists with given email
            if (userDTO.getEmail() != null && !(currentUserDTO.getEmail().equals(userDTO.getEmail()))) {

                Optional<UserEntity> byEmail = findByEmail(userDTO.getEmail());

                if (byEmail.isPresent()) {
                    throw new UserException(String.format("User with this Email - %s Already Exists.", userDTO.getEmail()));
                }
            }

            currentUserDTO.setFirstname(userDTO.getFirstname() == null ?
                    currentUserDTO.getFirstname() : userDTO.getFirstname());

            currentUserDTO.setLastname(userDTO.getLastname() == null ?
                    currentUserDTO.getLastname() : userDTO.getLastname());

            currentUserDTO.setPassword(userDTO.getPassword() == null ?
                    currentUserDTO.getPassword() : passwordEncoder.encode(userDTO.getPassword()));

            currentUserDTO.setEmail(userDTO.getEmail() == null ?
                    currentUserDTO.getEmail() : userDTO.getEmail());

            currentUserDTO.setRole(userDTO.getRole() == null ?
                    currentUserDTO.getRole() : userDTO.getRole());

            return modelMapper.map(userRepository.save(modelMapper.map(userDTO,
                    UserEntity.class)),
                    UserDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteUserById(int id) {

        try {
            readUserById(id);
            tokenService.deleteTokenByUserId(id);
            userRepository.deleteById(id);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public UserDTO readUserById(int id) {

        try {

            Optional<UserEntity> byId = userRepository.findById(id);

            if (byId.isEmpty()) {
                throw new UserException(String.format("Unable to find User with Associate ID - %d", id));
            }

            return modelMapper.map(byId, UserDTO.class);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<UserDTO> readAllUsersByDbStatus(EntityDbStatus dbStatus) {

        try {
            return modelMapper.map(userRepository.findAll(),
                    new TypeToken<ArrayList<UserDTO>>() {
                    }.getType());

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {

        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            throw e;
        }
    }
}
