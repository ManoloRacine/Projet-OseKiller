package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.UsersDto;

import java.util.List;

public interface UserService {
    List<UsersDto> getUsers();
    void invalidateUser(String email);
    void validateUser(String emailValidated);
}
