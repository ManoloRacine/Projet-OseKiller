package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.request.SignInDto;
import com.osekiller.projet.controller.payload.request.SignUpDto;
import com.osekiller.projet.controller.payload.response.AuthPingDto;
import com.osekiller.projet.controller.payload.response.JwtResponseDto;
import com.osekiller.projet.controller.payload.response.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    JwtResponseDto signIn(SignInDto dto);
    void signUp(SignUpDto dto);
    void signOut(String token);
    JwtResponseDto refresh(String token);
    UserDto getUserFromToken(String token);


}
