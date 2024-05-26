package dev.gabrielmumo.demo.controller;

import dev.gabrielmumo.demo.dto.Login;
import dev.gabrielmumo.demo.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/authentication")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Login.Response> login(@Valid @RequestBody Login.Request login) {
        var optionalResponse = loginService.login(login);
        return optionalResponse.map(logged -> new ResponseEntity<>(logged, HttpStatus.OK))
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh() {
        var token = loginService.refresh();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
