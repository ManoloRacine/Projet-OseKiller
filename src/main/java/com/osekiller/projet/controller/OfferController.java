package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.service.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/offers")
public class OfferController {

    OfferService offerService;

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
    public ResponseEntity<Void> applyToInternshipOffer(@PathVariable Long id){

        return null;
    }
}
