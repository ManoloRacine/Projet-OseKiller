package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.EvaluationDto;
import com.osekiller.projet.controller.payload.request.StudentEvaluationDto;
import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.model.ERole;
import com.osekiller.projet.service.AuthService;
import com.osekiller.projet.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/contracts")
public class ContractController {

    ContractService contractService;

    AuthService authService;

    @GetMapping()
    //TODO ajust API to reflect the possibility of multiple different objects
    public ResponseEntity<List<?>> getContracts(
            @RequestParam(required = false) Boolean toEvaluate,
            @RequestParam(required = false) Long signatoryId,
            @RequestParam(required = false) Boolean hasInternEvaluation,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header
    ) {
        if(toEvaluate != null && signatoryId != null && hasInternEvaluation != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(signatoryId != null){
            String jwt = header.substring(7);
            UserDto signatoryFound = authService.getUserFromToken(jwt);

            if(!signatoryId.equals(signatoryFound.id())){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }

            if (signatoryFound.role().equals(ERole.MANAGER.name())){
                return ResponseEntity.ok(contractService.getContractsByManagerId(signatoryId));
            }

            if (signatoryFound.role().equals(ERole.COMPANY.name())){
                return ResponseEntity.ok(contractService.getContractsByCompanyId(signatoryId));
            }

            if (signatoryFound.role().equals(ERole.STUDENT.name())){
                return ResponseEntity.ok(contractService.getContractsByStudentId(signatoryId));
            }
        }

        if (toEvaluate == null && hasInternEvaluation == null) {
            return ResponseEntity.ok(contractService.getContracts()) ;
        }

        if (Boolean.TRUE.equals(toEvaluate)) {
            return ResponseEntity.ok(contractService.getUnevaluatedContracts()) ;
        }

        if (Boolean.TRUE.equals(hasInternEvaluation)) {
            return ResponseEntity.ok(contractService.getContractWithInternEvaluations()) ;
        }

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED) ;
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<Resource> getContractPdf(@PathVariable(name = "id") Long id) {
        Resource contract = contractService.getContract(id) ;
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        contract.getFilename() + "\"").body(contract) ;
    }

    @GetMapping("/{id}/intern-evaluation")
    public ResponseEntity<Resource> getInternEvaluation(@PathVariable(name = "id") Long id) {
        Resource contract = contractService.getInternEvaluation(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        contract.getFilename() + "\"").body(contract) ;
    }

    @PostMapping("/{id}/sign")
    public ResponseEntity<Resource> signContract(@PathVariable(name = "id") Long contractId,
                                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                 @RequestParam("image") MultipartFile image) throws IOException {
        String jwt = header.substring(7);
        UserDto userDto = authService.getUserFromToken(jwt);

        if(image != null && !image.isEmpty()){
            return ResponseEntity.ok(contractService.signContract(contractId, userDto.id(), image));
        }

        if(!contractService.hasSignature(userDto.id())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(contractService.signContract(contractId, userDto.id()));
    }

    @PostMapping("/{contractId}/evaluate-internship")
    public ResponseEntity<Void> evaluateInternship(@PathVariable(name = "contractId") Long contractId,
                                                   @Valid @RequestBody EvaluationDto dto) throws IOException {
        contractService.evaluateIntership(contractId, dto) ;
        return ResponseEntity.ok().build() ;
    }

    @PostMapping("/{contractId}/evaluate-intern")
    public ResponseEntity<Void> evaluateIntern(@PathVariable(name = "contractId") Long contractId,
                                               @Valid @RequestBody StudentEvaluationDto dto) throws IOException {
        contractService.evaluateIntern(contractId, dto) ;
        return ResponseEntity.ok().build() ;
    }

    @GetMapping("/{contractId}/report")
    public ResponseEntity<Resource> getReport(@PathVariable(name = "contractId") Long contractId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION).body(contractService.getReport(contractId)) ;
    }
    
    @PutMapping("/{contractId}/report")
    public ResponseEntity<Void> putReport(@PathVariable(name = "contractId") Long contractId,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                          @Valid @RequestBody MultipartFile file) throws IOException {
        String jwt = header.substring(7);
        UserDto userDto = authService.getUserFromToken(jwt);

        contractService.saveReport(file, contractId, userDto.id());

        return ResponseEntity.ok().build() ;
    }
}
