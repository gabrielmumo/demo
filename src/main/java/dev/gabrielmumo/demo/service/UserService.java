package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.dto.Signup;

import java.util.Optional;

public interface UserService {
    Optional<Signup.Response> signup(Signup.Request user) throws Exception;
}
