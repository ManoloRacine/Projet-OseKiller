package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.JwtRequestDto;
import com.osekiller.projet.controller.payload.response.AuthPingDto;
import com.osekiller.projet.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    public ResponseEntity<AuthPingDto> pingToken(@Valid @RequestBody JwtRequestDto dto) {
        return ResponseEntity.ok(authService.authPing(dto.refreshToken()));
    }
}
