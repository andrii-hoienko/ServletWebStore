package com.example.webstore.web;

import com.example.webstore.exception.UserException;
import com.example.webstore.model.User;
import com.example.webstore.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.example.webstore.controller.Path.*;

public class AdminUsersPostCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isAdmin(request)) {
            int userId = Integer.parseInt(request.getParameter("userIdToChangeStatus"));
            UserService userService = UserService.getInstance();
            userService.changeUserBlockStatus(userId);

            return CommandContainer.get("admin-panel/users").execute(request, response);
        }
        return PAGE_MAIN;
    }
}
