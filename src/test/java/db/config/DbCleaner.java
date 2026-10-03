package db.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DbCleaner {

    private DbCleaner() {}

    /**
     * Полностью очищает таблицу
     */
    public static void truncateUsers() throws SQLException {
        String sql = "TRUNCATE TABLE users RESTART IDENTITY CASCADE";
        try (Connection conn = DbConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        }
    }
}