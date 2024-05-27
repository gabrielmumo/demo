package dev.gabrielmumo.demo.repository;

import dev.gabrielmumo.demo.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    private final String USERNAME = "nicotesla@gmail.com";

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public void setup() {
        User user = buildTestUser();

        userRepository.save(user);
    }

    @Test
    public void shouldSaveSingleUser() {
        User user = buildTestUser();
        user.setUsername("nicolatesla@gmail.com");

        var savedUser = userRepository.save(user);

        assertNotNull(savedUser, "User should be saved");
        assertTrue(savedUser.getId() > 0, "Should have a user id");
    }

    @Test
    public void shouldThrowUniquenessConstraintException() {
        User user = buildTestUser();

        Exception exception = assertThrows(DataIntegrityViolationException.class, () ->
                userRepository.save(user)
        );

        assertTrue(exception.getMessage().contains("Unique index or primary key violation"));
    }

    @Test
    public void shouldThrowNotNullConstraintException() {
        User user = buildTestUser();
        user.setUsername(null);
        checkNotNullExceptionIsThrown(user, "username");

        user = buildTestUser();
        user.setPassword(null);
        checkNotNullExceptionIsThrown(user, "password");

        user = buildTestUser();
        user.setName(null);
        checkNotNullExceptionIsThrown(user, "name");

        user = buildTestUser();
        user.setLastname(null);
        checkNotNullExceptionIsThrown(user, "lastname");

        user = buildTestUser();
        user.setBirthdate(null);
        checkNotNullExceptionIsThrown(user, "birthdate");
    }

    private void checkNotNullExceptionIsThrown(User user, String field) {
        Exception exception = assertThrows(DataIntegrityViolationException.class, () ->
                userRepository.save(user)
        );

        assertTrue(
                exception.getMessage().contains("NULL not allowed for column"),
                String.format("Field %s should not allow null values", field)
        );
    }

    @Test
    public void shouldConfirmUserExistsByUsername() {
        Boolean userExists = userRepository.existsByUsername(USERNAME);

        assertTrue(userExists, "User should exist");
    }

    @Test
    public void shouldNotConfirmUserExistsByUsername() {
        Boolean userExists = userRepository.existsByUsername("notexisting@gmail.com");

        assertFalse(userExists, "User should not exist");
    }

    @Test
    public void shouldFindUserById() {
        var foundUser = userRepository.findById(1);

        assertTrue(foundUser.isPresent(), "Should find setup user");
        assertEquals(1, foundUser.get().getId(), "Setup user and found user should be equal");
    }

    @Test
    public void shouldNotFindNotStoredUser() {
        var foundUser = userRepository.findById(1000);

        assertFalse(foundUser.isPresent(), "Should not find not stored user");
    }

    @Test
    public void shouldFindUserByUsername() {
        var foundUser = userRepository.findByUsername(USERNAME);

        assertTrue(foundUser.isPresent(), "Should find setup user");
        assertEquals(USERNAME, foundUser.get().getUsername(), "Setup user and found user should be equal");
    }

    @Test
    public void shouldNotFindUserWithNotStoredUsername() {
        var foundUser = userRepository.findByUsername("notexisting@gmail.com");

        assertFalse(foundUser.isPresent(), "Should not find not stored user");
    }

    @Test
    public void shouldFindAllPersistedUsers() {
        var users = userRepository.findAll();

        assertNotNull(users, "Should contain the persisted users");
        assertEquals(1, users.size(), "Should contain all persisted users");
    }

    private User buildTestUser() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword("pwd-123");
        user.setName("Nicola");
        user.setLastname("Tesla");
        user.setBirthdate(new GregorianCalendar(1856, Calendar.JULY, 10).getTime());

        return user;
    }
}