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


}
