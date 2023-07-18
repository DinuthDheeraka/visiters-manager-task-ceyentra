/**
 * @author :  Dinuth Dheeraka
 * Created : 7/10/2023 2:07 AM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.VisitorCardDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.enums.VisitorCardStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorCardException;
import com.ceyentra.springboot.visitersmanager.service.VisitorCardService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class VisitorCardRestController {

    private final VisitorCardService visitorCardService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<VisitorCardDTO>>> getAllVisitorCards() {

        Optional<List<VisitorCardDTO>> optional = Optional
                .ofNullable(visitorCardService
                        .findVisitorCardsByDbStatus(EntityDbStatus.ACTIVE));

        if (optional.isEmpty()) {
            throw new VisitorCardException("Couldn't find Visitor Cards Details.");
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "Successfully Retrieved Visitor Cards Details",
                optional.get()),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<VisitorCardDTO>> getVisitorCardById(@PathVariable int id) {

        Optional<VisitorCardDTO> optional = Optional.ofNullable(visitorCardService.readVisitorCardById(id));

        if (optional.isEmpty() || optional.get().getDbStatus() == EntityDbStatus.DELETED) {
            throw new VisitorCardException(String.format("Couldn't find Visitor Card with Associate ID - %d", id));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Successfully Retrieved Visitor Card details for Associate ID - %d", id),
                optional.get()),
                HttpStatus.OK);
    }

    @GetMapping("/card_status/{status}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<VisitorCardDTO>>> getVisitorCardsByStatus(@PathVariable VisitorCardStatus status) {

        Optional<List<VisitorCardDTO>> optional = Optional.ofNullable(visitorCardService.readVisitorCardByStatus(status));

        if (optional.isEmpty()) {
            throw new VisitorCardException(String.format("Couldn't find Visitor Cards with Status - %s", status));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Successfully Retrieved Visitor Cards with status - %s", status),
                optional.get()),
                HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ResponseUtil<VisitorCardDTO>> updateVisitorCard(@RequestBody VisitorCardDTO visitorCardDTO) {

        Optional<VisitorCardDTO> optional = Optional.ofNullable(visitorCardService.updateVisitorCard(visitorCardDTO));

        if (optional.isEmpty()) {
            throw new VisitorCardException(String.format("Couldn't Update Visitor Card with Associate ID - %d.", visitorCardDTO.getCardId()));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Successfully Updated Visitor Card with Given ID - %d.", visitorCardDTO.getCardId()),
                optional.get()),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ResponseUtil<VisitorCardDTO>> addVisitorCard(@RequestBody VisitorCardDTO visitorCardDTO) {

        Optional<VisitorCardDTO> optional = Optional.ofNullable(visitorCardService.saveVisitorCard(visitorCardDTO));

        if (optional.isEmpty()) {
            throw new VisitorCardException("Couldn't save Visitor Card.");
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Successfully Saved Visitor Card with ID - %d.",optional.get().getCardId()),
                optional.get()),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteVisitorCardById(@PathVariable int id) {

        int count = visitorCardService.updateVisitorCardDbStatusById(EntityDbStatus.DELETED, id);

        if (count <= 0) {
            throw new VisitorCardException(String.format("Couldn't find Visitor Card with Associate ID - %d" ,id));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "Successfully Deleted Visitor Card.",
                String.format("Deleted Visitor Card with ID - %d.",id)),
                HttpStatus.OK);
    }
}
