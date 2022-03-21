package com.example.webstore.web;

import com.example.webstore.exception.UserException;
import com.example.webstore.model.Role;
import com.example.webstore.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public abstract class Command {

    /**
     * Execution method for command.
     *
     * @return Address to go once the command is executed.
     */
    public abstract String execute(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception;

    @Override
    public final String toString() {
        return getClass().getSimpleName();
    }

    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        Role userRole = currentUser.getRole();
        boolean isBlocked = currentUser.isBlocked();
        return userRole.equals(Role.ADMIN) && !isBlocked;
    }
    public static boolean isUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return false;
        }
        Role userRole = currentUser.getRole();
        boolean isBlocked = currentUser.isBlocked();
        return userRole.equals(Role.USER) && !isBlocked;
    }
}