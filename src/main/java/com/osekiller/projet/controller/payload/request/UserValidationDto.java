package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserValidationDto (@NotNull(message = "validation-is-mandatory") boolean validation) {
}
