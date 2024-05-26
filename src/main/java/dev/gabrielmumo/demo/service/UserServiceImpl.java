package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.dto.UserDto;
import dev.gabrielmumo.demo.repository.UserRepository;
import dev.gabrielmumo.demo.utils.UserConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private static final Log LOG = LogFactory.getLog(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public Optional<UserDto> signup(UserDto user) throws Exception {
        if(userRepository.existsByUsername(user.username())) {
            var message = String.format("Username already exists %s", user.username());
            LOG.error(message);
            throw new BadRequestException(message);
        }
        userRepository.save(userConverter.toEntity(user));
        return Optional.of(user);
    }
}
