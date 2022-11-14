package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record NextInternshipDto(@NotNull Integer fieldA,
                                @NotBlank String fieldB,
                                @NotNull Boolean fieldC,
                                @NotBlank String date) {
}
