package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.response.UserDto;
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
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(UserDto::from).toList() ;
    }

    public void validateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.setEnabled(true);
        userRepository.save(user) ;
    }

    public void invalidateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userRepository.delete(user);
    }
}
