package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record InterpersonnalQualityDto(@NotNull Integer fieldA,
                                       @NotNull Integer fieldB,
                                       @NotNull Integer fieldC,
                                       @NotNull Integer fieldD,
                                       @NotNull Integer fieldE,
                                       @NotNull Integer fieldF,
                                       @NotBlank String comments) {
}
