/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 9:49 PM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.RequestVisitDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitNotFoundException;
import com.ceyentra.springboot.visitersmanager.service.VisitService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/visits")
@PreAuthorize("hasRole('ADMIN') or hasRole('RECEPTIONIST')")
public class VisitRestController {

    private final VisitService visitService;

    @Autowired
    public VisitRestController(VisitService visitService) {
        this.visitService = visitService;
    }

//    @GetMapping
//    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
//    public ResponseEntity<ResponseUtil<List<VisitDTO>>> getAllVisits() {
//
//        Optional<List<VisitDTO>> visitDTOList = Optional.ofNullable(visitService.readAllVisits());
//
//        if (visitDTOList.isEmpty()) {
//            throw new VisitNotFoundException("couldn't find visits");
//        }
//
//        return new ResponseEntity<>(new ResponseUtil<>(
//                HttpStatus.OK.value(), "successfully retrieved visits",
//                visitDTOList.get()),
//                HttpStatus.OK);
//    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<List<VisitDTO>>>
    getAllVisitsByDates(@RequestParam("start")Long start,@RequestParam("end")Long end) {

        List<VisitDTO> visitDTOS;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        boolean isStartDatePresent = start!=0;
        boolean isEndDatePresent = end!=0;

        if(isStartDatePresent&&isEndDatePresent){

            visitDTOS = visitService.findVisitsByBetweenDays(
                    dateFormat.format(new Date(start)),
                    dateFormat.format(new Date(end)),
                    EntityDbStatus.ACTIVE);
        }
        else if(isEndDatePresent){

            visitDTOS = visitService.findVisitsUntilGivenDate(
                    dateFormat.format(new Date(end)),
                    EntityDbStatus.ACTIVE);

        }else if(isStartDatePresent){

            visitDTOS = visitService.findVisitsByBetweenDays(
                    dateFormat.format(new Date(start)),
                    dateFormat.format(new Date(System.currentTimeMillis())),
                    EntityDbStatus.ACTIVE
            );

        }else{
            visitDTOS = visitService.findAllVisitsByDbStatus(EntityDbStatus.ACTIVE);
        }

        if(visitDTOS==null){
            throw new VisitNotFoundException("couldn't find visits:(");
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(), "successfully retrieved visits",
                visitDTOS),
                HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<VisitDTO>> getVisitById(@PathVariable int id) {

        Optional<VisitDTO> optionalVisitDTO = Optional.ofNullable(visitService.readVisitById(id));

        if (optionalVisitDTO.isEmpty()) {
            throw new VisitNotFoundException("couldn't find visit - " + id);
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(), "successfully retrieved visit -" + id,
                optionalVisitDTO.get()),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') or hasAuthority('receptionist:create')")
    public ResponseEntity<ResponseUtil<String>> addVisit(@RequestBody RequestVisitDTO requestVisitDTO) {

        System.out.println(requestVisitDTO);
        visitService.saveVisit(requestVisitDTO);

        return new ResponseEntity<>(
                new ResponseUtil<>(
                        HttpStatus.CREATED.value(),
                        "successfully saved visit details",
                        "success"
                ),
                HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('receptionist:update')")
    public ResponseEntity<ResponseUtil<String>> updateVisit(@RequestBody RequestVisitDTO requestVisitDTO) {

        System.out.println(requestVisitDTO);
        visitService.updateVisitById(requestVisitDTO);

        return new ResponseEntity<>(
                new ResponseUtil<>(
                        HttpStatus.OK.value(),
                        "successfully updated visit details",
                        "updated visit -"+requestVisitDTO.getVisitId()
                ),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteVisitById(@PathVariable int id){

        int count = visitService.updateVisitDbStatusById(EntityDbStatus.DELETED,id);

        if(count<=0){
            throw new VisitNotFoundException("couldn't find visit - "+id);
        }

        return new ResponseEntity<>(
                new ResponseUtil<>(
                        HttpStatus.OK.value(),
                        "successfully deleted visit",
                        "deleted visit - "+id
                ),
                HttpStatus.OK);
    }
}