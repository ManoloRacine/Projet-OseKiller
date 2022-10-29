package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;

public record InterviewConfirmationDto(
        @NotBlank(message = "date-is-mandatory") String chosenDate
) {
}
