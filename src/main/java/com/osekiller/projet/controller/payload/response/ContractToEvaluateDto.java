package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.Contract;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ContractToEvaluateDto(
        @NotNull Long contractId,
        @NotBlank String companyName,
        @NotBlank String studentName,
        @NotBlank String position
) {

    public static ContractToEvaluateDto from(Contract contract) {
        return new ContractToEvaluateDto(
                contract.getId(),
                contract.getOffer().getOwner().getName(),
                contract.getStudent().getName(),
                contract.getOffer().getPosition()
        ) ;
    }
}
