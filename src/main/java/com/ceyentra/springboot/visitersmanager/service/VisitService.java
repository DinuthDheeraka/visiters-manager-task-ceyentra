package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.HttpRequestVisitDTO;

import java.util.List;

public interface VisitService {

    List<VisitDTO> readAllVisits();

    String saveVisit(HttpRequestVisitDTO requestVisitDTO);

    VisitDTO readVisitById(int id);

    VisitDTO updateVisitById(HttpRequestVisitDTO httpRequestVisitDTO);

    String deleteVisitById(int id);
}
