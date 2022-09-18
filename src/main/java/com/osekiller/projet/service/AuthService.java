package com.osekiller.projet.service;

import com.osekiller.projet.controller.request.SignInDto;
import com.osekiller.projet.controller.request.SignUpDto;
import com.osekiller.projet.model.User;

public interface AuthService {
    String signIn(SignInDto dto);

    User signUp(SignUpDto dto);

}
