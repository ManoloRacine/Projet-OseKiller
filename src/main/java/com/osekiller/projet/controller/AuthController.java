package com.osekiller.projet.controller;

import com.osekiller.projet.controller.payload.request.JwtRequestDto;
import com.osekiller.projet.controller.payload.request.SignInDto;
import com.osekiller.projet.controller.payload.request.SignUpDto;
import com.osekiller.projet.controller.payload.response.JwtResponseDto;
import com.osekiller.projet.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthController {
    AuthService authService;
    AuthenticationManager authManager;
    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpDto dto){
        authService.signUp(dto);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponseDto> signIn(@Valid @RequestBody SignInDto dto) {
        authenticate(dto.email(), dto.password());
       return ResponseEntity.ok(authService.signIn(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponseDto> refresh(@Valid @RequestBody JwtRequestDto dto){
        return ResponseEntity.ok(authService.refresh(dto.refreshToken()));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<JwtResponseDto> signOut(@Valid @RequestBody JwtRequestDto dto){
        authService.signOut(dto.refreshToken());
        return ResponseEntity.noContent().build();
    }

    private void authenticate(String username, String password) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"invalid-credentials");
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"user-disabled");
        }
    }

    //https://www.baeldung.com/spring-boot-bean-validation
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
