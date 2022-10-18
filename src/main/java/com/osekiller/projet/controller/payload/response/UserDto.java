package com.osekiller.projet.controller.payload.response;

import com.osekiller.projet.model.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserDto(@NotBlank String email,
                      @NotBlank String name,
                      @NotNull boolean enabled,
                      @NotNull Long id,
                      @NotBlank String role) {

    public static UserDto from(User user){
        return new UserDto(user.getEmail(), user.getName(), user.isEnabled(), user.getId(), user.getRole().getName());
    }
}
