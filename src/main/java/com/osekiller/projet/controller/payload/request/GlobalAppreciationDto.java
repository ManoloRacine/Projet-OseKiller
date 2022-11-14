package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record GlobalAppreciationDto(@NotNull Integer fieldA,
                                    @NotBlank String fieldB,
                                    @NotNull Boolean fieldC) {
}
