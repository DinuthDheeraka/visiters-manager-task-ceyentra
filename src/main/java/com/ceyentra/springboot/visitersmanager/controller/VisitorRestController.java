/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 6:52 PM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorException;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        Optional<List<VisitorDTO>> optionalVisitorDTOS = Optional.ofNullable(visitorService.findVisitorsByDbStatus(EntityDbStatus.ACTIVE));

        if (optionalVisitorDTOS.isEmpty()) {
            throw new VisitorException("Unable to Find Visitor Details.");
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(), "Successfully Retrieved Visitor Details.",
                optionalVisitorDTOS.get()),
                HttpStatus.OK
        );

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> getVisitorById(@PathVariable int id) {

        Optional<VisitorDTO> optionalVisitorDTO = Optional.ofNullable(visitorService.readVisitorById(id));

        if (optionalVisitorDTO.isEmpty() || optionalVisitorDTO.get().getDbStatus()==EntityDbStatus.DELETED) {
            throw new VisitorException(String.format("Couldn't find Visitor with Associate ID - %d.",id));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Retrieved Visitor Details successfully for Associate ID - %d.",id),
                optionalVisitorDTO.get()),
                HttpStatus.OK);
    }


    @GetMapping("/nic")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> getVisitorByNic(@RequestParam("nic") String nic) {

        Optional<VisitorDTO> optionalVisitorDTO = Optional.ofNullable(visitorService.readVisitorByNic(nic));

        if (optionalVisitorDTO.isEmpty()) {
            throw new VisitorException(String.format("Couldn't find Visitor with Associate NIC - %s",nic));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Successfully Retrieved Visitor Details for Associate NIC - %s",nic),
                optionalVisitorDTO.get()),
                HttpStatus.OK);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') or hasAuthority('receptionist:create')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> addVisitor(@RequestBody VisitorDTO visitorDTO) {

        Optional<VisitorDTO> optional  = Optional.ofNullable(visitorService.saveVisitor(visitorDTO));

        if(optional.isEmpty()){
            throw new VisitorException("Unable to Save Visitor Details.");
        }

        System.out.println(visitorDTO);
        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.CREATED.value(),
                "Successfully Saved Visitor Details.",
                optional.get()),
                HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('receptionist:update')")
    public ResponseEntity<ResponseUtil<VisitorDTO>> updateVisitor(@RequestBody VisitorDTO visitorDTO) {

        Optional<VisitorDTO> optional  = Optional.ofNullable(visitorService.updateVisitor(visitorDTO));

        if(optional.isEmpty()){
            throw new VisitorException(String.format("Unable to Update Visitor Details for Associate ID - %d.",visitorDTO.getVisitorId()));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Successfully Updated Visitor Details with Associate ID - %d.",visitorDTO.getVisitorId()),
                visitorService.updateVisitor(visitorDTO)),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete') or hasAuthority('receptionist:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteVisitor(@PathVariable int id) {

        int count = visitorService.updateVisitorDbStatusById(EntityDbStatus.DELETED,id);

        if (count<=0) {
            throw new VisitorException(String.format("Unable to Delete.Couldn't find Visitor with Associate ID - %d." ,id));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "Deleted Visitor Successfully",
                String.format("Deleted Visitor with Associate ID - %d",id)),
                HttpStatus.OK);
    }

    @GetMapping("/visits/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<VisitDTO>>> getVisitsById(@PathVariable int id) {

        Optional<List<VisitDTO>> optional = Optional.ofNullable(visitorService.readAllVisitsByVisitorId(id));

        if(optional.isEmpty()){
            throw new VisitorException(String.format("Unable to find Visits Associate with Visitor ID - %d.",id));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Retrieved Visits Associate with Visitor ID - %d.",id),
                optional.get()),
                HttpStatus.OK);
    }


}
