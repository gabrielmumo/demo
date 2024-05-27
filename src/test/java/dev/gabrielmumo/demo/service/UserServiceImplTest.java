package dev.gabrielmumo.demo.service;

import dev.gabrielmumo.demo.dto.Signup;
import dev.gabrielmumo.demo.model.Role;
import dev.gabrielmumo.demo.model.User;
import dev.gabrielmumo.demo.repository.UserRepository;
import dev.gabrielmumo.demo.utils.UserConverter;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private final String USERNAME = "nicotesla@gmail.com";

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UserConverter userConverterMock;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    public void shouldThrowBadRequestExceptionWhenUserAlreadyExists() {
        var user = buildTestUser();
        Mockito.when(userRepositoryMock.existsByUsername(USERNAME)).thenReturn(true);

        Exception exception = assertThrows(BadRequestException.class, () ->
                userServiceImpl.signup(user)
        );

        assertTrue(
                exception.getMessage().contains("Username already exists"),
                String.format("User %s already stored", USERNAME)
        );
    }

    @Test
    public void shouldSignupUser() throws Exception {
        var requestUser = buildTestUser();
        var savedEntity = buildEntityUser(requestUser);
        Mockito.when(userRepositoryMock.existsByUsername(USERNAME)).thenReturn(false);
        Mockito.when(userConverterMock.toEntity(requestUser)).thenReturn(savedEntity);
        Mockito.when(userRepositoryMock.save(savedEntity)).thenReturn(savedEntity);

        var savedUser = userServiceImpl.signup(requestUser);

        assertTrue(savedUser.isPresent(), "User should be stored");
        assertEquals(requestUser.username(), savedUser.get().username());
        assertEquals(requestUser.name(), savedUser.get().name());
        assertEquals(requestUser.lastname(), savedUser.get().lastname());
    }

    private User buildEntityUser(Signup.Request user) {
        User entity = new User();
        entity.setId(1);
        entity.setUsername(user.username());
        entity.setPassword(user.password());
        entity.setName(user.name());
        entity.setLastname(user.lastname());
        entity.setBirthdate(user.birthday());
        entity.setRoles(List.of(new Role()));
        return entity;
    }

    private Signup.Request buildTestUser() {
        return new Signup.Request(
                USERNAME,
                "pwd-123",
                "Nicola",
                "Tesla",
                new GregorianCalendar(1856, Calendar.JULY, 10).getTime()
        );
    }
}