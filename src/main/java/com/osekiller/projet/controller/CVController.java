package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.CVValidationDto;
import com.osekiller.projet.service.ResourceFactory;
import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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

    @GetMapping("/student/{id}/cv")
    public ResponseEntity<Resource> getCV(@PathVariable(name = "id") Long id) {
        ResourceFactory factory = UrlResource::new;
        Resource cv = studentService.getCV(id, factory) ;
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cv.getFilename() + "\"").body(cv);
    }


    @PostMapping("/student/{id}/cv/validate")
    public ResponseEntity<Void> validateStudentCv(@Valid @RequestBody CVValidationDto dto,
                                                  @PathVariable(name = "id") Long id) {
        if (dto.validate()) {
            studentService.validateCV(id);
        } else {
            studentService.invalidateCV(id);
        }
        return ResponseEntity.ok().build();
    }
}
