package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.ContractDto;
import com.osekiller.projet.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/contracts")
public class ContractController {

    ContractService contractService ;

    @GetMapping()
    public ResponseEntity<List<ContractDto>> getContracts() {
        return ResponseEntity.ok(contractService.getContracts()) ;
    }
}
