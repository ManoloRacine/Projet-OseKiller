package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.EvaluationDto;
import com.osekiller.projet.controller.payload.request.StudentEvaluationDto;
import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.controller.payload.response.ContractDto;
import com.osekiller.projet.controller.payload.response.ContractToEvaluateDto;
import com.osekiller.projet.controller.payload.response.EvaluationSimpleDto;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface ContractService {
    Resource generateContract(List<String> contractTasks, long offerId, long studentId, long managerId) throws IOException;
    List<ApplicationDto> getAcceptedApplications() ;

    List<ContractDto> getContracts() ;

    Resource getContract(long contractId) ;

    List<ContractToEvaluateDto> getUnevaluatedContracts();

    void evaluateIntership(Long contractId, EvaluationDto dto) throws IOException;

    void evaluateIntern(long contractId, StudentEvaluationDto dto) throws IOException;
    List<EvaluationSimpleDto> getEvaluations();

    Resource getEvaluationPdf(Long contractId);
}
