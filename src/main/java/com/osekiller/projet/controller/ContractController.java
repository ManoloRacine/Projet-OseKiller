package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.EvaluationDto;
import com.osekiller.projet.controller.payload.response.ContractToEvaluateDto;
import com.osekiller.projet.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/contracts")
public class ContractController {

    ContractService contractService ;

    @GetMapping()
    //TODO ajust API to reflect the possibility of multiple different objects
    public ResponseEntity<List<?>> getContracts(@RequestParam(required = false) Boolean toEvaluate) {
        if (toEvaluate == null) {
            return ResponseEntity.ok(contractService.getContracts()) ;
        }
        if (toEvaluate) {
            return ResponseEntity.ok(contractService.getUnEvaluatedContracts()) ;
        }
        else  {
            throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED) ;
        }
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> getContractPdf(@PathVariable(name = "id") Long id) {
        Resource contract = contractService.getContract(id) ;
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        contract.getFilename() + "\"").body(contract) ;
    }

    @PostMapping("/{contractId}/evaluate-internship")
    public ResponseEntity<Void> evaluateInternship(@PathVariable(name = "contractId") Long contractId,
                                                   @Valid @RequestBody EvaluationDto dto) throws IOException {
        contractService.evaluateIntership(contractId, dto) ;
        return ResponseEntity.ok().build() ;
    }
}
