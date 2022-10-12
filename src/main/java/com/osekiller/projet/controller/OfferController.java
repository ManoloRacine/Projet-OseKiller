package com.osekiller.projet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osekiller.projet.controller.payload.request.OfferDto;
import com.osekiller.projet.controller.payload.response.OfferDtoResponse;
import com.osekiller.projet.controller.payload.response.OfferDtoResponseNoPdf;
import com.osekiller.projet.controller.payload.response.GeneralOfferDto;
import com.osekiller.projet.service.CompanyService;
import com.osekiller.projet.service.OfferService;
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
