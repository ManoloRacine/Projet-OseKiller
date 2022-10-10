package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;

import java.util.List;

public interface CompanyService {
    void addOffer(Long companyId, OfferDto offerDto) ;
    OfferDtoResponse getOffer(Long offerId, Long companyId);
    List<OfferDtoResponse> getAllOffersCompany(Long companyId) ;
}
