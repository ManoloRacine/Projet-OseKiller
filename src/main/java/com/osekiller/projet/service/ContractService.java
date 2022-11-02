package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.ApplicationDto;
import com.osekiller.projet.controller.payload.response.ContractDto;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface ContractService {
    Resource generateContract(List<String> contractTasks, long offerId, long studentId, long managerId) throws IOException;
    List<ApplicationDto> getAcceptedApplications() ;

    List<ContractDto> getContracts() ;

    Resource getContract(long contractId) ;
}