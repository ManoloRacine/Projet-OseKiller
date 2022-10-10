package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.model.CV;
import com.osekiller.projet.model.Offer;
import com.osekiller.projet.model.user.Company;
import com.osekiller.projet.repository.OfferRepository;
import com.osekiller.projet.repository.user.CompanyRepository;
import com.osekiller.projet.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository ;

    private OfferRepository offerRepository ;

    private final Path cvPath = Paths.get("OFFER");

    @Override
    public void addOffer(Long companyId, OfferDto offerDto) {
        Optional<Company> companyOptional = companyRepository.findById(companyId) ;

        if (companyOptional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND) ;

        Offer offer = new Offer(companyOptional.get(), offerDto.position(), offerDto.salary(), LocalDate.parse(offerDto.startDate()), LocalDate.parse(offerDto.endDate())) ;

        offerRepository.save(offer) ;


        /*
        try {
            Path path = cvPath.resolve(offer.getId() + ".pdf");
            if (path.toFile().exists()) {
                Files.delete(path);
            }
            Files.copy(offerDto.offer().getInputStream(), path);
            CV newCV = cvRepository.save(new CV(cvPath.toString(), student.get(), false));
            student.get().setCv(newCV);
            student.get().setCvRejected(false);
            studentRepository.save(student.get());
        } catch (IOException e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }

    @Override
    public OfferDtoResponse getOffer(Long offerId, Long companyId) {
        return null;
    }

    @Override
    public List<OfferDtoResponse> getAllOffersCompany(Long companyId) {
        return null;
    }
}
