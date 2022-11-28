package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.InternDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;

import java.util.List;

public interface CompanyService {
    List<OfferDtoResponseNoPdf> getOffersByCompany(Long companyId);
    Boolean companyExists(Long id);
    Boolean companyOwnsOffer(Long companyId, Long offerId);
    void validateOffer(Long offerId, String feedback);
    void invalidateOffer(Long offerId, String feedback);

    List<InternDto> getInterns(Long companyId);
}
