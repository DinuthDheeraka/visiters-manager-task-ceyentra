/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 7:56 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dao.VisitorDAO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.entity.Visitor;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
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

    @Override
    public VisitorDTO saveVisitor(VisitorDTO visitorDTO) {
        visitorDTO.setVisitorId(0);
        Visitor save = visitorDAO.save(modelMapper.map(visitorDTO, Visitor.class));
        return modelMapper.map(save, VisitorDTO.class);
    }

    @Override
    public VisitorDTO updateVisitor(VisitorDTO visitorDTO) {
        Visitor save = visitorDAO.save(modelMapper.map(visitorDTO, Visitor.class));
        return modelMapper.map(save,VisitorDTO.class);
    }

    @Override
    public String deleteVisitorById(int id) {
        Optional<Visitor> byId = visitorDAO.findById(id);
        if(byId.isPresent()){
            visitorDAO.deleteById(id);
            return "deleted visitor - "+id;
        }
        return "unable to delete.visitor not found";
    }

    @Override
    public VisitorDTO readVisitorById(int id) {
        Optional<Visitor> byId = visitorDAO.findById(id);
        return modelMapper.map(byId.get(),VisitorDTO.class);
    }
}
