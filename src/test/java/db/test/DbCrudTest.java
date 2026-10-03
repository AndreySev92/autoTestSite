package db.test;

import config.BaseDbSpec;
import db.config.DbCleaner;
import db.config.DbConnection;
import db.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import testdata.builders.ExpectedMessages;
import testdata.builders.TestDataGenerator;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static testdata.builders.ExpectedMessages.NULL_IN_VALUE;

@Tag("db")
@DisplayName("CRUD тесты для UserDao")
public class DbCrudTest extends BaseDbSpec {

    @BeforeEach
    void cleanDb() throws SQLException {
        DbCleaner.truncateUsers();
    }

    @Test
    @Tag("success")
    @DisplayName("CREATE: пользователь создаётся в БД")
    void shouldCreateUserTest() throws SQLException {
        User user = userDataBuilder.randomUser();

        int id = userDao.create(user);

        User created = userDao.findByEmail(user.email());
        assertThat(created).isNotNull();
        assertThat(created.id()).isEqualTo(id);
        assertThat(created)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(user);
    }

    @ParameterizedTest(name = "SQL-инъекция: email=''{0}''")
    @MethodSource("testdata.builders.SecurityTestData#sqlInjectionEmails")
    @Tag("security")
    @DisplayName("CREATE: SQL инъекция через Email безопасна")
    void shouldNotSqlInjection(String injectionEmail) throws SQLException {
        User user = userDataBuilder.userWithEmail(injectionEmail);

        int id = userDao.create(user);

        User created = userDao.findByEmail(injectionEmail);
        assertThat(created).isNotNull();
        assertThat(created.id()).isEqualTo(id);
        assertThat(userDao.findAll()).isNotEmpty();
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

    @ParameterizedTest(name = "CREATE: не создаётся пользователь с {0}")
    @MethodSource("testdata.builders.CheckNullWithCreate#userWithNullNameAndEmail")
    @Tag("validation")
    @DisplayName("CREATE: не создаётся пользователь с null в обязательном поле")
    void sholdNotCreateWithNullName(User user){

        assertThatThrownBy(() -> userDao.create(user))
                .isInstanceOf(SQLException.class)
                .hasMessageContaining(NULL_IN_VALUE);

    }


    @Test
    @Tag("success")
    @DisplayName("READ: findByEmail возвращает созданного пользователя")
    void shouldNevalidNotEmail() throws SQLException {
        User user = givenUserInDb();

        User found = userDao.findByEmail(user.email());

        assertThat(found).isEqualTo(user);
    }

    @Test
    @Tag("success")
    @DisplayName("UPDATE: имя пользователя обновляется")
    void shouldUpdateUser() throws SQLException {
        User user = givenUserInDb();
        String newName = userDataBuilder.randomUser().name();

        User updated = new User(user.id(), newName, user.email(), user.password());
        boolean result = userDao.update(updated);

        assertThat(result).isTrue();

        User afterUpdate = userDao.findByEmail(user.email());
        assertThat(afterUpdate).isEqualTo(updated);
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