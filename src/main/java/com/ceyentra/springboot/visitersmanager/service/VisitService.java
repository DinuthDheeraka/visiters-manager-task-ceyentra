package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.RequestVisitDTO;
import com.ceyentra.springboot.visitersmanager.entity.VisitEntity;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.enums.VisitStatus;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface VisitService {

    List<VisitDTO> readAllVisits();

    String saveVisit(RequestVisitDTO requestVisitDTO);

    VisitDTO readVisitById(int id);

    VisitDTO updateVisitById(RequestVisitDTO httpRequestVisitDTO);

    String deleteVisitById(int id);

    List<VisitDTO> findVisitsByBetweenDays(String startDate,String endDate,EntityDbStatus entityDbStatus);

    List<VisitDTO> findVisitsUntilGivenDate(String endDate,EntityDbStatus entityDbStatus);

    List<VisitDTO> findAllVisitsByDbStatus(EntityDbStatus dbStatus);

    int updateVisitDbStatusById(EntityDbStatus status,int id);

}
