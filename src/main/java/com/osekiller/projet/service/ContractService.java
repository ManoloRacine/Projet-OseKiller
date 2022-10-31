package com.osekiller.projet.service;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface ContractService {
    Resource generateContract(List<String> contractTasks, long offerId, long studentId, long managerId) throws IOException;
}
