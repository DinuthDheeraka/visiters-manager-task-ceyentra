/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 6:52 PM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.entity.VisitDTO;
import com.ceyentra.springboot.visitersmanager.dto.entity.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.exceptions.VisitorNotFoundException;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import com.ceyentra.springboot.visitersmanager.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/visitor")
public class VisitorRestController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorRestController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @GetMapping
    public ResponseEntity<ResponseUtil<List<VisitorDTO>>> getAllVisitors() {

        Optional<List<VisitorDTO>> optionalVisitorDTOS = Optional.ofNullable(visitorService.readAllVisitors());

        if(optionalVisitorDTOS.isEmpty()){
            throw new VisitorNotFoundException("Unable to find visitors");
        }

        return new ResponseEntity(new ResponseUtil(
                        HttpStatus.OK.value(), "success" ,optionalVisitorDTOS.get()
                ),
                HttpStatus.OK
        );

    }

    @GetMapping("/{id}")
    public VisitorDTO getAllVisitors(@PathVariable int id) {
        return visitorService.readVisitorById(id);
    }

    @PostMapping
    public VisitorDTO addVisitor(@RequestBody VisitorDTO visitorDTO) {
        return visitorService.saveVisitor(visitorDTO);
    }

    @PutMapping
    public VisitorDTO updateVisitor(@RequestBody VisitorDTO visitorDTO) {
        return visitorService.updateVisitor(visitorDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteVisitor(@PathVariable int id) {
        return visitorService.deleteVisitorById(id);
    }

    @GetMapping("/visits/{id}")
    public List<VisitDTO> getVisitsById(@PathVariable int id) {
        return visitorService.readAllVisitsByVisitorId(id);
    }
}
