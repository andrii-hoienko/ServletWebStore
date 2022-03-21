package com.example.webstore.service;

import com.example.webstore.dao.UserDAO;
import com.example.webstore.exception.UserException;
import com.example.webstore.model.Role;
import com.example.webstore.model.User;
import com.example.webstore.model.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class UserService {

    private final UserDAO userDAO;
    private static UserService instance;

    public static synchronized UserService getInstance() throws SQLException {
        if (instance == null) {
            instance = new UserService(UserDAO.getInstance());
        }
        return instance;
    }

    private UserService(UserDAO userDao) {
        this.userDAO = userDao;
    }

    public void create(UserDTO userDTO) throws UserException {
        boolean isUserAlreadyExists = userDAO.findUserByEmail(userDTO.getEmail()).isPresent();
        if (isUserAlreadyExists) {
            throw new UserException("User already exists");
        }
        User newUser = new User();
        newUser.setRole(Role.USER);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(userDTO.getPassword());
        userDAO.create(newUser);
    }

    public void changeUserBlockStatus(int id) throws SQLException, UserException {
        Optional<User> userToBeBlocked = userDAO.findById(id);
        if (userToBeBlocked.isPresent()) {
            User user = userToBeBlocked.get();
            user.setBlocked(!user.isBlocked());
            userDAO.update(user);
        } else {
            throw new UserException("User does not exist");
        }
    }

    public List<User> getUsers() {
        return userDAO.findAll();
    }

    public User findUserByEmail(String email) throws UserException {
        return userDAO.findUserByEmail(email).orElseThrow(() -> new UserException("User does not exist"));
    }
}
