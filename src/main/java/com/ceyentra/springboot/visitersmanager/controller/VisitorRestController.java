/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 6:52 PM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/visitors")
@PreAuthorize("hasRole('ADMIN') or hasRole('RECEPTIONIST')")
@RequiredArgsConstructor
public class VisitorRestController {

    private final VisitorService visitorService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<VisitorDTO>>> getAllVisitors() {

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(), "Successfully Retrieved Visitor Details.",
                visitorService.findVisitorsByDbStatus(EntityDbStatus.ACTIVE)),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> getVisitorById(@PathVariable int id) {

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Retrieved Visitor Details successfully for Associate ID - %d.", id),
                visitorService.readVisitorById(id)),
                HttpStatus.OK);
    }

    @GetMapping("/nic")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> getVisitorByNic(@RequestParam("nic") String nic) {

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Successfully Retrieved Visitor Details for Associate NIC - %s", nic),
                visitorService.readVisitorByNic(nic)),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') or hasAuthority('receptionist:create')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> addVisitor(@RequestBody VisitorDTO visitorDTO) {

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.CREATED.value(),
                "Successfully Saved Visitor Details.",
                visitorService.saveVisitor(visitorDTO)),
                HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('receptionist:update')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> updateVisitor(@RequestBody VisitorDTO visitorDTO) {

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Successfully Updated Visitor Details with Associate ID - %d.", visitorDTO.getVisitorId()),
                visitorService.updateVisitor(visitorDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete') or hasAuthority('receptionist:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteVisitor(@PathVariable int id) {

        visitorService.updateVisitorDbStatusById(EntityDbStatus.DELETED, id);

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "Deleted Visitor Successfully",
                String.format("Deleted Visitor with Associate ID - %d", id)),
                HttpStatus.OK);
    }

    @GetMapping("/visits/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<VisitDTO>>> getVisitsById(@PathVariable int id) {

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Retrieved Visits Associate with Visitor ID - %d.", id),
                visitorService.readAllVisitsByVisitorId(id)),
                HttpStatus.OK);
    }

}
