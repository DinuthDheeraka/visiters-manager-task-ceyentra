package com.ceyentra.springboot.visitersmanager.service;

import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.entity.Visitor;

import java.util.List;

public interface VisitorService {

    List<VisitorDTO> readAllVisitors();
}
