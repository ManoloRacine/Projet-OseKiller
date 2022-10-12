package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.model.CV;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.service.CompanyService;
import com.osekiller.projet.service.ResourceFactory;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository ;

    private OfferRepository offerRepository ;

    private final Path cvPath = Paths.get("OFFER");

    @Override
    public void addOffer(Long companyId, OfferDto offerDto, MultipartFile file) {
        Optional<Company> companyOptional = companyRepository.findById(companyId) ;

        if (companyOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        Offer offer = new Offer(companyOptional.get(), offerDto.position(), offerDto.salary(), LocalDate.parse(offerDto.startDate()), LocalDate.parse(offerDto.endDate()), false) ;

        offerRepository.save(offer) ;

        try {
            Path path = cvPath.resolve(offer.getId() + ".pdf");
            if (path.toFile().exists()) {
                Files.delete(path);
            }
            Files.copy(file.getInputStream(), path);
            offer.setPath(path.toString());
            offerRepository.save(offer);
        } catch (IOException e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public OfferDtoResponse getOffer(Long offerId) {

        Optional<Offer> offer = offerRepository.findById(offerId) ;

        if (offer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        Offer offer1 = offer.get() ;

        try {
            Path file = cvPath.resolve(offerId.toString() + ".pdf");
            Resource resource = new UrlResource(file.toUri());
            return new OfferDtoResponse(offer1.getId(), offer1.getPosition(), offer1.getSalary(), offer1.getStartDate().toString(), offer1.getEndDate().toString(), resource) ;
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public List<OfferDtoResponse> getAllOffersCompany(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId) ;
        if (company.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        List<Offer> offerList = offerRepository.findAllByOwner(company.get()) ;

        List<OfferDtoResponse> offerDtoResponseList = new ArrayList<>() ;

        for (Offer offer: offerList) {
            try {
                Path file = cvPath.resolve(offer.getId().toString() + ".pdf");
                Resource resource = new UrlResource(file.toUri());
                offerDtoResponseList.add(new OfferDtoResponse(offer.getId(), offer.getPosition(), offer.getSalary(),
                        offer.getStartDate().toString(), offer.getEndDate().toString(), resource)) ;
            } catch (MalformedURLException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return offerDtoResponseList ;
    }

    public void init() {
        try {
            Files.createDirectory(cvPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    public void deleteAll() {
        offerRepository.deleteAll();
        FileSystemUtils.deleteRecursively(cvPath.toFile());
    }

}