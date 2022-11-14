package com.osekiller.projet.controller.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record WorkQualityDto(@NotNull Integer fieldA,
                             @NotNull Integer fieldB,
                             @NotNull Integer fieldC,
                             @NotNull Integer fieldD,
                             @NotNull Integer fieldE,
                             @NotBlank String comments) {
}
