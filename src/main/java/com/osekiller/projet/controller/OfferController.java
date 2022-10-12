package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class OfferController {

    OfferService offerService;

    @GetMapping("/offers")
    public ResponseEntity<List<GeneralOfferDto>> getAllValidOffers(@RequestParam String accepted) {

        if (accepted.equals("true")) {
            return ResponseEntity.ok().body(offerService.getAllValidOffers()) ;
        } else if (accepted.equals("false")) {
            return ResponseEntity.ok().body(offerService.getAllInvalidOffers()) ;
        }

        return ResponseEntity.badRequest().build() ;
    }
}
