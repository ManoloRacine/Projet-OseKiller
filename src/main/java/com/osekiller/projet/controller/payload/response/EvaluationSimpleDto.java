package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.Contract;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record EvaluationSimpleDto(
        @NotNull Long contractId,
        @NotBlank String companyName,
        @NotBlank String studentName,
        @NotBlank String position,
        @NotBlank String startDate,
        @NotBlank String endDate
) {
    public static EvaluationSimpleDto from(Contract contract) {
        return new EvaluationSimpleDto(
                contract.getId(),
                contract.getOffer().getOwner().getName(),
                contract.getStudent().getName(),
                contract.getOffer().getPosition(),
                contract.getOffer().getStartDate().toString(),
                contract.getOffer().getEndDate().toString()
        ) ;
    }
}
