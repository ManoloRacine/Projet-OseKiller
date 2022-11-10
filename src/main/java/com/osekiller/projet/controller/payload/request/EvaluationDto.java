package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record EvaluationDto(
        @NotBlank String companyContact,
        @NotBlank String address,
        @NotBlank String city,
        @NotBlank String postalCode,
        @NotBlank String phoneNumber,
        @NotBlank String fax,
        @NotNull Integer internshipNo,
        @NotNull List<QuestionAnswerDto> evaluation,
        @NotBlank String comment,
        @NotNull Integer preferredInternship,
        @NotNull Integer internNbs,
        @NotNull Boolean keepIntern,
        @NotNull List<List<Integer>> workShifts
        ) {
}
