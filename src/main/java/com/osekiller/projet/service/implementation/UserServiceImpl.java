package com.osekiller.projet.service.implementation;

import com.osekiller.projet.controller.payload.response.StudentDto;
import com.osekiller.projet.controller.payload.response.UserDto;
import com.osekiller.projet.model.user.Student;
import com.osekiller.projet.model.user.User;
import com.osekiller.projet.repository.user.StudentRepository;
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
    private StudentRepository studentRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(
                this::userToDTO
        ).toList() ;
    }

    private UserDto userToDTO(User user) {
        return new UserDto(user.getEmail(), user.getName(), user.isEnabled(), user.getId(), user.getRole().getName()) ;
    }

    public List<StudentDto> getStudents() {
        return studentRepository.findAll().stream().map(
                this::studentToDto
        ).toList() ;
    }

    private StudentDto studentToDto(Student student) {
        boolean cvPresent = false ;
        if (student.getCv().getPath() != null) {
            cvPresent = true ;
        }
        return new StudentDto(student.getEmail(), student.getName(), student.getId(), student.isEnabled(), student.getCv().isValidated(), student.isCvRejected(), cvPresent) ;
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
