package config;

import db.config.DbInitializer;
import db.dao.UserDao;
import db.model.User;
import org.junit.jupiter.api.BeforeAll;
import testdata.builders.TestDataGenerator;

import java.sql.SQLException;

public abstract class BaseDbSpec {

    protected final UserDao userDao;

    public BaseDbSpec() {
        this.userDao = new UserDao();
    }

    @BeforeAll
    static void initSchema() throws SQLException {
        DbInitializer.init();
    }

    protected User givenUserInDb() throws SQLException {
        User user = User.of(
                TestDataGenerator.name(),
                TestDataGenerator.uniqueEmail(),
                TestDataGenerator.password()
        );
        int id = userDao.create(user);
        return new User(id, user.name(), user.email(), user.password());
    }
}