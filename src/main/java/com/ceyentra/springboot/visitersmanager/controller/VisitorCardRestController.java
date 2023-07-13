/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 2:07 AM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;
import com.ceyentra.springboot.visitersmanager.service.VisitorCardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("v1/visitor_cards")
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
    public List<VisitorCardDTO> getVisitorCardsByStatus(@PathVariable VisitorCardStatus status){
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
