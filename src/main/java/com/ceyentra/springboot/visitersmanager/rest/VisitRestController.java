/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 9:49 PM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.entity.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.HttpRequestVisitDTO;
import com.ceyentra.springboot.visitersmanager.entity.Visit;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitNotFoundException;
import com.ceyentra.springboot.visitersmanager.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        Optional<List<VisitDTO>> visitDTOList = Optional.ofNullable(visitService.readAllVisits());

        if(visitDTOList.isEmpty()){
            throw new VisitNotFoundException("couldn't find visits");
        }

        return visitDTOList.get();

    }

    @GetMapping("/{id}")
    public VisitDTO getVisitById(@PathVariable int id) {

        Optional<VisitDTO> optionalVisitDTO = Optional.ofNullable(visitService.readVisitById(id));

        if(optionalVisitDTO.isEmpty()){
            throw new VisitNotFoundException("couldn't find visit - "+id);
        }

        return optionalVisitDTO.get();
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


//{
//        "visitorId":1,
//        "visitorCardId":1,
//        "floorId":1,
//        "checkInDate":"2023-07-09",
//        "checkInTime":"13:30:00",
//        "checkOutTime":"14:50:00",
//        "reason":"Business Meeting",
//        "visitStatus":"CHECKED_IN"
//        }