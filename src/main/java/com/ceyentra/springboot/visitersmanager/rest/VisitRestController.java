/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 9:49 PM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.entity.FloorDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.HttpRequestVisitDTO;
import com.ceyentra.springboot.visitersmanager.entity.Visitor;
import com.ceyentra.springboot.visitersmanager.service.FloorService;
import com.ceyentra.springboot.visitersmanager.service.VisitService;
import com.ceyentra.springboot.visitersmanager.service.VisitorCardService;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitRestController {

    private VisitService visitService;


    @Autowired
    public VisitRestController(VisitService visitService) {

        this.visitService = visitService;
    }

    @GetMapping
    public List<VisitDTO> getAllVisits(){

        return visitService.readAllVisits();
    }

    @PostMapping
    public VisitDTO addVisit(@RequestBody HttpRequestVisitDTO requestVisitDTO){
        visitService.saveVisit(requestVisitDTO);
        return null;
    }
}
