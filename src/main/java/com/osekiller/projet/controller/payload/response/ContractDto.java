package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.Contract;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record ContractDto(
        @NotNull Long studentId,
        @NotNull Long offerId,
        @NotNull Long managerId,
        @NotNull Long contractId,
        @NotBlank String studentName,
        @NotBlank String managerName,
        @NotBlank String companyName,
        @NotBlank String position,
        LocalDate studentSigningDate,
        LocalDate managerSigningDate,
        LocalDate companySigningDate,
        Boolean hasContractPdf,
        Boolean hasReport
) {
    public static ContractDto from(Contract contract){
        return new ContractDto(
                contract.getStudent().getId(),
                contract.getOffer().getId(),
                contract.getManager().getId(),
                contract.getId(),
                contract.getStudent().getName(),
                contract.getManager().getName(),
                contract.getOffer().getOwner().getName(),
                contract.getOffer().getPosition(),
                contract.getStudentSigningDate(),
                contract.getManagerSigningDate(),
                contract.getCompanySigningDate(),
                contract.getPdf() != null,
                contract.getReport() != null
                );
    }
}
