package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.user.Student;

import javax.validation.constraints.NotBlank;

public record StudentWithCvStateDto(@NotBlank String email,
                                    @NotBlank String name,
                                    @NotBlank Long id,
                                    @NotBlank boolean enabled,
                                    @NotBlank boolean cvValidated,
                                    @NotBlank boolean cvRejected,
                                    @NotBlank boolean cvPresent,
                                    @NotBlank String feedback,
                                    @NotBlank Integer sessionYear) {
    public static StudentWithCvStateDto from(Student student){
        boolean cvPresent = false ;
        if (student.getCv().getPdf() != null) {
            cvPresent = true ;
        }
        return new StudentWithCvStateDto(student.getEmail(), student.getName(), student.getId(), student.isEnabled(),
                student.getCv().isValidated(), student.isCvRejected(), cvPresent, student.getCv().getFeedback(), student.getSessionYear() );
    }
}
