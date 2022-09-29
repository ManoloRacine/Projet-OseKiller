package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.StudentCvValidationDto;
import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin
public class StudentController {

    StudentService studentService;

    @PostMapping("/student/{id}/cv/validate")
    public ResponseEntity<Void> validateStudentCv(@Valid @RequestBody StudentCvValidationDto dto,
                                                  @RequestParam(name = "id") Long id){
        if (dto.validation()) {
            studentService.validateCV(id);
        }
        else {
            studentService.invalidateCV(id);
        }
        return ResponseEntity.ok().build();
    }

}
