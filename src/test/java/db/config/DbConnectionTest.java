package db;

import db.config.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbConnectionTest {
    public static void main(String[] args) throws Exception {
        try (Connection conn = DbConnection.get();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1")) {
            if (rs.next()) {
                System.out.println("✅ Подключение к БД работает: " + rs.getInt(1));
            }
        }
    }

}