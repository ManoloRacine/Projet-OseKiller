package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserValidationDto (@NotBlank(message = "email-is-mandatory") @Email String email,
                                 @NotBlank(message = "validation-is-mandatory") boolean validation) {
}
