package com.example.webstore.web;

import com.example.webstore.model.Product;
import com.example.webstore.model.User;
import com.example.webstore.service.ProductService;
import com.example.webstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.example.webstore.controller.Path.*;

public class AdminUsersCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        if(isAdmin(request)) {

            UserService userService = UserService.getInstance();
            List<User> userList = userService.getUsers();
            request.setAttribute("users", userList);

            return PAGE_ADMIN_USERS;
        }
        return PAGE_MAIN;
    }
}