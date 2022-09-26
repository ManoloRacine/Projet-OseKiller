package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.UserValidationDto;
import com.osekiller.projet.controller.payload.response.UsersDto;
import com.osekiller.projet.service.AuthService;
import com.osekiller.projet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class UserController {
    UserService userService;

    @PostMapping("/user/validate")
    public ResponseEntity<Void> validateUser(@Valid @RequestBody UserValidationDto dto) {
        if (dto.validation()) {
            userService.validateUser(dto.email());
        }
        else {
            userService.invalidateUser(dto.email());
        }
        return ResponseEntity.accepted().build() ;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UsersDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers()) ;
    }
}
