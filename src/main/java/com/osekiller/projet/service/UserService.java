package com.osekiller.projet.service;

import com.osekiller.projet.controller.payload.response.StudentDto;
import com.osekiller.projet.controller.payload.response.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    List<StudentDto> getStudents();
    void invalidateUser(Long email);
    void validateUser(Long email);

    StudentDto getStudent(Long id);
}
