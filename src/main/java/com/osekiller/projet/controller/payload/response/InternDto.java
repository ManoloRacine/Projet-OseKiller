package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.Contract;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record InternDto(
        @NotNull Long studentId,
        @NotBlank String studentName,
        @NotBlank String position,
        @NotBlank String companyName,
        @NotNull Long contractId
) {
    public static InternDto from(Contract contract) {
        return new InternDto(contract.getStudent().getId(),
                contract.getStudent().getName(),
                contract.getOffer().getPosition(),
                contract.getOffer().getOwner().getName(),
                contract.getId());
    }
}
