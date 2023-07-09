package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;

import java.util.List;

public interface VisitorService {

    List<VisitorDTO> readAllVisitors();

    VisitorDTO saveVisitor(VisitorDTO visitorDTO);

    VisitorDTO updateVisitor(VisitorDTO visitorDTO);

    String deleteVisitorById(int id);
}
