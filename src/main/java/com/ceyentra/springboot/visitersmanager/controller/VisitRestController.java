/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 9:49 PM
 */
package com.ceyentra.springboot.visitersmanager.controller;

import com.ceyentra.springboot.visitersmanager.dto.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.request.RequestVisitDTO;
import com.ceyentra.springboot.visitersmanager.enums.EntityDbStatus;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitException;
import com.ceyentra.springboot.visitersmanager.service.VisitService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/visits")
@PreAuthorize("hasRole('ADMIN') or hasRole('RECEPTIONIST')")
@RequiredArgsConstructor
public class VisitRestController {

    private final VisitService visitService;

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
    getAllVisitsByDates(@RequestParam("start") Long start, @RequestParam("end") Long end) {

        List<VisitDTO> visitDTOS;

        String message;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if ((start != null) && (end != null)) {

            Date startDate;
            Date endDate;

            try {
                startDate = new Date(start);
                endDate = new Date(end);
            } catch (IllegalArgumentException | ArithmeticException exception) {
                throw new RuntimeException("Unable to process.Invalid value for start Date or end Date.");
            }

            visitDTOS = visitService.findVisitsByBetweenDays(
                    dateFormat.format(startDate),
                    dateFormat.format(endDate),
                    EntityDbStatus.ACTIVE);

            message = String.format("Visits Details for %s to %s",
                    dateFormat.format(new Date(start)),
                    dateFormat.format(new Date(end)));
        } else if (end != null) {

            Date endDate;

            try {
                endDate = new Date(end);
            } catch (IllegalArgumentException | ArithmeticException exception) {
                throw new RuntimeException("Unable to process.Invalid value for end Date.");
            }

            visitDTOS = visitService.findVisitsUntilGivenDate(
                    dateFormat.format(endDate),
                    EntityDbStatus.ACTIVE);

            message = String.format("Visits Details Until %s",
                    dateFormat.format(new Date(end)));

        } else if (start != null) {

            Date startDate;

            try {
                startDate = new Date(start);
            } catch (IllegalArgumentException | ArithmeticException exception) {
                throw new RuntimeException("Unable to process.Invalid value for start Date.");
            }

            visitDTOS = visitService.findVisitsByBetweenDays(
                    dateFormat.format(startDate),
                    dateFormat.format(new Date(System.currentTimeMillis())),
                    EntityDbStatus.ACTIVE
            );

            message = String.format("Visits Details From %s",
                    dateFormat.format(new Date(start)));

        } else {
            visitDTOS = visitService.findAllVisitsByDbStatus(EntityDbStatus.ACTIVE);
            message = "Successfully Retrieved Visit Details";
        }

        if (visitDTOS == null) {
            throw new VisitException("Unable to find Visit Details.");
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                message,
                visitDTOS),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('receptionist:read')")
    public ResponseEntity<ResponseUtil<VisitDTO>> getVisitById(@PathVariable int id) {

        Optional<VisitDTO> optionalVisitDTO = Optional.ofNullable(visitService.readVisitById(id));

        if (optionalVisitDTO.isEmpty() || optionalVisitDTO.get().getDbStatus() == EntityDbStatus.DELETED) {
            throw new VisitException(String.format("Couldn't find Visit Details with Associate ID - %d.", id));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                String.format("Successfully Retrieved Visit Details for Visit ID - %d", id),
                optionalVisitDTO.get()),
                HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create') or hasAuthority('receptionist:create')")
    public ResponseEntity<ResponseUtil<String>> addVisit(@RequestBody RequestVisitDTO requestVisitDTO) {

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

        //set current date
        requestVisitDTO.setCheckInDate(LocalDate.now());

        //set current time
        requestVisitDTO.setCheckInTime(LocalTime.parse(timeFormat.format(LocalTime.now())));

        System.out.println(requestVisitDTO);

        visitService.saveVisit(requestVisitDTO);

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.CREATED.value(),
                "Successfully saved Visit Details.",
                String.format("Successfully Checked In Visitor - %d", requestVisitDTO.getVisitorId())),
                HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('receptionist:update')")
    public ResponseEntity<ResponseUtil<String>> updateVisit(@RequestBody RequestVisitDTO requestVisitDTO) {

        visitService.updateVisitById(requestVisitDTO);

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "Successfully Updated Visit Details.",
                String.format("Updated Visit Details with Associate ID - %d", requestVisitDTO.getVisitId())),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<ResponseUtil<String>> deleteVisitById(@PathVariable int id) {

        int count = visitService.updateVisitDbStatusById(EntityDbStatus.DELETED, id);

        if (count <= 0) {
            throw new VisitException(String.format("Unable to find Visit with given ID - %d.", id));
        }

        return new ResponseEntity<>(new ResponseUtil<>(
                HttpStatus.OK.value(),
                "Successfully Deleted Visit.",
                String.format("Deleted Visit ID - %d.", id)),
                HttpStatus.OK);
    }
}