package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.model.Role;
import dev.gabrielmumo.demo.model.User;
import dev.gabrielmumo.demo.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Log LOG = LogFactory.getLog(UserDetailServiceImpl.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    var message = String.format("Username %s not found", username);
                    LOG.error(message);
                    return new UsernameNotFoundException(message);
                });

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                convertRolesToAuthorities(user.getRoles())
        );
    }

    private List<GrantedAuthority> convertRolesToAuthorities(List<Role> roles) {
        if(CollectionUtils.isEmpty(roles)) return new ArrayList<>();
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toList());
    }
}
