package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record StudentEvaluationDto(
        @NotBlank String supervisorName,
        @NotBlank String supervisorPosition,
        @NotBlank String phoneNumber,
        @NotNull ProductivityDto productivity,
        @NotNull WorkQualityDto workQuality,
        @NotNull PersonalAbilityDto personalAbility,
        @
) {
}
