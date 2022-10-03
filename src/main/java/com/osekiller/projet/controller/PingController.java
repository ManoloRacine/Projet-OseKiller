package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/ping")
public class PingController {
    AuthService authService;
    @GetMapping
    public ResponseEntity<Void> ping() {
        return ResponseEntity.ok().build();
    }
    @GetMapping("/token")
    public ResponseEntity<UserDto> pingToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        String jwt = header.substring(7);
        return ResponseEntity.ok(authService.getUserFromToken(jwt));
    }
}
