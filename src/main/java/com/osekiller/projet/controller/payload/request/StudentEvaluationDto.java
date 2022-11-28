package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record StudentEvaluationDto(
        @NotBlank String supervisorName,
        @NotBlank String supervisorPosition,
        @NotBlank String phoneNumber,
        @NotNull StudentEvaluationSection productivity,
        @NotNull StudentEvaluationSection workQuality,
        @NotNull StudentEvaluationSection interpersonalQuality,
        @NotNull StudentEvaluationSection personalAbility,
        @NotNull Integer hoursOfSupportPerWeek,
        @NotNull Integer expectationsAchieved,
        @NotBlank String expectationsComment,
        @NotNull Boolean internInformed,
        @NotNull Integer keepIntern,
        @NotBlank String internFormationComment
) {
}
