package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;

import java.util.List;

public interface VisitService {

    List<VisitDTO> readAllVisits();

    VisitDTO saveVisit(VisitDTO visitDTO);
}
