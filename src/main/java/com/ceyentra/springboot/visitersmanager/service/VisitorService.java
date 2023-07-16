package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;

import java.util.List;

public interface VisitorService {

    List<VisitorDTO> readAllVisitors();

    VisitorDTO saveVisitor(VisitorDTO visitorDTO);

    VisitorDTO updateVisitor(VisitorDTO visitorDTO);

    String deleteVisitorById(int id);

    VisitorDTO readVisitorById(int id);

    VisitorDTO readVisitorByNic(String nic);

    List<VisitDTO> readAllVisitsByVisitorId(int id);

    int updateVisitorDbStatusById(EntityDbStatus status,int id);

    List<VisitorDTO> findVisitorsByDbStatus(EntityDbStatus status);
}
