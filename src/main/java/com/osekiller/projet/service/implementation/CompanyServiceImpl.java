package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.response.InternDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.repository.ContractRepository;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository ;

    private OfferRepository offerRepository ;

    private ContractRepository contractRepository ;

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
    public List<OfferDtoResponseNoPdf> getOffersByCompany(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId) ;
        if (company.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        List<Offer> offerList = offerRepository.findAllByOwner(company.get()) ;

        List<OfferDtoResponseNoPdf> offerDtoResponseList = new ArrayList<>() ;

        for (Offer offer: offerList) {
            offerDtoResponseList.add(new OfferDtoResponseNoPdf(offer.getId(), offer.getPosition(), offer.getSalary(),
                    offer.getStartDate().toString(), offer.getEndDate().toString(), offer.isAccepted(), offer.getFeedback())) ;
        }

        return offerDtoResponseList ;
    }

    @Override
    public void validateOffer(Long offerId, String feedback) {
        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        offer.setAccepted(true);
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
    public List<InternDto> getInterns(Long companyId) {
        companyRepository.findById(companyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

        List<InternDto> dtos = new ArrayList<>() ;

        contractRepository.findAllByOffer_Owner_Id(companyId).
                forEach(contract -> dtos.add(InternDto.from(contract)));

        return dtos ;
    }

}
