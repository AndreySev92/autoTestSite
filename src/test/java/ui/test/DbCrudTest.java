package ui.test;

import db.dao.UserDao;
import db.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DbCrudTest {

    private final UserDao userDao = new UserDao();

    @Test
    @DisplayName("CRUD: создание → чтение → обновление → удаление")
    public void fullCrudFlow() throws SQLException {
        String email = "test_" + System.currentTimeMillis() + "@mail.com";
        User newUser = User.of("Test User", email, "pass123");

        int id = userDao.create(newUser);
        assertThat(id).isPositive();

        User found = userDao.findByEmail(email);
        assertThat(found).isNotNull();
        assertThat(found.name()).isEqualTo("Test User");
        assertThat(found.email()).isEqualTo(email);

        User updated = new User(found.id(), "Updated Name", found.email(), found.password());
        assertThat(userDao.update(updated)).isTrue();

        User afterUpdate = userDao.findByEmail(email);
        assertThat(afterUpdate.name()).isEqualTo("Updated Name");

        assertThat(userDao.delete(id)).isTrue();
        assertThat(userDao.findByEmail(email)).isNull();
    }
}