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
@CrossOrigin
@RequestMapping("/visitor_cards")
public class VisitorCardRestController {

    private VisitorCardService visitorCardService;

    public VisitorCardRestController(VisitorCardService visitorCardService) {
        this.visitorCardService = visitorCardService;
    }

    @GetMapping
    public List<VisitorCardDTO> getAllVisitorCard(){
        return visitorCardService.readAllVisitorCard();
    }

    @GetMapping("/v2/{id}")
    public VisitorCardDTO getVisitorCardById(@PathVariable int id){
        return visitorCardService.readVisitorCardById(id);
    }

    @GetMapping("/v2/card_status/{status}")
    public List<VisitorCardDTO> getVisitorCardsByStatus(@PathVariable VisitorCardStatus status){
        return visitorCardService.readVisitorCardByStatus(status);
    }

    @PutMapping("/v1")
    public VisitorCardDTO updateVisitorCard(@RequestBody VisitorCardDTO visitorCardDTO){
        return visitorCardService.updateVisitorCard(visitorCardDTO);
    }

    @PostMapping("/v1")
    public VisitorCardDTO addVisitorCard(@RequestBody VisitorCardDTO visitorCardDTO){
        return visitorCardService.saveVisitorCard(visitorCardDTO);
    }

    @DeleteMapping("/v1/{id}")
    public String deleteVisitorCardById(@PathVariable int id){
        return visitorCardService.deleteVisitorCardBYId(id);
    }
}
