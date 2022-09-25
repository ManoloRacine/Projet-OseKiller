package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.AuthPingDto;
import com.osekiller.projet.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/ping")
@CrossOrigin
public class PingController {
    AuthService authService;
    @GetMapping
    public ResponseEntity<Void> ping() {
        return ResponseEntity.ok().build();
    }
    @GetMapping("/token")
    public ResponseEntity<AuthPingDto> pingToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String jwt = header.substring(7);
        return ResponseEntity.ok(authService.authPing(jwt));
    }
}
