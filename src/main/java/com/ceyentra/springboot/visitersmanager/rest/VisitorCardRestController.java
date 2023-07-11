/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 2:07 AM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.enums.entity.visitorcard.VisitorCardStatus;
import com.ceyentra.springboot.visitersmanager.service.VisitorCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visitor_card")
public class VisitorCardRestController {

    private VisitorCardService visitorCardService;

    public VisitorCardRestController(VisitorCardService visitorCardService) {
        this.visitorCardService = visitorCardService;
    }

    @GetMapping
    public List<VisitorCardDTO> getAllVisitorCard(){
        return visitorCardService.readAllVisitorCard();
    }

    @GetMapping("/{id}")
    public VisitorCardDTO getVisitorCardById(@PathVariable int id){
        return visitorCardService.readVisitorCardById(id);
    }

    @GetMapping("/card_status/{status}")
    public List<VisitorCardDTO> getVisitorCardById(@PathVariable VisitorCardStatus status){
        return visitorCardService.readVisitorCardByStatus(status);
    }

    @PutMapping
    public VisitorCardDTO updateVisitorCard(@RequestBody VisitorCardDTO visitorCardDTO){
        return visitorCardService.updateVisitorCard(visitorCardDTO);
    }

    @PostMapping
    public VisitorCardDTO addVisitorCard(@RequestBody VisitorCardDTO visitorCardDTO){
        return visitorCardService.saveVisitorCard(visitorCardDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteVisitorCardById(@PathVariable int id){
        return visitorCardService.deleteVisitorCardBYId(id);
    }
}
