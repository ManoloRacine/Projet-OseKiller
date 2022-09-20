package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.SignInDto;
import com.osekiller.projet.controller.payload.request.SignUpDto;
import com.osekiller.projet.controller.payload.response.AuthPingDto;
import com.osekiller.projet.controller.payload.response.JwtResponseDto;
import com.osekiller.projet.controller.payload.response.UsersDto;

import java.util.List;

public interface AuthService {
    JwtResponseDto signIn(SignInDto dto);
    void signUp(SignUpDto dto);
    void signOut(String token);
    JwtResponseDto refresh(String refreshToken);
    AuthPingDto authPing(String refreshToken);

    List<UsersDto> getUsers();
}
