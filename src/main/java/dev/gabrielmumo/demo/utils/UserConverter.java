package dev.gabrielmumo.demo.utils;

import dev.gabrielmumo.demo.dto.Signup;
import dev.gabrielmumo.demo.model.Role;
import dev.gabrielmumo.demo.model.User;
import dev.gabrielmumo.demo.repository.RoleRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserConverter(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    
    public User toEntity(Signup.Request user) throws BadRequestException {
        User entity = new User();
        entity.setUsername(user.username());
        entity.setPassword(passwordEncoder.encode(user.password()));
        entity.setName(user.name());
        entity.setLastname(user.lastname());
        entity.setBirthdate(user.birthday());
        entity.setRoles(mapToRoles(List.of(Role.Roles.USER)));
        return entity;
    }

    private List<Role> mapToRoles(List<Role.Roles> roles) throws BadRequestException {
        if(CollectionUtils.isEmpty(roles)) throw new BadRequestException("Roles are required!");
        return roles.stream().map(roleRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
