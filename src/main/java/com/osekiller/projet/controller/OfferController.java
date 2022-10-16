package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.security.JwtUtils;
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
    public ResponseEntity<List<GeneralOfferDto>> getAllValidOffers(@RequestParam String accepted) {

        if (accepted.equals("true")) {
            return ResponseEntity.ok().body(offerService.getAllValidOffers()) ;
        } else if (accepted.equals("false")) {
            return ResponseEntity.ok().body(offerService.getAllInvalidOffers()) ;
        }

        return ResponseEntity.badRequest().build() ;
    }

    @PostMapping("/{id}/apply")
    public ResponseEntity<Void> applyToInternshipOffer(@PathVariable(name = "id") Long offerId, @RequestHeader(HttpHeaders.AUTHORIZATION) String header){
        String jwt = header.substring(7);
        Long studentId = authService.getUserFromToken(jwt).id();
        offerService.addApplicantToOffer(offerId, studentId);
        return ResponseEntity.ok().build();
    }
}
