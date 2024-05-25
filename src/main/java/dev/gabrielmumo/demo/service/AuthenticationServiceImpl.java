package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.dto.LoggedDto;
import dev.gabrielmumo.demo.dto.LoginDto;
import dev.gabrielmumo.demo.security.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;

    @Value("${dev.gabrielmumo.security.token-type}")
    private String tokenType;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtManager jwtManager) {
        this.authenticationManager = authenticationManager;
        this.jwtManager = jwtManager;
    }

    @Override
    public LoggedDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new LoggedDto(
                jwtManager.generateTkn(authentication),
                jwtManager.generateRefreshTkn(authentication),
                tokenType
        );
    }

    @Override
    public String refresh() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return jwtManager.generateTkn(authentication);
    }
}
