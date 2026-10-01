package db.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DbInitializer {
    private DbInitializer() {}

    public static void init() throws SQLException {
        if (!tableExists("users")) {
            createUsersTable();
        }
    }

    private static boolean tableExists(String tableName) throws SQLException {
        String sql = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ?)";
        try (Connection conn = DbConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tableName);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getBoolean(1);
            }
        }
    }

    private static void createUsersTable() throws SQLException {
        String sql = """
                CREATE TABLE users (
                    id         SERIAL PRIMARY KEY,
                    name       VARCHAR(100) NOT NULL,
                    email      VARCHAR(255) UNIQUE NOT NULL,
                    password   VARCHAR(255) NOT NULL,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
                """;
        try (Connection conn = DbConnection.get();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}