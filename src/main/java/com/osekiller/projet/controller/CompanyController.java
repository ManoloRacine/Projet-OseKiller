package com.osekiller.projet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.request.ValidationDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/companies")
public class CompanyController {

    CompanyService companyService ;

    @PostMapping("/{id}/offers")
    public ResponseEntity<Void> postOffer(@RequestParam(name = "offerDto") String offerDto,
                                          @RequestParam(name = "file") MultipartFile file,
                                          @PathVariable(name = "id") Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        OfferDto offerDto1 = mapper.readValue(offerDto, OfferDto.class) ;
        companyService.addOffer(id, offerDto1, file);
        return ResponseEntity.accepted().build() ;
    }

    @GetMapping("/{id}/offers")
    public ResponseEntity<List<OfferDtoResponseNoPdf>> getOffers(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(companyService.getAllOffersCompany(id)) ;
    }

    @GetMapping("/{companyId}/offers/{offerId}/pdf")
    public ResponseEntity<Resource> getOfferPdf(@PathVariable(name = "companyId") Long companyId,
                                              @PathVariable(name = "offerId") Long offerId) {

        if(!companyService.companyExists(companyId) || !companyService.companyOwnsOffer(companyId, offerId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        OfferDtoResponse offerDtoResponse = companyService.getOffer(offerId) ;

        return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA).body(offerDtoResponse.offer()) ;
    }

    @GetMapping("/{companyId}/offers/{offerId}")
    public ResponseEntity<OfferDtoResponseNoPdf> getOffers(@PathVariable(name = "companyId") Long companyId,
                                              @PathVariable(name = "offerId") Long offerId) {

        if(!companyService.companyExists(companyId) || !companyService.companyOwnsOffer(companyId, offerId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        OfferDtoResponse offerDtoResponse = companyService.getOffer(offerId) ;
        OfferDtoResponseNoPdf offerDtoResponseNoPdf = new OfferDtoResponseNoPdf(offerDtoResponse.offerId(),
                offerDtoResponse.position(), offerDtoResponse.salary(), offerDtoResponse.startDate(),
                offerDtoResponse.endDate()) ;

        return ResponseEntity.ok().body(offerDtoResponseNoPdf) ;
    }

    @PostMapping("/{companyId}/offers/{offerId}/validate")
    public ResponseEntity<Void> validateOffer(@PathVariable(name = "companyId") Long companyId,
                                              @PathVariable(name = "offerId") Long offerId,
                                              @Valid @RequestBody ValidationDto dto) {

        if(!companyService.companyExists(companyId) || !companyService.companyOwnsOffer(companyId, offerId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (dto.validation()){
            companyService.validateOffer(offerId, dto.feedBack());
        } else {
            companyService.invalidateOffer(offerId, dto.feedBack());
        }

        return ResponseEntity.ok().build();
    }

}
