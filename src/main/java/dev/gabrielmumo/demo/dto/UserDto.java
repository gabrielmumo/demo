package dev.gabrielmumo.demo.dto;

import dev.gabrielmumo.demo.model.UserEntity;

import java.util.List;

public record UserDto (
        String username,
        String password,
        List<String> roles) {


}
