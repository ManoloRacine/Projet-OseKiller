package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.service.AuthService;
import com.osekiller.projet.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/offers")
public class OfferController {

    OfferService offerService;

    AuthService authService;

    @GetMapping
    public ResponseEntity<List<GeneralOfferDto>> getAllValidOffers(@RequestParam String accepted,
                                                                   @RequestParam(required = false) Integer session) {

        if (accepted.equals("true")) {
            if (session == null) {
                return ResponseEntity.ok().body(offerService.getAllValidOffers()) ;
            }
            else {
                return ResponseEntity.ok().body(offerService.getAllValidOffersBySession(session)) ;
            }
        } else if (accepted.equals("false")) {
            if (session == null) {
                return ResponseEntity.ok().body(offerService.getAllInvalidOffers()) ;
            }
            else {
                return ResponseEntity.ok().body(offerService.getAllInvalidOffersBySession(session)) ;
            }
        }

        return ResponseEntity.badRequest().build() ;
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> applyToInternshipOffer(@PathVariable(name = "id") Long offerId, @RequestHeader(HttpHeaders.AUTHORIZATION) String header){
        String jwt = header.substring(7);
        Long studentId = authService.getUserFromToken(jwt).id();
        offerService.addApplicantToOffer(studentId, offerId);
        return ResponseEntity.ok().build();
    }
}
