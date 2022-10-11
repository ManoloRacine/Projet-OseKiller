package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.StudentCVValidationDto;
import com.osekiller.projet.controller.payload.response.StudentDto;
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
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/students")
public class StudentController {
    StudentService studentService;
    @GetMapping
    public ResponseEntity<List<StudentDto>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents()) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudents(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(studentService.getStudent(id)) ;
    }
    @PutMapping("/{id}/cv")
    public ResponseEntity<Void> saveCV(@Valid @RequestBody MultipartFile file, @PathVariable(name = "id") Long id) {
        studentService.saveCV(file, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/cv")
    public ResponseEntity<Resource> getCV(@PathVariable(name = "id") Long id) {
        Resource cv = studentService.getCV(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cv.getFilename() + "\"").body(cv);
    }

    @PostMapping("/{id}/cv/validate")
    public ResponseEntity<Void> validateStudentCv(@Valid @RequestBody StudentCVValidationDto dto,
            @PathVariable(name = "id") Long id) {
        if (dto.validation()) {
            studentService.validateCV(id, dto.feedBack());
        } else {
            studentService.invalidateCV(id, dto.feedBack());
        }
        return ResponseEntity.ok().build();
    }
}
