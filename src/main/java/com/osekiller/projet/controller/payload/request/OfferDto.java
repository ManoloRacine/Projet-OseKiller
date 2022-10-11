package com.osekiller.projet.controller.payload.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public record OfferDto(
        @NotBlank String position,
        @NotNull double salary,
        @NotBlank String startDate,
        @NotBlank String endDate
        ) {
}
