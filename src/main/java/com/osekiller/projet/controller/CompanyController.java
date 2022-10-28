package com.osekiller.projet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.request.ValidationDto;
import com.osekiller.projet.controller.payload.response.UserInfoDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.service.CompanyService;
import com.osekiller.projet.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    OfferService offerService;

    @PostMapping("/{id}/offers")
    public ResponseEntity<Void> postOffer(@RequestParam(name = "offerDto") String offerDto,
                                          @RequestParam(name = "file") MultipartFile file,
                                          @PathVariable(name = "id") Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        OfferDto offerDto1 = mapper.readValue(offerDto, OfferDto.class) ;
        offerService.addOffer(id, offerDto1, file);
        return ResponseEntity.accepted().build() ;
    }

    @GetMapping("/{id}/offers")
    public ResponseEntity<List<OfferDtoResponseNoPdf>> getOffers(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(companyService.getOffersByCompany(id)) ;
    }

    @GetMapping("/{companyId}/offers/{offerId}/pdf")
    public ResponseEntity<Resource> getOfferPdf(@PathVariable(name = "companyId") Long companyId,
                                                @PathVariable(name = "offerId") Long offerId) {

        if(!companyService.companyExists(companyId) || !companyService.companyOwnsOffer(companyId, offerId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        OfferDtoResponse offerDtoResponse = offerService.getOffer(offerId) ;

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + offerDtoResponse.offer().getFilename() +
                        "\"").body(offerDtoResponse.offer());
    }

    @GetMapping("/{companyId}/offers/{offerId}/applicants")
    public ResponseEntity<List<UserInfoDto>> getOfferApplicants(@PathVariable(name = "companyId") Long companyId,
                                                                @PathVariable(name = "offerId") Long offerId) {

        if(!companyService.companyExists(companyId) || !companyService.companyOwnsOffer(companyId, offerId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(offerService.getApplicants(offerId));
    }

    @PutMapping("/{companyId}/offers/{offerId}")
    public ResponseEntity<Void> updateOffer (@PathVariable(name = "companyId") Long companyId,
                                             @PathVariable(name = "offerId") Long offerId,
                                             @RequestParam(name = "offerDto") String offerDtoString,
                                             @RequestParam(name = "file") MultipartFile file) throws JsonProcessingException {

        if(!companyService.companyExists(companyId) || !companyService.companyOwnsOffer(companyId, offerId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        ObjectMapper mapper = new ObjectMapper();
        OfferDto offerDto = mapper.readValue(offerDtoString, OfferDto.class) ;

        offerService.modifyOffer(offerId,offerDto,file);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{companyId}/offers/{offerId}")
    public ResponseEntity<OfferDtoResponseNoPdf> getOffer(@PathVariable(name = "companyId") Long companyId,
                                                                   @PathVariable(name = "offerId") Long offerId) {

        if(!companyService.companyExists(companyId) || !companyService.companyOwnsOffer(companyId, offerId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        OfferDtoResponse offerDtoResponse = offerService.getOffer(offerId) ;
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
