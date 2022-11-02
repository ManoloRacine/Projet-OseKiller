package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.InterviewConfirmationDto;
import com.osekiller.projet.controller.payload.request.ValidationDto;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.InterviewDto;
import com.osekiller.projet.controller.payload.response.StudentWithCvStateDto;
import com.osekiller.projet.service.*;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/students")
public class StudentController {
    StudentService studentService;
    InterviewService interviewService;
    AuthService authService;
    ContractService contractService ;

    CompanyService companyService;
    @GetMapping
    public ResponseEntity<List<StudentWithCvStateDto>> getStudents() {
        return ResponseEntity.ok(studentService.getStudents()) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentWithCvStateDto> getStudents(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(studentService.getStudent(id)) ;
    }
    @PutMapping("/{id}/cv")
    public ResponseEntity<Void> saveCv(@Valid @RequestBody MultipartFile file, @PathVariable(name = "id") Long id) {
        studentService.saveCV(file, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/cv")
    public ResponseEntity<Resource> getCv(@PathVariable(name = "id") Long id) {
        Resource cv = studentService.getCV(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cv.getFilename() + "\"").body(cv);
    }

    @GetMapping("/{id}/applications")
    public ResponseEntity<List<GeneralOfferDto>> getApplications(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(studentService.getApplications(id));
    }

    @PostMapping("/{id}/interviews")
    public ResponseEntity<Void> inviteToInterview(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                  @PathVariable(name = "id") Long studentId,
                                                  @RequestParam Long offerId,
                                                  @RequestBody List<String> datesStrings){
        String jwt = header.substring(7);
        Long companyId = authService.getUserFromToken(jwt).id();

        if(!companyService.companyOwnsOffer(companyId, offerId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        List<LocalDate> dates = new ArrayList<>();
        for (String dateString: datesStrings) {
            try {
                dates.add(LocalDate.parse(dateString));
            } catch (DateTimeParseException ex){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        interviewService.inviteApplicantToInterview(studentId,offerId,dates);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}/interviews")
    public ResponseEntity<List<InterviewDto>> getInterviews(@PathVariable(name = "id") Long studentId){
        return ResponseEntity.ok(studentService.getInterviews(studentId));
    }

    @PostMapping("/{id}/cv/validate")
    public ResponseEntity<Void> validateStudentCv(@Valid @RequestBody ValidationDto dto,
            @PathVariable(name = "id") Long id) {
        if (dto.validation()) {
            studentService.validateCV(id, dto.feedBack());
        } else {
            studentService.invalidateCV(id, dto.feedBack());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/updateSession")
    public ResponseEntity<StudentWithCvStateDto> updateStudentSession(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(studentService.updateSession(id)) ;
    }

    @PostMapping("/{studentId}/applications/{offerId}/generate-contract")
    public ResponseEntity<Resource> generateContract(@RequestHeader(HttpHeaders.AUTHORIZATION) String header,
                                                     @PathVariable(name = "studentId") Long studentId,
                                                     @PathVariable(name = "offerId") Long offerId,
                                                     @RequestBody List<String> contractTasks) throws IOException {

        String jwt = header.substring(7);
        Long managerId = authService.getUserFromToken(jwt).id();
        Resource contract = contractService.generateContract(contractTasks, offerId, studentId, managerId);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + contract.getFilename() + "\"").body(contract);
    }

    @PostMapping("/{studentId}/interviews/{interviewId}/confirm")
    public ResponseEntity<Void> confirmInterviewDate(@Valid @RequestBody InterviewConfirmationDto dto,
                                                     @PathVariable(name = "studentId") Long studentId,
                                                     @PathVariable(name = "interviewId") Long interviewId,
                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) String header){

        String jwt = header.substring(7);
        Long tokenStudentId = authService.getUserFromToken(jwt).id();

        if(!tokenStudentId.equals(studentId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        if(!studentService.studentExists(studentId) || !studentService.studentOwnsInterview(studentId,interviewId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        studentService.confirmInterview(interviewId, dto.chosenDate());

        return ResponseEntity.ok().build();
    }
}
