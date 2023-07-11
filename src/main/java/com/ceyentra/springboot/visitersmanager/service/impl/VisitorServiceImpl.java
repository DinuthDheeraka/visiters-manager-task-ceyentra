/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 7:56 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dao.VisitorDAO;
import com.ceyentra.springboot.visitersmanager.dto.entity.FloorDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.entity.Visit;
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

    private final VisitorDAO visitorDAO;

    private final ModelMapper modelMapper;

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
        return modelMapper.map(save, VisitorDTO.class);
    }

    @Override
    public String deleteVisitorById(int id) {
        Optional<Visitor> byId = visitorDAO.findById(id);
        if (byId.isPresent()) {
            visitorDAO.deleteById(id);
            return "deleted visitor - " + id;
        }
        return "unable to delete.visitor not found";
    }

    @Override
    public VisitorDTO readVisitorById(int id) {
        Optional<Visitor> byId = visitorDAO.findById(id);
        return modelMapper.map(byId.get(), VisitorDTO.class);
    }

    @Override
    public List<VisitDTO> readAllVisitsByVisitorId(int id) {

        List<VisitDTO> visitDTOS = new ArrayList();
        for (Visit visit : visitorDAO.findVisitsByVisitorId(id).getVisitList()) {

            //visitDTO
            VisitDTO visitDTO = new VisitDTO(visit.getVisitId(), visit.getCheckInDate(),
                    visit.getCheckInTime(), visit.getCheckOutTime(),
                    visit.getReason(), visit.getVisitStatus());

            //visitorDTO
            VisitorDTO visitorDTO = modelMapper.map(visit.getVisitor(), VisitorDTO.class);

            //visitorCardDTO
            VisitorCardDTO visitorCardDTO = modelMapper.map(visit.getVisitorCard(), VisitorCardDTO.class);

            //floorDTO
            FloorDTO floorDTO = modelMapper.map(visit.getFloor(), FloorDTO.class);

            visitDTO.setVisitor(visitorDTO);
            visitDTO.setVisitorCard(visitorCardDTO);
            visitDTO.setFloor(floorDTO);

            visitDTOS.add(visitDTO);
        }
        return visitDTOS;
    }
}
