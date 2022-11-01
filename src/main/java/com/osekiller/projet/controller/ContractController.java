package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.ContractDto;
import com.osekiller.projet.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> getContractPdf(@PathVariable(name = "id") Long id) {
        Resource contract = contractService.getContract(id) ;
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        contract.getFilename() + "\"").body(contract) ;
    }
}
