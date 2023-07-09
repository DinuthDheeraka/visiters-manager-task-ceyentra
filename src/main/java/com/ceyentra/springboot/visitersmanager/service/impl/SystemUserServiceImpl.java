/**
 * @author :  Dinuth Dheeraka
 * Created : 7/8/2023 1:44 AM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dao.SystemUserDAO;
import com.ceyentra.springboot.visitersmanager.dto.entity.SystemUserDTO;
import com.ceyentra.springboot.visitersmanager.entity.SystemUser;
import com.ceyentra.springboot.visitersmanager.service.SystemUserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SystemUserServiceImpl implements SystemUserService {

    private final SystemUserDAO systemUserDAO;

    private final ModelMapper modelMapper;

    @Autowired
    public SystemUserServiceImpl(SystemUserDAO systemUserDAO, ModelMapper modelMapper) {
        this.systemUserDAO = systemUserDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public SystemUserDTO saveSystemUser(SystemUserDTO systemUserDTO) {
        systemUserDTO.setSystemUserId(0);
        SystemUser save = systemUserDAO.save(modelMapper.map(systemUserDTO, SystemUser.class));
        return modelMapper.map(save, SystemUserDTO.class);
    }

    @Override
    public SystemUserDTO updateSystemUser(SystemUserDTO systemUserDTO) {
        return null;
    }

    @Override
    public SystemUserDTO deleteSystemUserById(int id) {
        return null;
    }

    @Override
    public SystemUserDTO readSystemUserById(int id) {
        return null;
    }

    @Override
    public List<SystemUserDTO> readAllSystemUsers() {
        return modelMapper.map(systemUserDAO.findAll(),
                new TypeToken<ArrayList<SystemUserDTO>>() {
                }.getType());
    }
}
