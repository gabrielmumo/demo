package dev.gabrielmumo.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/greetings")
public class Greeting {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hi there");
    }

    @GetMapping("/bye")
    public ResponseEntity<String> sayGoodbye() {
        return ResponseEntity.ok("Goodbye folks!");
    }
}
