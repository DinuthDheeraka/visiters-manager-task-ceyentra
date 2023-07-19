/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 10:20 PM
 */
package com.ceyentra.springboot.visitersmanager.service.impl;

import com.ceyentra.springboot.visitersmanager.dto.FloorDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.RequestVisitDTO;
import com.ceyentra.springboot.visitersmanager.entity.VisitEntity;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.enums.VisitStatus;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitException;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorCardException;
import com.ceyentra.springboot.visitersmanager.repository.VisitRepository;
import com.ceyentra.springboot.visitersmanager.service.FloorService;
import com.ceyentra.springboot.visitersmanager.service.VisitService;
import com.ceyentra.springboot.visitersmanager.service.VisitorCardService;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import com.ceyentra.springboot.visitersmanager.util.convert.CustomConvertor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitDAO;

    private final VisitorCardService visitorCardService;

    private final FloorService floorService;

    private final VisitorService visitorService;

    private final ModelMapper modelMapper;

    private final CustomConvertor<VisitEntity, VisitDTO> convertor;

    @Override
    public String saveVisit(RequestVisitDTO requestVisitDTO) {

        try {

            //check visitor if exists
            visitorService.readVisitorById(requestVisitDTO.getVisitorId());

            //check floor if exists
            floorService.readFloorById(requestVisitDTO.getFloorId());

            //check card if exists
            visitorCardService.readVisitorCardById(requestVisitDTO.getVisitorCardId());

            //check card availability
            if (visitorCardService.findVisitorCardStatusByCardId(requestVisitDTO.getVisitorCardId()) == VisitorCardStatus.IN_USE) {
                throw new VisitorCardException(String.format("Selected Visitor Card %d Already in use.", requestVisitDTO.getVisitorCardId()));
            }

            //add visit
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

            visitDAO.saveVisit(
                    requestVisitDTO.getVisitorId(), requestVisitDTO.getVisitorCardId(),
                    requestVisitDTO.getFloorId(), LocalDate.now(),
                    LocalTime.parse(timeFormat.format(LocalTime.now())), requestVisitDTO.getCheckOutTime(),
                    requestVisitDTO.getReason(), VisitStatus.CHECKED_IN.name()
            );

            //update card status
            VisitorCardDTO visitorCardDTO = visitorCardService.readVisitorCardById(requestVisitDTO.getVisitorCardId());
            visitorCardDTO.setVisitorCardStatus(VisitorCardStatus.IN_USE);
            visitorCardService.updateVisitorCard(visitorCardDTO);

            return "saved visit successfully";

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public VisitDTO readVisitById(int id) {

        try {

            Optional<VisitEntity> byId = visitDAO.findById(id);

            if (byId.isEmpty() || byId.get().getDbStatus() == EntityDbStatus.DELETED) {
                throw new VisitException(String.format("Couldn't find Visit Details with Associate ID - %d.", id));
            }

            VisitEntity visit = byId.get();

            //visit dto
            VisitDTO visitDTO = new VisitDTO(
                    visit.getVisitId(), visit.getCheckInDate(),
                    visit.getCheckInTime(), visit.getCheckOutTime(),
                    visit.getReason(), visit.getVisitStatus());

            //set db status
            visitDTO.setDbStatus(visit.getDbStatus());

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

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateVisitById(RequestVisitDTO requestVisitDTO) {

        try {

            //check visit if exists
            VisitDTO visitDTO = readVisitById(requestVisitDTO.getVisitId());

            //check visitor if exists
            if(requestVisitDTO.getVisitorId()!=0){
                visitorService.readVisitorById(requestVisitDTO.getVisitorId());
            }

            //check floor if exists
            if(requestVisitDTO.getFloorId()!=0){
                floorService.readFloorById(requestVisitDTO.getFloorId());
            }

            //check card if exists
            if(requestVisitDTO.getVisitorCardId()!=0){
                visitorCardService.readVisitorCardById(requestVisitDTO.getVisitorCardId());
            }

            //update card status and checked out time
            if (requestVisitDTO.getVisitStatus() == VisitStatus.CHECKED_OUT) {

                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

                //set checkout time
                visitDTO.setCheckOutTime(LocalTime.parse(timeFormat.format(LocalTime.now())));

                //update card
                VisitorCardDTO visitorCardDTO = visitorCardService.readVisitorCardById(visitDTO.getVisitorCard().getCardId());
                visitorCardDTO.setVisitorCardStatus(VisitorCardStatus.NOT_IN_USE);
                visitorCardService.updateVisitorCard(visitorCardDTO);
            }

            //update visit
            visitDAO.patcher(

                    requestVisitDTO.getVisitId(),

                    requestVisitDTO.getVisitorId() == 0 ?
                            visitDTO.getVisitor().getVisitorId() : requestVisitDTO.getVisitorId(),

                    requestVisitDTO.getVisitorCardId() == 0 ?
                            visitDTO.getVisitorCard().getCardId() : requestVisitDTO.getVisitorCardId(),

                    requestVisitDTO.getFloorId() == 0 ?
                            visitDTO.getFloor().getFloorId() : requestVisitDTO.getFloorId(),

                    requestVisitDTO.getCheckInDate() == null ?
                            visitDTO.getCheckInDate() : requestVisitDTO.getCheckInDate(),

                    requestVisitDTO.getCheckInTime() == null ?
                            visitDTO.getCheckInTime() : requestVisitDTO.getCheckInTime(),

                    requestVisitDTO.getCheckOutTime() == null ?
                            visitDTO.getCheckOutTime() : requestVisitDTO.getCheckOutTime(),

                    requestVisitDTO.getReason() == null ?
                            visitDTO.getReason() : requestVisitDTO.getReason(),

                    requestVisitDTO.getVisitStatus() == null ?
                            visitDTO.getVisitStatus().name() : requestVisitDTO.getVisitStatus().name()
            );

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<VisitDTO> findVisitsByBetweenDays(Long start, Long end, EntityDbStatus dbStatus) {

        try {
            Date startDate;
            Date endDate;
            try {
                startDate = new Date(start);
                endDate = new Date(end);

            } catch (IllegalArgumentException | ArithmeticException exception) {
                throw new RuntimeException("Unable to process.Invalid value for start Date or end Date.");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            return convertor.convert(visitDAO.findVisitsByDateRange(dateFormat.format(startDate), dateFormat.format(endDate), dbStatus.name()));

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<VisitDTO> findVisitsUntilGivenDate(Long end, EntityDbStatus entityDbStatus) {

        try {

            Date endDate;

            try {
                endDate = new Date(end);

            } catch (IllegalArgumentException | ArithmeticException exception) {
                throw new RuntimeException("Unable to process.Invalid value for end Date.");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            List<VisitEntity> visits = visitDAO.findVisitsUntilGivenDate(dateFormat.format(endDate), entityDbStatus.name());

            return convertor.convert(visits);

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<VisitDTO> findAllVisitsByDbStatus(EntityDbStatus dbStatus) {

        try {

            return convertor.convert(visitDAO.findAllVisitsByDbStatus(dbStatus.name()));

        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public int updateVisitDbStatusById(EntityDbStatus status, int id) {

        try {

            VisitDTO visitDTO = readVisitById(id);

            if (status == EntityDbStatus.DELETED) {
                visitorCardService.updateVisitorCardStatusById(VisitorCardStatus.NOT_IN_USE.name(), visitDTO.getVisitorCard().getCardId());
            }

            return visitDAO.updateVisitDbStatusById(status.name(), id);

        } catch (Exception e) {
            throw e;
        }
    }

}
