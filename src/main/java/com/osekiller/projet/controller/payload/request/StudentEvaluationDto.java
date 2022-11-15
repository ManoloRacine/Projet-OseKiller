package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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
        @NotNull String expectationsComment,
        @NotNull Boolean internInformed,
        @NotNull Integer keepIntern,
        @NotNull String internFormationComment
) {
}
