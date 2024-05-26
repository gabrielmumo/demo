package dev.gabrielmumo.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public record UserDto (

        @Email(
                regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "Email is not a valid email address."
        )
        String username,
        @NotBlank(message = "The password is required")
        String password,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Lastname is required")
        String lastname,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthday) {


}
