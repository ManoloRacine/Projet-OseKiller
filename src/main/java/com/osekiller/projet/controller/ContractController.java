package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.ContractDto;
import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.model.ERole;
import com.osekiller.projet.service.AuthService;
import com.osekiller.projet.controller.payload.request.EvaluationDto;
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
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header
    ) {
        if(toEvaluate != null && signatoryId != null){
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
        if (toEvaluate == null) {
            return ResponseEntity.ok(contractService.getContracts()) ;
        }
        if (toEvaluate) {
            return ResponseEntity.ok(contractService.getUnevaluatedContracts()) ;
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

    @PostMapping("/{id}/sign")
    public ResponseEntity<Resource> signContract(@PathVariable(name = "id") Long contractId, @RequestHeader(HttpHeaders.AUTHORIZATION) String header, @RequestParam("image") MultipartFile image) throws IOException {
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
}
