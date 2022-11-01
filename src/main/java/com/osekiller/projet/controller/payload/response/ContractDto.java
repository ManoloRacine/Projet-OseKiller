package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.controller.ContractController;
import com.osekiller.projet.model.Contract;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.service.implementation.ContractServiceImpl;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ContractDto(
        @NotNull Long studentId,
        @NotNull Long offerId,
        @NotNull Long managerId,
        @NotNull Long contractId,
        @NotBlank String studentName,
        @NotBlank String managerName,
        @NotBlank String companyName,
        @NotBlank String position
) {
    public static ContractDto from(Contract contract){
        return new ContractDto(contract.getStudent().getId(), contract.getOffer().getId(), contract.getManager().getId(),
                contract.getId(), contract.getStudent().getName(), contract.getManager().getName(), contract.getOffer().getOwner().getName(),
                contract.getOffer().getPosition());
    }
}
