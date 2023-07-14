/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 10:20 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.repository.VisitRepository;
import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.RequestVisitDTO;
import com.ceyentra.springboot.visitersmanager.entity.VisitEntity;
import com.ceyentra.springboot.visitersmanager.enums.VisitStatus;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorCardInUseException;
import com.ceyentra.springboot.visitersmanager.service.FloorService;
import com.ceyentra.springboot.visitersmanager.service.VisitService;
import com.ceyentra.springboot.visitersmanager.service.VisitorCardService;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitDAO;

    private final VisitorCardService visitorCardService;

    private final VisitorService visitorService;

    private final FloorService floorService;

    private final ModelMapper modelMapper;

    @Autowired
    public VisitServiceImpl(VisitRepository visitDAO, VisitorCardService visitorCardService,
                            VisitorService visitorService, FloorService floorService,
                            ModelMapper modelMapper) {
        this.visitDAO = visitDAO;
        this.visitorCardService = visitorCardService;
        this.modelMapper = modelMapper;
        this.visitorService = visitorService;
        this.floorService = floorService;
    }

    @Override
    public List<VisitDTO> readAllVisits() {

        Optional<List<VisitEntity>> visitDTOList = Optional.ofNullable(visitDAO.findAll());

        if (visitDTOList.isPresent()) {

            List<VisitDTO> visitDTOS = new ArrayList();

            for (VisitEntity visit : visitDTOList.get()) {

                //visitDTO
                VisitDTO visitDTO = new VisitDTO(
                        visit.getVisitId(), visit.getCheckInDate(),
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
        return null;
    }

    @Override
    public String saveVisit(RequestVisitDTO requestVisitDTO) {
        //add visit
         visitDAO.saveVisit(
                requestVisitDTO.getVisitorId(), requestVisitDTO.getVisitorCardId(),
                requestVisitDTO.getFloorId(), requestVisitDTO.getCheckInDate(),
                requestVisitDTO.getCheckInTime(), requestVisitDTO.getCheckOutTime(),
                requestVisitDTO.getReason(), VisitStatus.CHECKED_IN.ordinal()
        );

         //check card availability
        if(visitorCardService.findVisitorCardStatusByCardId(
                requestVisitDTO.getVisitorCardId())==VisitorCardStatus.IN_USE){

            throw new VisitorCardInUseException("selected visitor card "+requestVisitDTO.getVisitorCardId()+" already in use");
        }

        //update card status
        VisitorCardDTO visitorCardDTO = visitorCardService.readVisitorCardById(requestVisitDTO.getVisitorCardId());
        visitorCardDTO.setVisitorCardStatus(VisitorCardStatus.IN_USE);
        visitorCardService.updateVisitorCard(visitorCardDTO);

        return "saved visit successfully";
    }

    @Override
    public VisitDTO readVisitById(int id) {

        Optional<VisitEntity> byId = visitDAO.findById(id);

        if (byId.isPresent()) {

            VisitEntity visit = byId.get();

            //visit dto
            VisitDTO visitDTO = new VisitDTO(
                    visit.getVisitId(), visit.getCheckInDate(),
                    visit.getCheckInTime(), visit.getCheckOutTime(),
                    visit.getReason(), visit.getVisitStatus());

            //visitorDTO
            VisitorDTO visitorDTO = modelMapper.map(visit.getVisitor(), VisitorDTO.class);

            //visitorCardDTO
            VisitorCardDTO visitorCardDTO = modelMapper.map(visit.getVisitorCard(), VisitorCardDTO.class);

            //floorDTO
            FloorDTO floorDTO = modelMapper.map(visit.getFloor(), FloorDTO.class);

            visitDTO.setVisitor(visitorDTO);
            visitDTO.setFloor(floorDTO);
            visitDTO.setVisitorCard(visitorCardDTO);

            return visitDTO;
        }
        return null;
    }

    @Override
    public VisitDTO updateVisitById(RequestVisitDTO requestVisitDTO) {

        visitDAO.updateVisit(
                requestVisitDTO.getVisitId(),
                requestVisitDTO.getVisitorId(), requestVisitDTO.getVisitorCardId(),
                requestVisitDTO.getFloorId(), requestVisitDTO.getCheckInDate(),
                requestVisitDTO.getCheckInTime(), requestVisitDTO.getCheckOutTime(),
                requestVisitDTO.getReason(), requestVisitDTO.getVisitStatus()
        );

        //update card status
        if (requestVisitDTO.getVisitStatus() == VisitStatus.CHECKED_OUT) {
            VisitorCardDTO visitorCardDTO = visitorCardService.readVisitorCardById(requestVisitDTO.getVisitorCardId());
            visitorCardDTO.setVisitorCardStatus(VisitorCardStatus.NOT_IN_USE);
            visitorCardService.updateVisitorCard(visitorCardDTO);
        }

        return null;
    }

    @Override
    public String deleteVisitById(int id) {
        Optional<VisitEntity> byId = visitDAO.findById(id);
        if(byId.isPresent()){
            visitDAO.deleteById(id);
            return "deleted visit";
        }
        return null;
    }
}
