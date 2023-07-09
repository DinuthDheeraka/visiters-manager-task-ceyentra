/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 10:20 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dao.VisitDAO;
import com.ceyentra.springboot.visitersmanager.dto.entity.FloorDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.entity.Floor;
import com.ceyentra.springboot.visitersmanager.entity.Visit;
import com.ceyentra.springboot.visitersmanager.entity.Visitor;
import com.ceyentra.springboot.visitersmanager.entity.VisitorCard;
import com.ceyentra.springboot.visitersmanager.service.VisitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private final VisitDAO visitDAO;

    private final ModelMapper modelMapper;

    @Autowired
    public VisitServiceImpl(VisitDAO visitDAO, ModelMapper modelMapper) {
        this.visitDAO = visitDAO;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VisitDTO> readAllVisits() {
        List<VisitDTO> visitDTOS = new ArrayList();
        for(Visit visit : visitDAO.findAll()){
            VisitDTO visitDTO = new VisitDTO(visit.getVisitId(), visit.getCheckInDate(),
                    visit.getCheckInTime(),visit.getCheckOutTime(),
                    visit.getReason(),visit.getVisitStatus());

            //visitor
            Visitor visitor = visit.getVisitor();
            VisitorDTO visitorDTO = new VisitorDTO(visitor.getVisitorId(),
                    visitor.getNic(), visitor.getFirstName(), visitor.getLastName(),
                    visitor.getPhone());

            //visit card
            VisitorCard visitorCard = visit.getVisitorCard();
            VisitorCardDTO visitorCardDTO = new VisitorCardDTO(
                    visitorCard.getCardId(),visitorCard.getCardNumber(),
                    visitorCard.getCardType(),visitorCard.getVisitorCardStatus()
            );

            //floor
            Floor floor = visit.getFloor();
            FloorDTO floorDTO = new FloorDTO(
                    floor.getFloorId(), floor.getFloorNumber(), floor.getFloorName()
            );

            visitDTO.setVisitor(visitorDTO);
            visitDTO.setVisitorCard(visitorCardDTO);
            visitDTO.setFloor(floorDTO);

            visitDTOS.add(visitDTO);
        }
        return visitDTOS;
    }

    @Override
    public VisitDTO saveVisit(VisitDTO visitDTO) {
        return null;
    }
}