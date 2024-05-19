package dev.gabrielmumo.demo.controller;

import dev.gabrielmumo.demo.dto.UserDto;
import dev.gabrielmumo.demo.service.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody UserDto userDto) throws Exception {
        Optional<UserDto> user = userService.signup(userDto);
        return user.map(saved -> new ResponseEntity<>(saved, HttpStatus.CREATED))
                .orElseThrow();
    }
}