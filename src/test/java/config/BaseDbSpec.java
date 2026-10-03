package config;

import db.config.DbInitializer;
import db.dao.UserDao;
import db.model.User;
import org.junit.jupiter.api.BeforeAll;
import testdata.builders.UserDataBuilder;

import java.sql.SQLException;

public abstract class BaseDbSpec {

    protected final UserDao userDao;
    protected final UserDataBuilder userDataBuilder;


    public BaseDbSpec() {
        this.userDao = new UserDao();
        this.userDataBuilder = new UserDataBuilder(this.userDao);
    }

    @BeforeAll
    static void initSchema() throws SQLException {
        DbInitializer.init();
    }

    protected User givenUserInDb() throws SQLException {
        return userDataBuilder.givenUserInDb();
    }

}
