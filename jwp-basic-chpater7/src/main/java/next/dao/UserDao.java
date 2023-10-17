package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        InsertJdbcTemplate jdbcTemplate = new InsertJdbcTemplate();
        jdbcTemplate.insert(user, this);
    }

    public void update(User user) throws SQLException {
        UpdateJdbcTemplate jdbcTemplate = new UpdateJdbcTemplate();
        jdbcTemplate.update(user, this);
    }

    public List<User> findAll() throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, Password, name, email FROM USERS";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            List<User> userList = new ArrayList<>();

            if (rs.next()) {
                userList.add(new User(
                        rs.getString("userId"),
                        (rs.getString("password")),
                        (rs.getString("name")),
                        (rs.getString("email"))
                ));
            }

            return userList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                rs.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    String createQueryForInsert() {
        return "INSERT INTO USERS VALUES (?, ?, ?, ?)";
    }

    void setValuesForInsert(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getUserId());
        pstmt.setString(2, user.getPassword());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
    }

    String createQueryForUpdate() {
        return "UPDATE users SET password = ?, name = ?, email = ? WHERE userId = ?";
    }

    void setValuesForUpdate(User user, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, user.getPassword());
        pstmt.setString(2, user.getName());
        pstmt.setString(3, user.getEmail());
        pstmt.setString(4, user.getUserId());
    }
}
