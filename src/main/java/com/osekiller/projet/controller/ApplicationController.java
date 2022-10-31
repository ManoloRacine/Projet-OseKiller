package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/applications")
public class ApplicationController {

    ContractService contractService ;

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getApplications(@RequestParam boolean accepted) {
        if (accepted) {
            return ResponseEntity.ok(contractService.getAcceptedApplications()) ;
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST) ;
        }
    }
}
