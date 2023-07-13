/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 2:07 AM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorCardNotFoundException;
import com.ceyentra.springboot.visitersmanager.service.VisitorCardService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/visitor_cards")
@PreAuthorize("hasRole('ADMIN') or hasRole('RECEPTIONIST')")
public class VisitorCardRestController {

    private final VisitorCardService visitorCardService;

    public VisitorCardRestController(VisitorCardService visitorCardService) {
        this.visitorCardService = visitorCardService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<VisitorCardDTO>>> getAllVisitorCard() {

        Optional<List<VisitorCardDTO>> optional = Optional.ofNullable(visitorCardService.readAllVisitorCard());

        if (optional.isEmpty()) {
            throw new VisitorCardNotFoundException("couldn't find visitor cards");
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "successfully retrieved visitor cards",
                optional.get()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<VisitorCardDTO>> getVisitorCardById(@PathVariable int id) {

        Optional<VisitorCardDTO> optional = Optional.ofNullable(visitorCardService.readVisitorCardById(id));

        if (optional.isEmpty()) {
            throw new VisitorCardNotFoundException("couldn't find visitor card - "+id);
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "successfully retrieved visitor card -"+id,
                optional.get()),
                HttpStatus.OK);
    }

    @GetMapping("/card_status/{status}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<VisitorCardDTO>>> getVisitorCardsByStatus(@PathVariable VisitorCardStatus status) {

        Optional<List<VisitorCardDTO>> optional = Optional.ofNullable(visitorCardService.readVisitorCardByStatus(status));

        if (optional.isEmpty()) {
            throw new VisitorCardNotFoundException("couldn't find visitor cards with status - "+status);
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "successfully retrieved visitor cards with status - "+status,
                optional.get()),
                HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ResponseUtil<VisitorCardDTO>> updateVisitorCard(@RequestBody VisitorCardDTO visitorCardDTO) {

        Optional<VisitorCardDTO> optional = Optional.ofNullable(visitorCardService.updateVisitorCard(visitorCardDTO));

        if (optional.isEmpty()) {
            throw new VisitorCardNotFoundException("couldn't update visitor cards - "+visitorCardDTO.getCardId());
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "successfully updated visitor card - "+visitorCardDTO.getCardId(),
                optional.get()),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ResponseUtil<VisitorCardDTO>> addVisitorCard(@RequestBody VisitorCardDTO visitorCardDTO) {

        Optional<VisitorCardDTO> optional = Optional.ofNullable(visitorCardService.saveVisitorCard(visitorCardDTO));

        if (optional.isEmpty()) {
            throw new VisitorCardNotFoundException("couldn't save visitor card");
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "successfully saved visitor card - "+optional.get().getCardId(),
                optional.get()),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteVisitorCardById(@PathVariable int id) {

        Optional<String> optional = Optional.ofNullable(visitorCardService.deleteVisitorCardBYId(id));

        if (optional.isEmpty()) {
            throw new VisitorCardNotFoundException("couldn't find visitor card - "+id);
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "successfully deleted visitor card",
                optional.get()),
                HttpStatus.OK);
    }
}
