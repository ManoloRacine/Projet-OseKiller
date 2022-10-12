package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository ;

    private OfferRepository offerRepository ;

    @Override
    public Boolean companyExists(Long id) {
        return companyRepository.existsById(id);
    }

    @Override
    public Boolean companyOwnsOffer(Long companyId, Long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return offer.getOwner().getId().equals(companyId);
    }

    @Override
    public void addOffer(Long companyId, OfferDto offerDto, MultipartFile file) {
        Optional<Company> companyOptional = companyRepository.findById(companyId) ;

        if (companyOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        Offer offer = new Offer(companyOptional.get(), offerDto.position(), offerDto.salary(), LocalDate.parse(offerDto.startDate()), LocalDate.parse(offerDto.endDate())) ;

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        offer.setPdfName(fileName);

        try {
            offer.setPdf(file.getBytes());
        } catch (IOException e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        offerRepository.save(offer) ;


    }

    @Override
    public OfferDtoResponse getOffer(Long offerId) {

        Optional<Offer> offer = offerRepository.findById(offerId) ;

        if (offer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        Offer offer1 = offer.get() ;

        Resource resource = new ByteArrayResource(offer1.getPdf()) ;

        return new OfferDtoResponse(offer1.getId(), offer1.getPosition(), offer1.getSalary(), offer1.getStartDate().toString(),
                offer1.getEndDate().toString(), resource) ;

    }

    @Override
    public List<OfferDtoResponseNoPdf> getAllOffersCompany(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId) ;
        if (company.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        List<Offer> offerList = offerRepository.findAllByOwner(company.get()) ;

        List<OfferDtoResponseNoPdf> offerDtoResponseList = new ArrayList<>() ;

        for (Offer offer: offerList) {
            offerDtoResponseList.add(new OfferDtoResponseNoPdf(offer.getId(), offer.getPosition(), offer.getSalary(),
                    offer.getStartDate().toString(), offer.getEndDate().toString())) ;
        }

        return offerDtoResponseList ;
    }

    @Override
    public List<GeneralOfferDto> getAllValidOffers() {
        List<Offer> offerList = offerRepository.findAllByAcceptedIsTrue();
        List<GeneralOfferDto> generalOfferDtos = offerList.stream().map((offer -> new GeneralOfferDto(
                offer.getId(), offer.getOwner().getId(), offer.getOwner().getName(), offer.getPosition(),
                offer.getSalary(), offer.getStartDate().toString(), offer.getEndDate().toString()
        ))).toList();
        return generalOfferDtos;
    }

    @Override
    public void validateOffer(Long offerId, String feedback) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        offer.setAccepted(true);
        if(!(feedback == null || feedback.isEmpty() || feedback.isBlank()))
            offer.setFeedback(feedback);

        offerRepository.save(offer);
    }

    @Override
    public void invalidateOffer(Long offerId, String feedback) {
        if(feedback == null || feedback.isEmpty() || feedback.isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        Offer offer = offerRepository.findById(offerId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        offer.setFeedback(feedback);
        offerRepository.save(offer);
    }

    @Override
    public List<GeneralOfferDto> getAllInvalidOffers() {
        List<Offer> offerList = offerRepository.findAllByAcceptedIsFalse() ;
        List<GeneralOfferDto> generalOfferDtos = offerList.stream().map((offer -> new GeneralOfferDto(
                offer.getId(), offer.getOwner().getId(), offer.getOwner().getName(), offer.getPosition(),
                offer.getSalary(), offer.getStartDate().toString(), offer.getEndDate().toString()
        ))).toList() ;
        return generalOfferDtos;
    }

}
