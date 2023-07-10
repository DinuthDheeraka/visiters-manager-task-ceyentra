package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.entity.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.HttpRequestVisitDTO;

import java.util.List;

public interface VisitService {

    List<VisitDTO> readAllVisits();

    VisitDTO saveVisit(HttpRequestVisitDTO requestVisitDTO);

    VisitDTO readVisitById(int id);
}
