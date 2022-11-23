package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record StudentEvaluationSection(
        @NotNull List<QuestionAnswerDto> questionsAnswers,
        @NotBlank String comments
        ) {
}
