package db.test;

import config.BaseDbSpec;
import db.dao.UserDao;
import db.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import testdata.builders.ExpectedMessages;
import testdata.builders.TestDataGenerator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Tag("db")
@DisplayName("CRUD тесты для UserDao")
public class DbCrudTest extends BaseDbSpec {

    @Test
    @Tag("success")
    @DisplayName("CREATE: пользователь создаётся в БД")
    void shouldCreateUser() throws SQLException {
        User user = User.of(
                TestDataGenerator.name(),
                TestDataGenerator.uniqueEmail(),
                TestDataGenerator.password()
        );

        int id = userDao.create(user);

        User created = userDao.findByEmail(user.email());
        assertThat(created).isNotNull();
        assertThat(created.id()).isEqualTo(id);
        assertThat(created.name()).isEqualTo(user.name());
    }

    @Test
    @Tag("validation")
    @DisplayName("CREATE: не создаётся пользователь с дублирующимся email")
    void shouldNotCreateDuplicateEmail() throws SQLException {
        User first = givenUserInDb();

        User duplicate = User.of(
                TestDataGenerator.name(),
                first.email(),
                TestDataGenerator.password()
        );

        assertThatThrownBy(() -> userDao.create(duplicate))
                .isInstanceOf(SQLException.class)
                .hasMessageContaining(ExpectedMessages.UNIQUE_EMAIL_VIOLATION);
    }

    @Test
    @Tag("success")
    @DisplayName("READ: findByEmail возвращает созданного пользователя")
    void shouldFindUserByEmail() throws SQLException {
        User user = givenUserInDb();

        User found = userDao.findByEmail(user.email());

        assertThat(found).isNotNull();
        assertThat(found.email()).isEqualTo(user.email());
    }

    @Test
    @Tag("success")
    @DisplayName("UPDATE: имя пользователя обновляется")
    void shouldUpdateUser() throws SQLException {
        User user = givenUserInDb();
        String newName = TestDataGenerator.name();
        User updated = new User(user.id(), newName, user.email(), user.password());

        boolean result = userDao.update(updated);

        assertThat(result).isTrue();
        assertThat(userDao.findByEmail(user.email()).name()).isEqualTo(newName);
    }

    @Test
    @Tag("success")
    @DisplayName("DELETE: пользователь удаляется из БД")
    void shouldDeleteUser() throws SQLException {
        User user = givenUserInDb();

        boolean result = userDao.delete(user.id());

        assertThat(result).isTrue();
        assertThat(userDao.findByEmail(user.email())).isNull();
    }

}