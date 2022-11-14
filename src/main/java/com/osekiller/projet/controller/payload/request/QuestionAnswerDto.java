package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record QuestionAnswerDto(
        @NotBlank String question,
        @NotNull Integer answer
) {
}
