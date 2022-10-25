package com.osekiller.projet.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CurrentDateFactory {

    public LocalDate getCurrentDate() {
        return LocalDate.now() ;
    }
}
