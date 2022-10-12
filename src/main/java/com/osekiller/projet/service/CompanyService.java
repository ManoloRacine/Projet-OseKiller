package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompanyService {
    Boolean companyExists(Long id);
    Boolean companyOwnsOffer(Long companyId, Long offerId);
    void addOffer(Long companyId, OfferDto offerDto, MultipartFile file);
    OfferDtoResponse getOffer(Long offerId);
    List<OfferDtoResponseNoPdf> getAllOffersCompany(Long companyId) ;
    List<GeneralOfferDto> getAllValidOffers();
    List<GeneralOfferDto> getAllInvalidOffers();
    void validateOffer(Long offerId, String feedback);
    void invalidateOffer(Long offerId, String feedback);
}
