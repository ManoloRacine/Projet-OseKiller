package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.response.UsersDto;
import com.osekiller.projet.model.user.User;
import com.osekiller.projet.repository.user.UserRepository;
import com.osekiller.projet.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Override
    public List<UsersDto> getUsers() {
        return userRepository.findAll().stream().map(
                user -> new UsersDto(user.getEmail(), user.getName(), user.isEnabled())
        ).toList() ;
    }

    public void validateUser(String emailValidated) {
        User user = userRepository.findByEmail(emailValidated).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setEnabled(true);
        userRepository.save(user) ;
    }

    public void invalidateUser(String invalidatedEmail) {
        User user = userRepository.findByEmail(invalidatedEmail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userRepository.delete(user);
    }
}
