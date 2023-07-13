/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 1:44 AM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.repository.SystemUserRepository;
import com.ceyentra.springboot.visitersmanager.dto.SystemUserDTO;
import com.ceyentra.springboot.visitersmanager.entity.SystemUserEntity;
import com.ceyentra.springboot.visitersmanager.service.SystemUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserRepository systemUserDAO;

    private final ModelMapper modelMapper;

    @Autowired
    public SystemUserServiceImpl(SystemUserRepository systemUserDAO, ModelMapper modelMapper) {
        this.systemUserDAO = systemUserDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public SystemUserDTO saveSystemUser(SystemUserDTO systemUserDTO) {
        systemUserDTO.setSystemUserId(0);
        SystemUserEntity save = systemUserDAO.save(modelMapper.map(systemUserDTO, SystemUserEntity.class));
        return modelMapper.map(save, SystemUserDTO.class);
    }

    @Override
    public SystemUserDTO updateSystemUser(SystemUserDTO systemUserDTO) {
        SystemUserEntity save = systemUserDAO.save(modelMapper.map(systemUserDTO, SystemUserEntity.class));
        return modelMapper.map(save,SystemUserDTO.class);
    }

    @Override
    public String deleteSystemUserById(int id) {

        Optional<SystemUserEntity> byId = systemUserDAO.findById(id);
        if(byId.isPresent()){
            systemUserDAO.deleteById(id);
            return "deleted user - "+id;
        }
        return null;
    }

    @Override
    public SystemUserDTO readSystemUserById(int id) {

        Optional<SystemUserEntity> byId = systemUserDAO.findById(id);

        if(byId.isPresent()){
            return modelMapper.map(byId.get(),SystemUserDTO.class);
        }

        return null;
    }

    @Override
    public List<SystemUserDTO> readAllSystemUsers() {

        Optional<List<SystemUserEntity>> optionalSystemUserDTOS = Optional.ofNullable(
                systemUserDAO.findAll());

        if(optionalSystemUserDTOS.isPresent()){
            return modelMapper.map(optionalSystemUserDTOS.get(),
                    new TypeToken<ArrayList<SystemUserDTO>>() {
                    }.getType());
        }

        return null;
    }

    @Override
    public Optional<SystemUserEntity> findByUserName(String userName) {
        return systemUserDAO.findByUserName(userName);
    }
}
