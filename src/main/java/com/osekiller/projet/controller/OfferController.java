package com.osekiller.projet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class OfferController {

    CompanyService companyService ;

    @PostMapping("/companies/{id}/offers")
    public ResponseEntity<Void> postOffer(@RequestParam(name = "offerDto") String offerDto,
                                          @RequestParam(name = "file") MultipartFile file,
                                          @PathVariable(name = "id") Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        OfferDto offerDto1 = mapper.readValue(offerDto, OfferDto.class) ;
        companyService.addOffer(id, offerDto1, file);
        return ResponseEntity.accepted().build() ;
    }

    @GetMapping("/companies/{id}/offers")
    public ResponseEntity<List<OfferDtoResponse>> getOffers(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(companyService.getAllOffersCompany(id)) ;
    }

    @GetMapping("/companies/{companyId}/offers/{offerId}")
    public ResponseEntity<OfferDtoResponse> getOffers(@PathVariable(name = "companyId") Long companyId,
                                                            @PathVariable(name = "offerId") Long offerId) {
        return ResponseEntity.ok(companyService.getOffer(offerId)) ;
    }
}
