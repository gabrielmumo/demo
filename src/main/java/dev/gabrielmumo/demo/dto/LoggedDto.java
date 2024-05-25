package dev.gabrielmumo.demo.dto;

public record LoggedDto(String accessToken, String refreshToken, String tokenType) {
}
