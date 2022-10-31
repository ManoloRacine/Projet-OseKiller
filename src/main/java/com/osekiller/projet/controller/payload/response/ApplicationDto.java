package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ApplicationDto(
        @NotBlank String studentName,
        @NotNull Long studentId,
        @NotBlank String companyName,
        @NotNull Long offerId,
        @NotBlank String position
) {
    public static ApplicationDto from (Offer offer, Student student){
        return new ApplicationDto(student.getName(), student.getId(), offer.getOwner().getName(), offer.getId(), offer.getPosition());
    }
}
