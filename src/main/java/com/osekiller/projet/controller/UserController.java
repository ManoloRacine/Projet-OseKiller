package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.UserValidationDto;
import com.osekiller.projet.controller.payload.response.StudentDto;
import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    UserService userService;
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers()) ;
    }
    @PostMapping("/{id}/validate")
    public ResponseEntity<Void> validateUser(@Valid @RequestBody UserValidationDto dto,
                                             @PathVariable(name = "id") Long id) {
        if (dto.validation()) {
            userService.validateUser(id);
        }
        else {
            userService.invalidateUser(id);
        }
        return ResponseEntity.ok().build() ;
    }
}
