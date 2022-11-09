package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.ContractDto;
import com.osekiller.projet.controller.payload.response.UserDto;
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

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/contracts")
public class ContractController {

    ContractService contractService;

    AuthService authService;

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

    @PostMapping("/{id}/sign")
    public ResponseEntity<Resource> signContract(@PathVariable(name = "id") Long contractId, @RequestHeader(HttpHeaders.AUTHORIZATION) String header, @RequestParam("image") MultipartFile image) {
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
}
