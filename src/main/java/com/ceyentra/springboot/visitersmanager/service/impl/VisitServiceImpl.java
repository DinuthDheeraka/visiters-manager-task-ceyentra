/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 10:20 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.exceptions.VisitNotFoundException;
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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitDAO;

    private final VisitorCardService visitorCardService;

    private final ModelMapper modelMapper;

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

        System.out.println(requestVisitDTO);
        //add visit
         visitDAO.saveVisit(
                requestVisitDTO.getVisitorId(), requestVisitDTO.getVisitorCardId(),
                requestVisitDTO.getFloorId(), requestVisitDTO.getCheckInDate(),
                requestVisitDTO.getCheckInTime(), requestVisitDTO.getCheckOutTime(),
                requestVisitDTO.getReason(), VisitStatus.CHECKED_IN.name()
        );

         //check card availability
        if(visitorCardService
                .findVisitorCardStatusByCardId
                        (requestVisitDTO.getVisitorCardId())==VisitorCardStatus.IN_USE){
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

        Optional<VisitDTO> optional = Optional.ofNullable(readVisitById(requestVisitDTO.getVisitId()));

        if(optional.isEmpty()){
            throw  new VisitNotFoundException("couldn't find visit");
        }

        VisitDTO visitDTO = optional.get();

        visitDAO.patcher(
                requestVisitDTO.getVisitId(),

                requestVisitDTO.getVisitorId()==0?
                        visitDTO.getVisitor().getVisitorId():requestVisitDTO.getVisitorId(),

                requestVisitDTO.getVisitorCardId()==0?
                        visitDTO.getVisitorCard().getCardId(): requestVisitDTO.getVisitorCardId(),

                requestVisitDTO.getFloorId()==0?
                        visitDTO.getFloor().getFloorId(): requestVisitDTO.getFloorId(),

                requestVisitDTO.getCheckInDate()==null?
                        visitDTO.getCheckInDate():requestVisitDTO.getCheckInDate(),

                requestVisitDTO.getCheckInTime()==null?
                        visitDTO.getCheckInTime():requestVisitDTO.getCheckInTime(),

                requestVisitDTO.getCheckOutTime()==null?
                        visitDTO.getCheckOutTime():requestVisitDTO.getCheckOutTime(),

                requestVisitDTO.getReason()==null?
                        visitDTO.getReason():requestVisitDTO.getReason(),

                requestVisitDTO.getVisitStatus()==null?
                        visitDTO.getVisitStatus().name():requestVisitDTO.getVisitStatus().name()
        );

        //update card status
        if (requestVisitDTO.getVisitStatus() == VisitStatus.CHECKED_OUT) {
            VisitorCardDTO visitorCardDTO = visitorCardService.readVisitorCardById(visitDTO.getVisitorCard().getCardId());
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

    @Override
    public VisitDTO updateVisitStatusById(int id, VisitStatus visitStatus) {
        visitDAO.updateVisitStatusById(visitStatus.name(), id);
        return null;
    }
}
