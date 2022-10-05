package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotNull;

public record UserValidationDto (@NotNull(message = "validation-is-mandatory") boolean validation) {
}
