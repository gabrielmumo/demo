package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.dto.Login;

import java.util.Optional;

public interface LoginService {
    Optional<Login.Response> login(Login.Request login);
    String refresh();
}
