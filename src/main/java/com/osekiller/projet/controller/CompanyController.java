package com.osekiller.projet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{companyId}/offers/{offerId}")
    public ResponseEntity<MultiValueMap<String, Object>> getOffers(@PathVariable(name = "companyId") Long companyId,
                                                                   @PathVariable(name = "offerId") Long offerId) {
        OfferDtoResponse offerDtoResponse = companyService.getOffer(offerId) ;
        OfferDtoResponseNoPdf offerDtoResponseNoPdf = new OfferDtoResponseNoPdf(offerDtoResponse.offerId(),
                offerDtoResponse.position(), offerDtoResponse.salary(), offerDtoResponse.startDate(), offerDtoResponse.endDate()) ;
        MultiValueMap<String, Object> multipartBody = new LinkedMultiValueMap<>();
        multipartBody.add("offerDto", offerDtoResponseNoPdf);
        multipartBody.add("file", offerDtoResponse.offer());

        return ResponseEntity.ok().contentType(MediaType.MULTIPART_FORM_DATA).body(multipartBody) ;
    }

}
