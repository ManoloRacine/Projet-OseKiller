package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.UserInfoDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OfferService {
    void modifyOffer(long offerId, OfferDto  offerDto, MultipartFile file);
    void addOffer(long companyId, OfferDto offerDto, MultipartFile file);
    List<GeneralOfferDto> getAllValidOffers();
    List<GeneralOfferDto> getAllInvalidOffers();
    OfferDtoResponse getOffer(long offerId);
    void addApplicantToOffer(long studentId, long offerId);
    List<UserInfoDto> getApplicants(long offerId);
}
