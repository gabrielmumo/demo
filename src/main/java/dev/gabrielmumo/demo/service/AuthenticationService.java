package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.dto.LoggedDto;
import dev.gabrielmumo.demo.dto.LoginDto;

public interface AuthenticationService {
    LoggedDto login(LoginDto loginDto);
    String refresh();
}
