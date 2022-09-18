package com.osekiller.projet.controller;

import com.osekiller.projet.controller.request.SignInDto;
import com.osekiller.projet.controller.request.SignUpDto;
import com.osekiller.projet.model.User;
import com.osekiller.projet.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthController {
    AuthService authService;
    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@Valid @RequestBody SignUpDto dto){
        authService.signUp(dto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<String> signin(@Valid @RequestBody SignInDto dto){
       return ResponseEntity.ok(authService.signIn(dto));
    }

    //https://www.baeldung.com/spring-boot-bean-validation
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
