package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.dto.LoginDto;

public interface AuthenticationService {
    void login(LoginDto loginDto);
}
