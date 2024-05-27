package dev.gabrielmumo.demo.repository;

import dev.gabrielmumo.demo.model.Role;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @BeforeAll
    public void setup() {
        Role admin = new Role();
        admin.setName(Role.Roles.ADMIN);

        roleRepository.save(admin);
    }

    @Test
    public void shouldSaveSingleRole() {
        // Given
        Role role = new Role();
        role.setName(Role.Roles.USER);

        // When
        Role savedRole = roleRepository.save(role);

        // Then
        assertNotNull(savedRole, "Role should be saved");
        assertTrue(savedRole.getId() > 0, "Should have a role id");
    }

    @Test
    public void shouldThrowUniquenessConstrainException() {
        // Given
        Role role = new Role();
        role.setName(Role.Roles.ADMIN);

        // When
        Exception exception = assertThrows(DataIntegrityViolationException.class, () ->
            roleRepository.save(role)
        );

        // Then
        assertTrue(exception.getMessage().contains("Unique index or primary key violation"));
    }

    @Test
    public void shouldThrowNotNullConstrainException() {
        // Given
        Role role = new Role();

        // When
        Exception exception = assertThrows(DataIntegrityViolationException.class, () ->{
            roleRepository.save(role);
        });

        // Then
        assertTrue(exception.getMessage().contains("NULL not allowed for column"));
    }

    @Test
    public void shouldFindById() {
        // When
        var foundRole = roleRepository.findById(1);

        // Then
        assertTrue(foundRole.isPresent(), "Should find setup role");
        assertEquals(Role.Roles.ADMIN, foundRole.get().getName(), "Setup role and found role should be equal");
    }

    @Test
    public void shouldNotFindWithNoExistingId() {
        // When
        var foundRole = roleRepository.findById(1000);

        // Then
        assertFalse(foundRole.isPresent(), "Should find not stored role");
    }

    @Test
    public void shouldFindByName() {
        // When
        var foundRole = roleRepository.findByName(Role.Roles.ADMIN);

        // Then
        assertTrue(foundRole.isPresent(), "Should find setup role by name");
        assertEquals(1, foundRole.get().getId(), "Setup role and found role should be equal");
    }

    @Test
    public void shouldNotFindWithNotStoredName() {
        // When
        var foundRole = roleRepository.findByName(Role.Roles.USER);

        // Then
        assertFalse(foundRole.isPresent(), "Should find not stored role");
    }

    @Test
    public void shouldListAllPersistedRoles() {
        // When
        var roles = roleRepository.findAll();

        // Then
        assertNotNull(roles, "Should contain the persisted roles");
        assertEquals(1, roles.size(), "Should contain all persisted roles");
    }
}