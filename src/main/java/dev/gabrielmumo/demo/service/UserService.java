package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> signup(UserDto user) throws Exception;
}
