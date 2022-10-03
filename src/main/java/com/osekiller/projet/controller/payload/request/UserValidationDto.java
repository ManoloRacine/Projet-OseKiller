package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserValidationDto (@NotBlank(message = "email-is-mandatory") @Email String email,
                                 boolean validation) {
}
