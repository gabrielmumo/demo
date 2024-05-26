package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.dto.Login;
import dev.gabrielmumo.demo.security.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;

    @Value("${dev.gabrielmumo.security.token-type}")
    private String tokenType;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtManager jwtManager) {
        this.authenticationManager = authenticationManager;
        this.jwtManager = jwtManager;
    }

    @Override
    public Optional<Login.Response> login(Login.Request login) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.username(), login.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return Optional.of(
                new Login.Response(
                        jwtManager.generateTkn(authentication),
                        jwtManager.generateRefreshTkn(authentication),
                        tokenType
                )
        );
    }

    @Override
    public String refresh() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return jwtManager.generateTkn(authentication);
    }
}
