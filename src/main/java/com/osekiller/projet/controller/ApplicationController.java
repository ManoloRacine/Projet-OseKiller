package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.ApplicationDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/applications")
public class ApplicationController {

    @GetMapping
    public ResponseEntity<List<ApplicationDto>> getApplications(@RequestParam boolean accepted) {
        return null ;
    }
}
