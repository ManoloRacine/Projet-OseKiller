package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.UserInfoDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;

import java.util.List;

public interface OfferService {
    List<GeneralOfferDto> getAllValidOffers();
    List<GeneralOfferDto> getAllInvalidOffers();
    OfferDtoResponse getOffer(long offerId);
    void addApplicantToOffer(long studentId, long offerId);
    List<UserInfoDto> getApplicants(long offerId);
}
