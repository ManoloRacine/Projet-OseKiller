package com.osekiller.projet.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CurrentDateFactory {

    public LocalDate getCurrentDate() {
        return LocalDate.now() ;
    }
}
