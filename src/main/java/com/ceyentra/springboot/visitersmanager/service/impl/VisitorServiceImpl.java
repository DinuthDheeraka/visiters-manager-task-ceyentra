/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 7:56 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dao.VisitorDAO;
import com.ceyentra.springboot.visitersmanager.dto.SystemUserDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VisitorServiceImpl implements VisitorService {

    private VisitorDAO visitorDAO;

    private ModelMapper modelMapper;

    @Autowired
    public VisitorServiceImpl(VisitorDAO visitorDAO, ModelMapper modelMapper) {
        this.visitorDAO = visitorDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VisitorDTO> readAllVisitors() {
        return modelMapper.map(visitorDAO.findAll(),
                new TypeToken<ArrayList<VisitorDTO>>() {
                }.getType());
    }
}
