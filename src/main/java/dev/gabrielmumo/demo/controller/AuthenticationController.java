package dev.gabrielmumo.demo.controller;

import dev.gabrielmumo.demo.dto.LoggedDto;
import dev.gabrielmumo.demo.dto.LoginDto;
import dev.gabrielmumo.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedDto> login(@RequestBody LoginDto loginDto) {
        var logged = authenticationService.login(loginDto);
        return new ResponseEntity<>(logged, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh() {
        var token = authenticationService.refresh();
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
