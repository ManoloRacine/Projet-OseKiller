package com.osekiller.projet.controller;


import com.osekiller.projet.controller.request.CompanySignUpRequest;
import com.osekiller.projet.controller.request.ManagerSignUpRequest;
import com.osekiller.projet.controller.request.StudentSignUpRequest;
import com.osekiller.projet.service.implementation.AuthServiceImplementation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthController {
    AuthServiceImplementation authService;
    @PostMapping("/student/signUp")
    public ResponseEntity<Void> signUpStudent(@Valid @RequestBody StudentSignUpRequest request){

        authService.signUpStudent(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/manager/signUp")
    public ResponseEntity<Void> signUpManger(@Valid @RequestBody ManagerSignUpRequest request){

        authService.signUpManager(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/company/signUp")
    public ResponseEntity<Void> signUpCompany(@Valid @RequestBody CompanySignUpRequest request){
        authService.signUpCompany(request);
        return ResponseEntity.accepted().build();
    }

//    @PostMapping("/signin")
//    public ResponseEntity<String> signin(){
//
//    }

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
