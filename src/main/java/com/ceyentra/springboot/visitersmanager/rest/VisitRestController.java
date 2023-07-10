/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 9:49 PM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.entity.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.HttpRequestVisitDTO;
import com.ceyentra.springboot.visitersmanager.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visit")
public class VisitRestController {

    private final VisitService visitService;


    @Autowired
    public VisitRestController(VisitService visitService) {

        this.visitService = visitService;
    }

    @GetMapping
    public List<VisitDTO> getAllVisits() {
        return visitService.readAllVisits();
    }

    @GetMapping("/{id}")
    public VisitDTO getVisitById(@PathVariable int id) {
        return visitService.readVisitById(id);
    }

    @PostMapping
    public VisitDTO addVisit(@RequestBody HttpRequestVisitDTO requestVisitDTO) {
        visitService.saveVisit(requestVisitDTO);
        return null;
    }

    @PutMapping
    public VisitDTO updateVisit(@RequestBody HttpRequestVisitDTO requestVisitDTO) {
        visitService.updateVisitById(requestVisitDTO);
        return null;
    }
}
