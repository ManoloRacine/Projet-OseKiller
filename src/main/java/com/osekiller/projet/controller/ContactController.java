package com.osekiller.projet.controller;

import com.osekiller.projet.service.AuthService;
import com.osekiller.projet.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/contracts")
public class ContactController {

    ContractService contractService ;

    AuthService authService ;

    @PostMapping("/{offerId}/generate")
    public ResponseEntity<Resource> generateContract(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                     @RequestParam Long offerId,
                                                     @RequestBody List<String> contractTasks) throws IOException {
        String jwt = header.substring(7);
        Long managerId = authService.getUserFromToken(jwt).id();
        Resource contract = contractService.generateContract(contractTasks, managerId, offerId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + contract.getFilename() + "\"").body(contract);
    }
}
