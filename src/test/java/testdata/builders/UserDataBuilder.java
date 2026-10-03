package testdata.builders;

import db.dao.UserDao;
import db.model.User;

import java.sql.SQLException;

public class UserDataBuilder {

    private final UserDao userDao;

    public UserDataBuilder(UserDao userDao) {
        this.userDao = userDao;
    }

    public User randomUser() {
        return User.of(
                TestDataGenerator.name(),
                TestDataGenerator.uniqueEmail(),
                TestDataGenerator.password()
        );
    }

    public User userWithEmail(String email) {
        return User.of(
                TestDataGenerator.name(),
                email,
                TestDataGenerator.password()
        );
    }

    public User userWithSqlInjectionEmail(String injection) {
        return userWithEmail(injection);
    }

    public User givenUserInDb() throws SQLException {
        User user = randomUser();
        int id = userDao.create(user);
        return new User(id, user.name(), user.email(), user.password());
    }

}