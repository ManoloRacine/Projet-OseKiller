package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.UserInfoDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.OfferService;
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
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository ;
    private CompanyRepository companyRepository ;
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
        student.getApplications().add(offer);

        offerRepository.save(offer);
    }

    @Override
    public List<UserInfoDto> getApplicants(long offerId) {
        List<Student> students = offerRepository.findByIdAndFetchApplicants(offerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getApplicants();


        return students.stream().map(applicant -> new UserInfoDto(applicant.getId(), applicant.getName(), applicant.getEmail())).toList();
    }

    @Override
    public void modifyOffer(long offerId, OfferDto offerDto, MultipartFile file) {

    }

    @Override
    public List<GeneralOfferDto> getAllValidOffers() {
        List<Offer> offerList = offerRepository.findAllByAcceptedIsTrue() ;
        return offerList.stream().map(GeneralOfferDto::from).toList();
    }

    @Override
    public List<GeneralOfferDto> getAllInvalidOffers() {
        List<Offer> offerList = offerRepository.findAllByAcceptedIsFalseAndFeedbackIsNull();
        return offerList.stream().map(GeneralOfferDto::from).toList();
    }

    @Override
    public void addOffer(long companyId, OfferDto offerDto, MultipartFile file) {
        Optional<Company> companyOptional = companyRepository.findById(companyId) ;

        if (companyOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        Offer offer = new Offer(companyOptional.get(), offerDto.position(), offerDto.salary(), LocalDate.parse(offerDto.startDate()), LocalDate.parse(offerDto.endDate())) ;

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        offer.setPdfName(fileName);

        try {
            offer.setPdf(file.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        offerRepository.save(offer) ;
    }
}
