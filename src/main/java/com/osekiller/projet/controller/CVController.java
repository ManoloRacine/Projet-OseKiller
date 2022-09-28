package com.osekiller.projet.controller;

import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@CrossOrigin
public class CVController {
    StudentService studentService ;

    @PutMapping("/student/{id}/cv")
    public ResponseEntity<Void> saveCV(@Valid @RequestBody MultipartFile file, @PathVariable(name = "id") Long id) {
        studentService.saveCV(file, id);
        return ResponseEntity.ok().build() ;
    }
}
