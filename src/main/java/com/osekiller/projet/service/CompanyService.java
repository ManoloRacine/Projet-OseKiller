package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService {
    void addOffer(Long companyId, OfferDto offerDto, MultipartFile file) ;
    OfferDtoResponse getOffer(Long offerId);
    List<OfferDtoResponseNoPdf> getAllOffersCompany(Long companyId) ;
    void init();
    void deleteAll();
}
