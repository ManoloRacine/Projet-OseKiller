package com.osekiller.projet.controller;

import com.osekiller.projet.service.ResourceFactory;
import com.osekiller.projet.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URI;

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
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cv.getFilename() + "\"").body(cv);
    }
}
