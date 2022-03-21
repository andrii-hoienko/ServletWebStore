package com.example.webstore.web;

import com.example.webstore.model.Order;
import com.example.webstore.model.Role;
import com.example.webstore.model.Status;
import com.example.webstore.model.User;
import com.example.webstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.example.webstore.controller.Path.*;

public class ProfileCommand extends Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (isUser(request) || isAdmin(request)) {
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            List<Order> userOrders = OrderService.getInstance().getOrdersForUser(currentUser);
            request.setAttribute("orders", userOrders);
            return PAGE_PROFILE;
        } else {
            return PAGE_MAIN;
        }
    }
}
