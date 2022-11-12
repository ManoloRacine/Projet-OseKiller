package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.EvaluationDto;
import com.osekiller.projet.controller.payload.request.ValidationDto;
import com.osekiller.projet.controller.payload.response.ContractToEvaluateDto;
import com.osekiller.projet.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/teacher")
public class TeacherController {

    ContractService contractService ;

    @GetMapping("/contracts-to-evaluate")
    public ResponseEntity<List<ContractToEvaluateDto>> getContractsToEvaluate() {
        return ResponseEntity.ok(contractService.getUnEvaluatedContracts()) ;
    }

    @PostMapping("/{contractId}/evaluateInternship")
    public ResponseEntity<Void> evaluateInternship(@PathVariable(name = "contractId") Long contractId,
                                                   @Valid @RequestBody EvaluationDto dto) throws IOException {
        contractService.evaluateIntership(contractId, dto) ;
        return ResponseEntity.ok().build() ;
    }
}
