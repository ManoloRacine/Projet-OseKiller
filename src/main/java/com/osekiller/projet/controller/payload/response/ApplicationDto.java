package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.Contract;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Student;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record ApplicationDto(
        @NotBlank String studentName,
        @NotNull Long studentId,
        @NotBlank String companyName,
        @NotNull Long offerId,
        @NotBlank String position,
        Long contractId,
        LocalDate studentSigningDate,
        LocalDate managerSigningDate,
        LocalDate companySigningDate,
        Long managerId
) {
    public static ApplicationDto from (Offer offer, Student student, Contract contract){
        return new ApplicationDto(
                student.getName(),
                student.getId(),
                offer.getOwner().getName(),
                offer.getId(), offer.getPosition(),
                (contract == null ? null : contract.getId()),
                (contract == null ? null : contract.getStudentSigningDate()),
                (contract == null ? null : contract.getManagerSigningDate()),
                (contract == null ? null : contract.getCompanySigningDate()),
                (contract == null ? null : contract.getManager().getId())
                );
    }
}
