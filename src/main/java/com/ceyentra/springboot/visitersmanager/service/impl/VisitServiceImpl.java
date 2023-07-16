/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 10:20 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
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
import com.ceyentra.springboot.visitersmanager.util.convert.CustomConvertor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitDAO;

    private final VisitorCardService visitorCardService;

    private final ModelMapper modelMapper;

    private final CustomConvertor convertor;

    @Override
    public List<VisitDTO> readAllVisits() {

        Optional<List<VisitEntity>> visitDTOList = Optional.ofNullable(visitDAO.findAll());

        if (visitDTOList.isPresent()) {

            return convertor.convert(visitDTOList.get());
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
            visitorCardService.updateVisitorCardStatusById(VisitorCardStatus.NOT_IN_USE.name(),byId.get().getVisitorCard().getCardId());
            visitDAO.deleteById(id);
            return "deleted visit";
        }
        return null;
    }

    @Override
    public List<VisitDTO> findVisitsByBetweenDays(String startDate, String endDate ,EntityDbStatus dbStatus) {

        System.out.println(startDate+" "+endDate);

        Optional<List<VisitEntity>> visits = Optional.ofNullable(visitDAO.findVisitsByDateRange(startDate, endDate,dbStatus.name()));

        if(visits.isPresent()){
            return convertor.convert(visits.get());
        }

        return null;

    }

    @Override
    public List<VisitDTO> findVisitsUntilGivenDate(String endDate,EntityDbStatus entityDbStatus) {

        System.out.println(endDate);

        Optional<List<VisitEntity>> visits = Optional.ofNullable(visitDAO.findVisitsUntilGivenDate(endDate,entityDbStatus.name()));

        if(visits.isPresent()){
            return convertor.convert(visits.get());
        }

        return null;
    }

    @Override
    public List<VisitDTO> findAllVisitsByDbStatus(EntityDbStatus dbStatus) {

        Optional<List<VisitEntity>> optional = Optional.ofNullable(visitDAO.findAllVisitsByDbStatus(dbStatus.name()));

        if(optional.isPresent()){
            return convertor.convert(optional.get());
        }

        return null;
    }

    @Override
    public int updateVisitDbStatusById(EntityDbStatus status, int id) {

        VisitDTO visitDTO = readVisitById(id);

        visitorCardService.updateVisitorCardStatusById(VisitorCardStatus.NOT_IN_USE.name(),visitDTO.getVisitorCard().getCardId());

        return visitDAO.updateVisitDbStatusById(status.name(),id);
    }

}
