package db.test;

import db.config.DbInitializer;
import db.dao.UserDao;
import db.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import testdata.builders.TestDataGenerator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DbCrudTest {

    private final UserDao userDao = new UserDao();

    @BeforeAll
    static void initSchema() throws SQLException {
        DbInitializer.init();
    }

    @Test
    @DisplayName("CRUD: создание → чтение → обновление → удаление")
    public void fullCrudFlow() throws SQLException {
        String email = TestDataGenerator.uniqueEmail();
        String password = TestDataGenerator.password();
        String name = TestDataGenerator.name();
        User newUser = User.of(name, email, password);

        int id = userDao.create(newUser);
        assertThat(id).isPositive();

        User found = userDao.findByEmail(email);
        assertThat(found).isNotNull();
        assertThat(found.name()).isEqualTo(name);
        assertThat(found.email()).isEqualTo(email);

        String newName = TestDataGenerator.name();
        User updated = new User(found.id(), newName, found.email(), found.password());
        assertThat(userDao.update(updated)).isTrue();

        User afterUpdate = userDao.findByEmail(email);
        assertThat(afterUpdate.name()).isEqualTo(newName);

        assertThat(userDao.delete(id)).isTrue();
        assertThat(userDao.findByEmail(email)).isNull();
    }
}