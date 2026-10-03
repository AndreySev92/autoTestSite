package db.dao;

import db.config.DbConnection;
import db.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    public int create(User user) throws SQLException {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = DbConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.name());
            stmt.setString(2, user.email());
            stmt.setString(3, user.password());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                throw new SQLException("Не удалось создать пользователя: id не получен");
            }
        }
    }

    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT id, name, email, password FROM users WHERE email = ?";

        try (Connection conn = DbConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
                return null;
            }
        }
    }

    public boolean update(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";

        try (Connection conn = DbConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.name());
            stmt.setString(2, user.email());
            stmt.setString(3, user.password());
            stmt.setInt(4, user.id());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DbConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT id, name, email, password FROM users ORDER BY id";
        List<User> users = new ArrayList<>();

        try (Connection conn = DbConnection.get();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")

                ));
            }
        }
        return users;
    }
}