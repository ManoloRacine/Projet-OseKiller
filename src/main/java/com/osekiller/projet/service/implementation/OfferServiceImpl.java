package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.UserInfoDto;
import com.osekiller.projet.model.ERole;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.repository.user.StudentRepository;
import com.osekiller.projet.service.NotificationsService;
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

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private OfferRepository offerRepository ;
    private CompanyRepository companyRepository ;
    private StudentRepository studentRepository;

    private NotificationsService notificationsService;

    private final int LAST_MONTH = 5 ;
    private final int LAST_DAY = 31 ;
    @Override
    public OfferDtoResponse getOffer(long offerId) {

        Offer offer = offerRepository.findById(offerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Resource resource = new ByteArrayResource(offer.getPdf()) ;

        return new OfferDtoResponse(offer.getId(), offer.getPosition(), offer.getSalary(), offer.getStartDate().toString(),
                offer.getEndDate().toString(), resource, offer.isAccepted(), offer.getFeedback()) ;

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
    public void acceptApplicantForOffer(long studentId, long offerId) {
        Offer offer = offerRepository.findByIdAndFetchApplicants(offerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!offer.getApplicants().contains(student))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        if(offer.getAcceptedApplicants().contains(student))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        offer.getAcceptedApplicants().add(student);
        student.getAcceptedApplications().add(offer);

        offerRepository.save(offer);

        notificationsService.addNotification(studentId,
                "vous avez été choisis pour un stage avec " + offer.getOwner().getName() + " en tant que " +
                offer.getPosition());
    }

    @Override
    public List<UserInfoDto> getApplicants(long offerId) {
        List<Student> students = offerRepository.findByIdAndFetchApplicants(offerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getApplicants();

        Offer offer =  offerRepository.findByIdAndFetchApplicants(offerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));


        return students.stream().map(applicant -> new UserInfoDto(applicant.getId(), applicant.getName(), applicant.getEmail(), applicant.getAcceptedApplications().contains(offer))).toList();
    }

    @Override
    public void modifyOffer(long offerId, OfferDto offerDto, MultipartFile file) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(offer.isAccepted())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if(file != null && !file.isEmpty()){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            offer.setPdfName(fileName);
            try {
                offer.setPdf(file.getBytes());
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        offer.setPosition(offerDto.position());
        offer.setSalary(offerDto.salary());
        offer.setStartDate(LocalDate.parse(offerDto.startDate()));
        offer.setEndDate(LocalDate.parse(offerDto.endDate()));

        offerRepository.save(offer);
    }


    @Override
    public List<GeneralOfferDto> getAllValidOffers() {
        List<Offer> offerList = offerRepository.findAllByAcceptedIsTrue() ;
        return offerList.stream().map(GeneralOfferDto::from).toList();
    }

    public List<GeneralOfferDto> getAllValidOffersBySession(int session) {
        List<Offer> offerList = offerRepository.findAllByAcceptedIsTrue() ;
        return offerList.stream().filter(
                offer -> offer.getStartDate().isAfter(LocalDate.of(session - 1, LAST_MONTH, LAST_DAY))
                        && offer.getStartDate().isBefore(LocalDate.of(session, LAST_MONTH, LAST_DAY)))
                .map(GeneralOfferDto::from).toList();
    }

    @Override
    public List<GeneralOfferDto> getAllInvalidOffers() {
        List<Offer> offerList = offerRepository.findAllByAcceptedIsFalseAndFeedbackIsNull();
        return offerList.stream().map(GeneralOfferDto::from).toList();
    }

    @Override
    public void addOffer(long companyId, OfferDto offerDto, MultipartFile file) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)) ;

        Offer offer = new Offer(company, offerDto.position(), offerDto.salary(), LocalDate.parse(offerDto.startDate()), LocalDate.parse(offerDto.endDate())) ;

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        offer.setPdfName(fileName);

        try {
            offer.setPdf(file.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        offerRepository.save(offer);

        notificationsService.addNotificationForRole(ERole.STUDENT.name(), "Une offre de " + company.getName() +
                "a été ajoutée");
    }

    public List<GeneralOfferDto> getAllInvalidOffersBySession(int session) {
        List<Offer> offerList = offerRepository.findAllByAcceptedIsFalseAndFeedbackIsNull() ;
        return offerList.stream().filter(offer -> offer.getStartDate().isAfter(LocalDate.of(session - 1, LAST_MONTH, LAST_DAY))
                        && offer.getStartDate().isBefore(LocalDate.of(session, LAST_MONTH, LAST_DAY)))
                .map(GeneralOfferDto::from).toList();
    }
}
