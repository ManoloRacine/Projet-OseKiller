package com.osekiller.projet.controller.request;

import javax.validation.constraints.NotBlank;

public record GetNewUsersRequest(
        @NotBlank(message = "token-is-mandatory") String token) {
}
