package dev.gabrielmumo.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Login {

    public record Request(
            @Email(
                    regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                    flags = Pattern.Flag.CASE_INSENSITIVE,
                    message = "Email is not a valid email address."
            )
            String username,

            @NotBlank(message = "The password is required")
            String password){}

    public record Response(String accessToken, String refreshToken, String tokenType){}
}
