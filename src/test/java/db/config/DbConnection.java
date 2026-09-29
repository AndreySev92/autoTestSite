package db.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private DbConnection() {}

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(
                DbConfig.URL,
                DbConfig.USER,
                DbConfig.PASSWORD
        );
    }
}
