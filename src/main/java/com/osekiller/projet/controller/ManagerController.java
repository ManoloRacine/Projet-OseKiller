package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.EvaluationSimpleDto;
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
@RequestMapping("/manager")
public class ManagerController {

    ContractService contractService;

    @GetMapping("/evaluations")
    public ResponseEntity<List<EvaluationSimpleDto>> getEvaluations() {
        return ResponseEntity.ok(contractService.getEvaluations()) ;
    }

    @GetMapping("/{contractId}/evaluation-pdf")
    public ResponseEntity<Resource> getEvaluationPdf(@PathVariable Long contractId) {
        Resource evaluation = contractService.getEvaluationPdf(contractId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        evaluation.getFilename() + "\"").body(evaluation) ;
    }
}
