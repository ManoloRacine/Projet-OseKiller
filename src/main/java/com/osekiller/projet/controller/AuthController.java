package com.osekiller.projet.controller;


import com.osekiller.projet.controller.request.CompanySignUpRequest;
import com.osekiller.projet.controller.request.ManagerSignUpRequest;
import com.osekiller.projet.controller.request.StudentSignUpRequest;
import com.osekiller.projet.model.Company;
import com.osekiller.projet.model.Manager;
import com.osekiller.projet.model.Student;
import com.osekiller.projet.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {
    AuthService authService;
    @PostMapping("/student/signUp")
    public ResponseEntity<Void> signUpStudent(@RequestBody StudentSignUpRequest request){
        authService.signUpStudent(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/manager/signUp")
    public ResponseEntity<Void> signUpManger(@RequestBody ManagerSignUpRequest request){
        authService.signUpManager(request);
        return ResponseEntity.accepted().build();
    }
    @PostMapping("/company/signUp")
    public ResponseEntity<Void> signUpCompany(@RequestBody CompanySignUpRequest request){
        authService.signUpCompany(request);
        return ResponseEntity.accepted().build();
    }
}
