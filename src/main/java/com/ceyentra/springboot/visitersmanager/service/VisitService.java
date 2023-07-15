package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.RequestVisitDTO;
import com.ceyentra.springboot.visitersmanager.enums.VisitStatus;

import java.util.List;

public interface VisitService {

    List<VisitDTO> readAllVisits();

    String saveVisit(RequestVisitDTO requestVisitDTO);

    VisitDTO readVisitById(int id);

    VisitDTO updateVisitById(RequestVisitDTO httpRequestVisitDTO);

    String deleteVisitById(int id);

    VisitDTO updateVisitStatusById(int id, VisitStatus visitStatus);
}
