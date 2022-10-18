package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.NameAndEmailDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository ;

    private StudentRepository studentRepository;
    @Override
    public OfferDtoResponse getOffer(long offerId) {

        Optional<Offer> offer = offerRepository.findById(offerId) ;

        if (offer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        Offer offer1 = offer.get() ;

        Resource resource = new ByteArrayResource(offer1.getPdf()) ;

        return new OfferDtoResponse(offer1.getId(), offer1.getPosition(), offer1.getSalary(), offer1.getStartDate().toString(),
                offer1.getEndDate().toString(), resource) ;

    }
    @Override
    public void addApplicantToOffer(long studentId, long offerId) {
        Offer offer = offerRepository.findByIdAndFetchApplicants(offerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(offer.getApplicants().contains(student))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        offer.getApplicants().add(student);

        offerRepository.save(offer);
    }

    @Override
    public List<NameAndEmailDto> getApplicants(long offerId) {
        List<Student> students = offerRepository.findByIdAndFetchApplicants(offerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getApplicants();


        return students.stream().map(applicant -> new NameAndEmailDto(applicant.getName(), applicant.getEmail())).toList();
    }

    @Override
    public List<GeneralOfferDto> getAllValidOffers() {
        List<Offer> offerList = offerRepository.findAllByAcceptedIsTrue() ;
        List<GeneralOfferDto> generalOfferDtos = offerList.stream().map(GeneralOfferDto::from).toList() ;
        return generalOfferDtos;
    }

    @Override
    public List<GeneralOfferDto> getAllInvalidOffers() {
        List<Offer> offerList = offerRepository.findAllByAcceptedIsFalseAndFeedbackIsNull();
        List<GeneralOfferDto> generalOfferDtos = offerList.stream().map(GeneralOfferDto::from).toList() ;
        return generalOfferDtos;
    }
}
