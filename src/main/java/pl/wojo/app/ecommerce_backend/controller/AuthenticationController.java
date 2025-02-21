package pl.wojo.app.ecommerce_backend.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pl.wojo.app.ecommerce_backend.api_model.LoginBody;
import pl.wojo.app.ecommerce_backend.api_model.LoginResponse;
import pl.wojo.app.ecommerce_backend.api_model.RegistrationBody;
import pl.wojo.app.ecommerce_backend.model.LocalUser;
import pl.wojo.app.ecommerce_backend.service.UserService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<LocalUser> register(@RequestBody @Valid RegistrationBody registrationBody) {
        LocalUser savedUser = userService.register(registrationBody);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(savedUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<LocalUser> login(@RequestBody LoginBody loginBody) {
        LoginResponse response = userService.login(loginBody);

        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getJwt())
            .body(response.getUser());
    }
}
