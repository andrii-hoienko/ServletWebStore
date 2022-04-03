package com.example.webstore.dao;

import com.example.webstore.model.Role;
import com.example.webstore.model.User;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class UserDAO implements Crud<User>{

    private static final Logger logger = Logger.getLogger("UserDAOLogger");
    private static UserDAO instance;

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    @Override
    public User create(User entity) {
        String sql = "INSERT INTO users (email, first_name, last_name, password, role, blocked) VALUES (?,?,?,?,?,?)";
        String sqlGetLast = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql);
             Statement statementGetLast = con.createStatement()) {
            statement.setString(1, entity.getEmail());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getRole().toString());
            statement.setBoolean(6, false);
            statement.execute();
            ResultSet set = statementGetLast.executeQuery(sqlGetLast);
            set.next();
            entity.setId(set.getInt("id"));
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_CREATE_USER.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return entity;
    }

    @Override
    public Optional<User> findById(int id) throws SQLException {
        User user = new User();
        String find = "SELECT * FROM users WHERE id=?";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statement = con.prepareStatement(find)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user.setPassword(resultSet.getString("password"));
            user.setEmail(resultSet.getString("email"));
            user.setLastName(resultSet.getString("last_name"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setBlocked(resultSet.getBoolean("blocked"));
            user.setRole(Role.valueOf(resultSet.getString("role")));
            user.setId(resultSet.getInt("id"));
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_FIND_USER_BY_ID.toString());
            user = null;
            System.out.println(e.getMessage());
        } finally {
            DataSource.closeConnection(con);
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> findUserByEmail (String email) {
        Connection con = DataSource.getConnection();
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.execute();
            ResultSet set = statement.getResultSet();
            set.next();
            User user = new User();
            user.setEmail(set.getString("email"));
            user.setFirstName(set.getString("first_name"));
            user.setLastName(set.getString("last_name"));
            user.setBlocked(set.getBoolean("blocked"));
            user.setRole(Role.valueOf(set.getString("role")));
            user.setPassword(set.getString("password"));
            user.setId(set.getInt("id"));
            return Optional.of(user);
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_FIND_USER_BY_EMAIL.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection con = DataSource.getConnection();
        try (Statement statement = con.createStatement();
             ResultSet set = statement.executeQuery("SELECT * FROM users")) {
            while (set.next()) {
                User user = new User();
                user.setId((set.getInt("id")));
                user.setFirstName(set.getString("first_name"));
                user.setLastName(set.getString("last_name"));
                user.setEmail(set.getString("email"));
                user.setRole(Role.valueOf(set.getString("role")));
                user.setBlocked(set.getBoolean("blocked"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_FIND_ALL_USERS.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
        return users;
    }

    @Override
    public void update(User entity) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, password = ?, blocked = ? WHERE id = ?";
        Connection con = DataSource.getConnection();
        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, entity.getFirstName());
            statement.setString(2, entity.getLastName());
            statement.setString(3, entity.getPassword());
            statement.setBoolean(4, entity.isBlocked());
            statement.setInt(5, entity.getId());
            statement.execute();
        } catch (SQLException e) {
            logger.info(Messages.CANNOT_UPDATE_USER.toString());
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(con);
        }
    }
}