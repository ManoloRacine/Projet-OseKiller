package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService {
    void addOffer(Long companyId, OfferDto offerDto, MultipartFile file) ;
    List<OfferDtoResponseNoPdf> getOffersByCompany(Long companyId);
    Boolean companyExists(Long id);
    Boolean companyOwnsOffer(Long companyId, Long offerId);
    void validateOffer(Long offerId, String feedback);
    void invalidateOffer(Long offerId, String feedback);
}
