/**
 * @author :  Dinuth Dheeraka
 * Created : 7/9/2023 6:52 PM
 */
package com.ceyentra.springboot.visitersmanager.rest;

import com.ceyentra.springboot.visitersmanager.dto.VisitorDTO;
import com.ceyentra.springboot.visitersmanager.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/visitor")
public class VisitorRestController {

    private final VisitorService visitorService;

    @Autowired
    public VisitorRestController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @GetMapping
    public List<VisitorDTO> getAllVisitors() {
        return visitorService.readAllVisitors();
    }

    @PostMapping
    public VisitorDTO addVisitor(@RequestBody VisitorDTO visitorDTO) {
        return visitorService.saveVisitor(visitorDTO);
    }
}
